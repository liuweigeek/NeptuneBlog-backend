package com.scott.neptune.userserver.service;

import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userclient.dto.RelationshipUserDto;
import org.springframework.data.domain.Page;

import java.util.Collection;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
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
     * @param userId 用户Id
     * @return 关注列表
     */
    Page<RelationshipUserDto> findFollowing(Long userId, boolean includeRelations, long offset, int limit);

    /**
     * 获取关注列表
     *
     * @param username 用户名
     * @return 关注列表
     */
    Page<RelationshipUserDto> findFollowing(String username, boolean includeRelations, long offset, int limit);

    /**
     * 获取关注者列表
     *
     * @param userId 用户Id
     * @return 关注者列表
     */
    Page<RelationshipUserDto> findFollowers(Long userId, boolean includeRelations, long offset, int limit);

    /**
     * 获取关注者列表
     *
     * @param username 用户名
     * @return 关注者列表
     */
    Page<RelationshipUserDto> findFollowers(String username, boolean includeRelations, long offset, int limit);

    /**
     * 获取全部已关注用户
     *
     * @param userId
     * @param targetUserIds
     * @return
     */
    Collection<RelationshipUserDto> findAllFollowing(Long userId, Collection<Long> targetUserIds, boolean includeRelations);

    /**
     * 获取全部关注者
     *
     * @param userId
     * @param sourceUserIds
     * @return
     */
    Collection<RelationshipUserDto> findAllFollowers(Long userId, Collection<Long> sourceUserIds, boolean includeRelations);

    /**
     * 获取全部已关注用户
     *
     * @param userId
     * @param targetUserIds
     * @return
     */
    Collection<Long> findAllFollowingIds(Long userId, Collection<Long> targetUserIds);

    /**
     * 获取全部关注者
     *
     * @param userId
     * @param sourceUserIds
     * @return
     */
    Collection<Long> findAllFollowersIds(Long userId, Collection<Long> sourceUserIds);

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

    /**
     * 查询指定用户与已登录用户的关系
     *
     * @param userIds
     * @param authUserId
     * @return
     */
    Collection<RelationshipUserDto> getRelationshipByIds(Collection<Long> userIds, Long authUserId);

    /**
     * 查询指定用户与已登录用户的关系
     *
     * @param usernames
     * @param authUserId
     * @return
     */
    Collection<RelationshipUserDto> getRelationshipByUsernames(Collection<String> usernames, Long authUserId);

}
