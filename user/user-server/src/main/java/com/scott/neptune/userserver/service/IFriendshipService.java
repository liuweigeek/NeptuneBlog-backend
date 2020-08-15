package com.scott.neptune.userserver.service;

import com.scott.neptune.userclient.dto.FriendshipDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/9/23 09:40
 * @Description: IFriendRelationService
 */
public interface IFriendshipService {

    /**
     * 保存好友关系
     *
     * @param friendshipDto 好友关系
     * @return 保存结果
     */
    FriendshipDto save(FriendshipDto friendshipDto);

    /**
     * 获取好友关系
     *
     * @param sourceId
     * @param targetId
     * @return
     */
    FriendshipDto getBySourceAndTarget(Long sourceId, Long targetId);

    /**
     * 获取关注列表
     *
     * @param userId 当前登陆用户Id
     * @return 关注列表
     */
    Page<FriendshipDto> findFriends(Long userId, long offset, int limit);

    /**
     * 获取关注者列表
     *
     * @param userId 当前登陆用户Id
     * @return 关注者列表
     */
    Page<FriendshipDto> findFollowers(Long userId, long offset, int limit);

    /**
     * 获取全部已关注用户
     *
     * @param userId
     * @param targetUserIds
     * @return
     */
    List<FriendshipDto> findAllFriends(Long userId, List<Long> targetUserIds);

    /**
     * 获取全部关注者
     *
     * @param userId
     * @param sourceUserIds
     * @return
     */
    List<FriendshipDto> findAllFollowers(Long userId, List<Long> sourceUserIds);

    /**
     * 删除好友关系
     *
     * @param friendshipDto 单向好友关系
     * @return 删除结果
     */
    void delete(FriendshipDto friendshipDto);

    /**
     * 根据关注人和被关注人解除关系
     *
     * @param sourceId 关注人
     * @param targetId 被关注人
     * @return
     */
    void delete(Long sourceId, Long targetId);

}
