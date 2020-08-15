package com.scott.neptune.userserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.model.OffsetPageable;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userserver.convertor.FriendshipConvertor;
import com.scott.neptune.userserver.domain.entity.FriendshipEntity;
import com.scott.neptune.userserver.repository.FriendshipRepository;
import com.scott.neptune.userserver.service.IFriendshipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
@Service
public class FriendshipServiceImpl implements IFriendshipService {

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
    public Page<FriendshipDto> findFriends(Long userId, long offset, int limit) {
        if (userId == null) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("followDate")));
        return friendshipRepository.findFriends(userId, pageable).map(friendshipConvertor.convertToDto());
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

    /**
     * 获取全部已关注用户
     *
     * @param userId
     * @param targetUserIds
     * @return
     */
    @Override
    public List<FriendshipDto> findAllFriends(Long userId, List<Long> targetUserIds) {
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
    public List<FriendshipDto> findAllFollowers(Long userId, List<Long> sourceUserIds) {
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
}
