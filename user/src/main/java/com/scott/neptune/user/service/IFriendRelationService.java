package com.scott.neptune.user.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.userapi.entity.FriendRelation;
import com.scott.neptune.userapi.entity.UserEntity;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:40
 * @Description: IFriendRelationService
 */
public interface IFriendRelationService {

    ServerResponse save(FriendRelation friendRelation);

    FriendRelation getRelation(String fromId, String toId);

    boolean delete(FriendRelation friendRelation);

    boolean deleteByFromIdAndToId(String fromId, String toId);

    List<UserEntity> findAllFollowing(String userId);

    List<UserEntity> findAllFollower(String userId);
}
