package com.scott.neptune.userserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.common.model.OffsetPageable;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userclient.dto.RelationshipUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userclient.enumerate.UserConnectionEnum;
import com.scott.neptune.userserver.component.FriendshipComponent;
import com.scott.neptune.userserver.convertor.FriendshipConvertor;
import com.scott.neptune.userserver.convertor.RelationshipUserConvertor;
import com.scott.neptune.userserver.convertor.UserConvertor;
import com.scott.neptune.userserver.domain.entity.FriendshipEntity;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import com.scott.neptune.userserver.repository.FriendshipRepository;
import com.scott.neptune.userserver.service.IFriendshipService;
import com.scott.neptune.userserver.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class FriendshipServiceImpl implements IFriendshipService {

    private final IUserService userService;
    private final FriendshipComponent friendshipComponent;
    private final FriendshipRepository friendshipRepository;
    private final FriendshipConvertor friendshipConvertor;
    private final UserConvertor userConvertor;
    private final RelationshipUserConvertor relationshipUserConvertor;

    /**
     * 保存好友关系
     *
     * @param friendshipDto 好友关系
     * @return 保存结果
     */
    @Override
    public FriendshipDto save(FriendshipDto friendshipDto) {

        FriendshipEntity friendshipEntity = friendshipConvertor.convertToEntity(friendshipDto);
        return friendshipRepository.findById(
                FriendshipEntity.FriendshipId.builder()
                        .sourceId(friendshipDto.getSourceId())
                        .targetId(friendshipDto.getTargetId())
                        .build())
                .map(friendshipConvertor.convertToDto())
                .orElseGet(() -> {
                    UserEntity sourceUser = Optional.ofNullable(userService.findUserById(friendshipDto.getSourceId(), null, false))
                            .map(userConvertor::convertToEntity)
                            .orElseThrow(() -> new RestException("当前用户不存在", HttpStatus.NOT_FOUND));
                    UserEntity targetUser = Optional.ofNullable(userService.findUserById(friendshipDto.getTargetId(), null, false))
                            .map(userConvertor::convertToEntity)
                            .orElseThrow(() -> new RestException("该用户不存在", HttpStatus.NOT_FOUND));
                    friendshipEntity.setSourceUser(sourceUser);
                    friendshipEntity.setTargetUser(targetUser);
                    friendshipEntity.setFollowDate(new Date());
                    friendshipRepository.save(friendshipEntity);
                    return friendshipConvertor.convertToDto(friendshipEntity);
                });
    }

    /**
     * 获取好友关系
     *
     * @param sourceId
     * @param targetId
     * @return
     */
    @Override
    public FriendshipDto getBySourceAndTarget(Long sourceId, Long targetId) {
        return friendshipRepository.findById(FriendshipEntity.FriendshipId.builder()
                .sourceId(sourceId).targetId(targetId).build())
                .map(friendshipConvertor.convertToDto())
                .orElse(null);
    }

    /**
     * 获取关注列表
     *
     * @param userId 当前登陆用户Id
     * @return 关注列表
     */
    @Override
    public Page<RelationshipUserDto> findFollowing(Long userId, boolean includeRelations, long offset, int limit) {
        if (userId == null) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("followDate")));
        Page<RelationshipUserDto> friendshipDtoPage = friendshipRepository.findFollowing(userId, pageable)
                .map(friendshipEntity -> {
                    RelationshipUserDto relationshipUserDto = relationshipUserConvertor.convertToDto(friendshipEntity.getTargetUser());
                    relationshipUserDto.addRelationInfo(friendshipEntity.getFollowDate(), friendshipEntity.getFollowFrom());
                    return relationshipUserDto;
                });

        if (includeRelations) {
            List<RelationshipUserDto> userList = friendshipDtoPage.getContent();
            friendshipComponent.fillUserRelationshipConnections(userList, userId);
            Map<Long, Collection<UserConnectionEnum>> collectionMap = userList.stream()
                    .collect(Collectors.toMap(RelationshipUserDto::getId, RelationshipUserDto::getConnections));
            friendshipDtoPage.getContent().forEach(userRelationshipDto -> userRelationshipDto.setConnections(collectionMap.get(userRelationshipDto.getId())));
        }
        return friendshipDtoPage;
    }

    @Override
    public Page<RelationshipUserDto> findFollowing(String username, boolean includeRelations, long offset, int limit) {
        UserDto userDto = userService.findUserByUsername(username, null, false);
        if (userDto == null) {
            return Page.empty();
        }
        return this.findFollowing(userDto.getId(), includeRelations, offset, limit);
    }

    /**
     * 获取关注者列表
     *
     * @param userId 当前登陆用户Id
     * @return 关注者列表
     */
    @Override
    public Page<RelationshipUserDto> findFollowers(Long userId, boolean includeRelations, long offset, int limit) {
        if (userId == null) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("followDate")));
        Page<RelationshipUserDto> friendshipDtoPage = friendshipRepository.findFollowers(userId, pageable)
                .map(friendshipEntity -> {
                    RelationshipUserDto relationshipUserDto = relationshipUserConvertor.convertToDto(friendshipEntity.getSourceUser());
                    relationshipUserDto.addRelationInfo(friendshipEntity.getFollowDate(), friendshipEntity.getFollowFrom());
                    return relationshipUserDto;
                });

        if (includeRelations) {
            List<RelationshipUserDto> userList = friendshipDtoPage.getContent();
            friendshipComponent.fillUserRelationshipConnections(userList, userId);
            Map<Long, Collection<UserConnectionEnum>> collectionMap = userList.stream()
                    .collect(Collectors.toMap(RelationshipUserDto::getId, RelationshipUserDto::getConnections));
            friendshipDtoPage.getContent().forEach(userRelationshipDto -> userRelationshipDto.setConnections(collectionMap.get(userRelationshipDto.getId())));
        }
        return friendshipDtoPage;
    }

    @Override
    public Page<RelationshipUserDto> findFollowers(String username, boolean includeRelations, long offset, int limit) {
        UserDto userDto = userService.findUserByUsername(username, null, false);
        if (userDto == null) {
            return Page.empty();
        }
        return this.findFollowers(userDto.getId(), includeRelations, offset, limit);
    }

    /**
     * 获取全部已关注用户
     *
     * @param userId
     * @param targetUserIds
     * @return
     */
    @CachePut(cacheNames = "followingUsers-all", key = "#userId")
    @Override
    public Collection<RelationshipUserDto> findAllFollowing(Long userId, Collection<Long> targetUserIds, boolean includeRelations) {
        if (userId == null) {
            return Collections.emptyList();
        }
        Collection<RelationshipUserDto> userRelationshipList;
        if (CollectionUtils.isEmpty(targetUserIds)) {
            userRelationshipList = friendshipRepository.findAllBySourceUser(userId, Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipEntity -> {
                        RelationshipUserDto relationshipUserDto = relationshipUserConvertor.convertToDto(friendshipEntity.getTargetUser());
                        relationshipUserDto.addRelationInfo(friendshipEntity.getFollowDate(), friendshipEntity.getFollowFrom());
                        return relationshipUserDto;
                    })
                    .collect(Collectors.toList());
        } else {
            userRelationshipList = friendshipRepository.findAllBySourceUserAndTargetUserIn(userId, targetUserIds,
                    Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipEntity -> {
                        RelationshipUserDto relationshipUserDto = relationshipUserConvertor.convertToDto(friendshipEntity.getTargetUser());
                        relationshipUserDto.addRelationInfo(friendshipEntity.getFollowDate(), friendshipEntity.getFollowFrom());
                        return relationshipUserDto;
                    })
                    .collect(Collectors.toList());
        }
        if (includeRelations) {
            friendshipComponent.fillUserRelationshipConnections(userRelationshipList, userId);
        }
        return userRelationshipList;
    }

    /**
     * 获取全部关注者
     *
     * @param userId
     * @param sourceUserIds
     * @return
     */
    @Override
    public Collection<RelationshipUserDto> findAllFollowers(Long userId, Collection<Long> sourceUserIds, boolean includeRelations) {
        if (userId == null) {
            return Collections.emptyList();
        }
        List<RelationshipUserDto> userRelationshipList;
        if (CollectionUtils.isEmpty(sourceUserIds)) {
            userRelationshipList = friendshipRepository.findAllByTargetUser(userId, Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipEntity -> {
                        RelationshipUserDto relationshipUserDto = relationshipUserConvertor.convertToDto(friendshipEntity.getSourceUser());
                        relationshipUserDto.addRelationInfo(friendshipEntity.getFollowDate(), friendshipEntity.getFollowFrom());
                        return relationshipUserDto;
                    })
                    .collect(Collectors.toList());
        } else {
            userRelationshipList = friendshipRepository.findAllByTargetUserAndSourceUserIn(userId, sourceUserIds,
                    Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipEntity -> {
                        RelationshipUserDto relationshipUserDto = relationshipUserConvertor.convertToDto(friendshipEntity.getSourceUser());
                        relationshipUserDto.addRelationInfo(friendshipEntity.getFollowDate(), friendshipEntity.getFollowFrom());
                        return relationshipUserDto;
                    })
                    .collect(Collectors.toList());
        }
        if (includeRelations) {
            friendshipComponent.fillUserRelationshipConnections(userRelationshipList, userId);
        }
        return userRelationshipList;
    }

    /**
     * 获取全部已关注用户
     *
     * @param userId
     * @param targetUserIds
     * @return
     */
    @CachePut(cacheNames = "followingIds-all", key = "#userId")
    @Override
    public Collection<Long> findAllFollowingIds(Long userId, Collection<Long> targetUserIds) {
        if (userId == null) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(targetUserIds)) {
            return friendshipRepository.findAllBySourceUser(userId, Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipConvertor.convertToDto())
                    .map(FriendshipDto::getTargetId)
                    .collect(Collectors.toList());
        } else {
            return friendshipRepository.findAllBySourceUserAndTargetUserIn(userId, targetUserIds,
                    Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipConvertor.convertToDto())
                    .map(FriendshipDto::getTargetId)
                    .collect(Collectors.toList());
        }
    }

    /**
     * 获取全部关注者
     *
     * @param userId
     * @param sourceUserIds
     * @return
     */
    @Override
    public Collection<Long> findAllFollowersIds(Long userId, Collection<Long> sourceUserIds) {
        if (userId == null) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(sourceUserIds)) {
            return friendshipRepository.findAllByTargetUser(userId, Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipConvertor.convertToDto())
                    .map(FriendshipDto::getSourceId)
                    .collect(Collectors.toList());
        } else {
            return friendshipRepository.findAllByTargetUserAndSourceUserIn(userId, sourceUserIds,
                    Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipConvertor.convertToDto())
                    .map(FriendshipDto::getSourceId)
                    .collect(Collectors.toList());
        }
    }

    /**
     * 删除好友关系
     *
     * @param friendshipDto 单向好友关系
     * @return 删除结果
     */
    @Override
    public void delete(FriendshipDto friendshipDto) {
        try {
            friendshipRepository.deleteById(FriendshipEntity.FriendshipId.builder()
                    .sourceId(friendshipDto.getSourceId())
                    .targetId(friendshipDto.getTargetId())
                    .build());
        } catch (Exception e) {
            log.error("取消关注失败: ", e);
            throw new NeptuneBlogException("取消关注失败", e);
        }
    }

    /**
     * 根据关注人和被关注人解除关系
     *
     * @param sourceId 关注人
     * @param targetId 被关注人
     * @return
     */
    @Override
    public void delete(Long sourceId, Long targetId) {
        try {
            friendshipRepository.deleteById(FriendshipEntity.FriendshipId.builder()
                    .sourceId(sourceId).targetId(targetId).build());
        } catch (Exception e) {
            log.error("取消关注失败: ", e);
            throw new NeptuneBlogException("取消关注失败", e);
        }
    }

    /**
     * 查询指定用户与已登录用户的关系
     *
     * @param userIds
     * @param authUserId
     * @return
     */
    @Override
    public Collection<RelationshipUserDto> getRelationshipByIds(Collection<Long> userIds, Long authUserId) {
        Collection<UserDto> userDtoList = userService.findAllUserByIdList(userIds, authUserId, true);
        return userDtoList.stream()
                .map(user -> new RelationshipUserDto(user.getId(), user.getUsername(), user.getName(), user.getConnections()))
                .collect(Collectors.toList());
    }

    /**
     * 查询指定用户与已登录用户的关系
     *
     * @param usernames
     * @param authUserId
     * @return
     */
    @Override
    public Collection<RelationshipUserDto> getRelationshipByUsernames(Collection<String> usernames, Long authUserId) {
        List<Long> ids = userService.findAllUserByUsernameList(usernames, authUserId, false)
                .stream().map(UserDto::getId).collect(Collectors.toList());
        return this.getRelationshipByIds(ids, authUserId);
    }
}
