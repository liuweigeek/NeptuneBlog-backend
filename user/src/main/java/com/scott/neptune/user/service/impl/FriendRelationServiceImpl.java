package com.scott.neptune.user.service.impl;

import com.google.common.collect.Lists;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.mapper.FriendRelationMapper;
import com.scott.neptune.user.service.IFriendRelationService;
import com.scott.neptune.user.service.IUserService;
import com.scott.neptune.userapi.entity.FriendRelation;
import com.scott.neptune.userapi.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Transactional
@Service
public class FriendRelationServiceImpl implements IFriendRelationService {

    @Resource
    private IUserService userService;
    @Resource
    private FriendRelationMapper friendRelationMapper;

    /**
     * 保存好友关系
     *
     * @param friendRelation 单向好友关系
     * @return 保存结果
     */
    @Override
    public ServerResponse save(FriendRelation friendRelation) {

        if (friendRelationMapper.exists(friendRelation)) {
            return ServerResponse.createBySuccess();
        }
        try {
            friendRelation.setFollowDate(new Date());
            friendRelationMapper.insert(friendRelation);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ServerResponse.createByErrorMessage(e.getMessage());
        }

    }

    /**
     * 获取好友关系
     *
     * @param fromId
     * @param toId
     * @return
     */
    @Override
    public FriendRelation getRelation(String fromId, String toId) {
        return friendRelationMapper.getOne(FriendRelation.builder().fromId(fromId).toId(toId).build());
    }

    /**
     * 删除好友关系
     *
     * @param friendRelation 单向好友关系
     * @return 删除结果
     */
    @Override
    public boolean delete(FriendRelation friendRelation) {

        try {
            friendRelationMapper.delete(friendRelation);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

    }

    /**
     * 根据关注人和被关注人解除关系
     *
     * @param fromId  关注人
     * @param toId  被关注人
     * @return
     */
    @Override
    public boolean deleteByFromIdAndToId(String fromId, String toId) {
        try {
            friendRelationMapper.delete(FriendRelation.builder().fromId(fromId).toId(toId).build());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 获取关注列表
     *
     * @param userId 当前登陆用户Id
     * @return 关注列表
     */
    @Override
    public List<UserEntity> findAllFollowing(String userId) {
        if (StringUtils.isBlank(userId)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<String> followingIdList = friendRelationMapper.findAll(FriendRelation.builder().fromId(userId).build())
                .stream()
                .map(FriendRelation::getToId)
                .collect(toList());

        return userService.findAllUserByIdList(followingIdList);
    }

    /**
     * 获取粉丝列表
     *
     * @param userId 当前登陆用户Id
     * @return 粉丝列表
     */
    @Override
    public List<UserEntity> findAllFollower(String userId) {
        if (StringUtils.isBlank(userId)) {
            return Lists.newArrayListWithCapacity(0);
        }

        List<String> followerIdList = friendRelationMapper.findAll(FriendRelation.builder().fromId(userId).build())
                .stream()
                .map(FriendRelation::getFromId)
                .collect(toList());

        return userService.findAllUserByIdList(followerIdList);
    }
}
