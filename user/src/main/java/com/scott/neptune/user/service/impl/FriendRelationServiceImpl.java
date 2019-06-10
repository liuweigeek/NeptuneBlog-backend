package com.scott.neptune.user.service.impl;

import com.google.common.collect.Lists;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.entity.FriendRelation;
import com.scott.neptune.user.entity.User;
import com.scott.neptune.user.repository.FriendRelationRepository;
import com.scott.neptune.user.service.IFriendRelationService;
import com.scott.neptune.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class FriendRelationServiceImpl implements IFriendRelationService {

    @Resource
    private IUserService userService;
    @Resource
    private FriendRelationRepository friendRelationRepository;

    /**
     * 保存好友关系
     *
     * @param friendRelation 单向好友关系
     * @return 保存结果
     */
    @Override
    public ServerResponse save(FriendRelation friendRelation) {

        if (friendRelationRepository.existsByAuthorIdAndTargetId(friendRelation.getAuthorId(), friendRelation.getTargetId())) {
            return ServerResponse.createBySuccess();
        }
        try {
            friendRelationRepository.save(friendRelation);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ServerResponse.createByErrorMessage(e.getMessage());
        }

    }

    /**
     * 获取好友关系
     *
     * @param authorId
     * @param targetId
     * @return
     */
    @Override
    public FriendRelation getRelation(String authorId, String targetId) {
        return friendRelationRepository.getByAuthorIdAndTargetId(authorId, targetId);
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
            friendRelationRepository.delete(friendRelation);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
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
    public List<User> findAllFollowing(String userId) {
        if (StringUtils.isBlank(userId)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<String> followingIdList = friendRelationRepository.findAllByAuthorId(userId)
                .stream()
                .map(FriendRelation::getTargetId)
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
    public List<User> findAllFollower(String userId) {
        if (StringUtils.isBlank(userId)) {
            return Lists.newArrayListWithCapacity(0);
        }

        List<String> followingIdList = friendRelationRepository.findAllByTargetId(userId)
                .stream()
                .map(FriendRelation::getTargetId)
                .collect(toList());

        return userService.findAllUserByIdList(followingIdList);
    }
}
