package com.scott.neptune.userserver.service.impl;

import com.google.common.collect.Lists;
import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.model.OffsetPageable;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userclient.dto.RelationshipDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.convertor.FriendshipConvertor;
import com.scott.neptune.userserver.domain.entity.FriendshipEntity;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import com.scott.neptune.userserver.repository.FriendshipRepository;
import com.scott.neptune.userserver.service.IFriendshipService;
import com.scott.neptune.userserver.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class FriendshipServiceImpl implements IFriendshipService {

    private final IUserService userService;
    private final FriendshipRepository friendshipRepository;
    private final FriendshipConvertor friendshipConvertor;

    /**
     * 保存好友关系
     *
     * @param friendshipDto 好友关系
     * @return 保存结果
     */
    @Override
    public FriendshipDto save(FriendshipDto friendshipDto) {

        FriendshipEntity friendshipEntity = friendshipConvertor.convertToEntity(friendshipDto);
        //TODO more clearly?
        return friendshipRepository.findById(FriendshipEntity.FriendshipId.builder()
                .sourceId(friendshipEntity.getSourceUser().getId())
                .targetId(friendshipEntity.getTargetUser().getId())
                .build())
                .map(friendshipConvertor.convertToDto())
                .orElseGet(() -> {
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
    public Page<FriendshipDto> findFollowing(Long userId, long offset, int limit) {
        if (userId == null) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("followDate")));
        return friendshipRepository.findFriends(userId, pageable).map(friendshipConvertor.convertToDto());
    }

    @Override
    public Page<FriendshipDto> findFollowing(String username, long offset, int limit) {
        UserDto userDto = userService.findUserByUsername(username, null);
        if (userDto == null) {
            return Page.empty();
        }
        return this.findFollowing(userDto.getId(), offset, limit);
    }

    /**
     * 获取关注者列表
     *
     * @param userId 当前登陆用户Id
     * @return 关注者列表
     */
    @Override
    public Page<FriendshipDto> findFollowers(Long userId, long offset, int limit) {
        if (userId == null) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("followDate")));
        return friendshipRepository.findFollowers(userId, pageable).map(friendshipConvertor.convertToDto());
    }

    @Override
    public Page<FriendshipDto> findFollowers(String username, long offset, int limit) {
        UserDto userDto = userService.findUserByUsername(username, null);
        if (userDto == null) {
            return Page.empty();
        }
        return this.findFollowers(userDto.getId(), offset, limit);
    }

    /**
     * 获取全部已关注用户
     *
     * @param userId
     * @param targetUserIds
     * @return
     */
    @Override
    public Collection<FriendshipDto> findAllFollowing(Long userId, Collection<Long> targetUserIds) {
        if (userId == null) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(targetUserIds)) {
            return friendshipRepository.findAllBySourceUser(userId, Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipConvertor.convertToDto())
                    .collect(Collectors.toList());
        } else {
            return friendshipRepository.findAllBySourceUserAndTargetUserIn(userId, targetUserIds,
                    Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipConvertor.convertToDto())
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
    public Collection<FriendshipDto> findAllFollowers(Long userId, Collection<Long> sourceUserIds) {
        if (userId == null) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(sourceUserIds)) {
            return friendshipRepository.findAllByTargetUser(userId, Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipConvertor.convertToDto())
                    .collect(Collectors.toList());
        } else {
            return friendshipRepository.findAllByTargetUserAndSourceUserIn(userId, sourceUserIds,
                    Sort.by(Sort.Order.desc("followDate"))).stream()
                    .map(friendshipConvertor.convertToDto())
                    .collect(Collectors.toList());
        }
    }

    /**
     * 获取全部已关注用户
     *
     * @param userId
     * @param targetUserIds
     * @return
     */
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
    public Collection<RelationshipDto> getRelationshipByIds(Collection<Long> userIds, Long authUserId) {

        Collection<UserDto> users = userService.findAllUserByIdList(userIds, authUserId);

        Collection<UserEntity> following = friendshipRepository
                .findAllBySourceUserAndTargetUserIn(authUserId, userIds, Sort.by(Sort.Order.desc("followDate")))
                .stream().map(FriendshipEntity::getTargetUser).collect(Collectors.toList());
        Collection<UserEntity> followers = friendshipRepository
                .findAllByTargetUserAndSourceUserIn(authUserId, userIds, Sort.by(Sort.Order.desc("followDate")))
                .stream().map(FriendshipEntity::getSourceUser).collect(Collectors.toList());

        Map<Long, RelationshipDto> relationshipMap = users.stream()
                .collect(Collectors.toMap(UserDto::getId, user -> new RelationshipDto(user.getId(),
                        user.getUsername(), user.getName())));

        following.forEach(entity -> relationshipMap.get(entity.getId()).addConnection(RelationshipDto.ConnectionEnum.FOLLOWING));
        followers.forEach(entity -> relationshipMap.get(entity.getId()).addConnection(RelationshipDto.ConnectionEnum.FOLLOWED_BY));

        return new ArrayList<>(relationshipMap.values());
    }

    /**
     * 查询指定用户与已登录用户的关系
     *
     * @param usernames
     * @param authUserId
     * @return
     */
    @Override
    public Collection<RelationshipDto> getRelationshipByUsernames(Collection<String> usernames, Long authUserId) {

        List<Long> ids = Lists.newArrayListWithExpectedSize(usernames.size());
        if (CollectionUtils.isNotEmpty(usernames)) {
            List<Long> idFromUsernames = userService.findAllUserByUsernameList(usernames, authUserId)
                    .stream().map(UserDto::getId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(idFromUsernames)) {
                ids.addAll(idFromUsernames);
            }
        }
        return this.getRelationshipByIds(ids, authUserId);
    }
}
