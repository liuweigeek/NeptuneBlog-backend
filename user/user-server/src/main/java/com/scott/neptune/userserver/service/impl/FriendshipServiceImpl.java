package com.scott.neptune.userserver.service.impl;

import com.scott.neptune.common.dto.OffsetPageRequest;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userserver.convertor.FriendshipConvertor;
import com.scott.neptune.userserver.domain.entity.FriendshipEntity;
import com.scott.neptune.userserver.repository.FriendshipRepository;
import com.scott.neptune.userserver.service.IFriendshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class FriendshipServiceImpl implements IFriendshipService {

    @Resource
    private FriendshipRepository friendshipRepository;
    @Resource
    private FriendshipConvertor friendshipConvertor;

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
    public FriendshipDto getRelationBySourceIdAndTargetId(Long sourceId, Long targetId) {
        return friendshipRepository.findById(FriendshipEntity.FriendshipId.builder()
                .sourceId(sourceId).targetId(targetId).build())
                .map(friendshipConvertor.convertToDto())
                .orElse(null);
    }

    /**
     * 删除好友关系
     *
     * @param friendshipDto 单向好友关系
     * @return 删除结果
     */
    @Override
    public boolean delete(FriendshipDto friendshipDto) {

        //TODO necessary to catch the exception?
        try {
            friendshipRepository.deleteById(FriendshipEntity.FriendshipId.builder()
                    .sourceId(friendshipDto.getSourceId())
                    .targetId(friendshipDto.getTargetId())
                    .build());
            return true;
        } catch (Exception e) {
            log.error("delete friendship exception: ", e);
            return false;
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
    public boolean deleteBySourceIdAndTargetId(Long sourceId, Long targetId) {
        friendshipRepository.deleteById(FriendshipEntity.FriendshipId.builder()
                .sourceId(sourceId).targetId(targetId).build());
        return true;
    }

    /**
     * 获取关注列表
     *
     * @param userId 当前登陆用户Id
     * @return 关注列表
     */
    @Override
    public Page<FriendshipDto> findFriends(Long userId, int offset, int limit) {
        if (userId == null) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageRequest.of(offset, limit, Sort.by(Sort.Order.desc("followDate")));
        return friendshipRepository.findFriends(userId, pageable).map(friendshipConvertor.convertToDto());
    }

    /**
     * 获取关注者列表
     *
     * @param userId 当前登陆用户Id
     * @return 关注者列表
     */
    @Override
    public Page<FriendshipDto> findFollowers(Long userId, int offset, int limit) {
        if (userId == null) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageRequest.of(offset, limit, Sort.by(Sort.Order.desc("followDate")));
        return friendshipRepository.findFollowers(userId, pageable).map(friendshipConvertor.convertToDto());
    }

    /**
     * 获取全部已关注用户
     *
     * @param userId
     * @return
     */
    @Override
    public List<FriendshipDto> findAllFriends(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        return friendshipRepository.findAllBySourceUserOrderByFollowDateDesc(userId).stream()
                .map(friendshipConvertor.convertToDto())
                .collect(Collectors.toList());
    }

    /**
     * 获取全部关注者
     *
     * @param userId
     * @return
     */
    @Override
    public List<FriendshipDto> findAllFollowers(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        return friendshipRepository.findAllByTargetUserOrderByFollowDateDesc(userId).stream()
                .map(friendshipConvertor.convertToDto())
                .collect(Collectors.toList());
    }

    /**
     * 获取用户关系
     *
     * @param fromUserId
     * @param toUserId
     * @return
     */
    /*@Override
    public UserDto.RelationEnum getRelation(String fromUserId, String toUserId) {
        if (StringUtils.equals(fromUserId, toUserId)) {
            return null;
        }
        FriendRelationEntity friendRelationEntity = this.getRelationBysourceIdAndtargetId(fromUserId, toUserId);
        if (Objects.isNull(friendRelationEntity)) {
            return UserDto.RelationEnum.UN_FOLLOW;
        }
        return UserDto.RelationEnum.FOLLOWING;
    }*/
}
