package com.scott.neptune.user.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.entity.FriendRelation;
import com.scott.neptune.user.entity.User;

import java.util.List;

public interface IFriendRelationService {

    ServerResponse save(FriendRelation friendRelation);

    FriendRelation getRelation(String authorId, String targetId);

    boolean delete(FriendRelation friendRelation);

    List<User> findAllFollowing(String userId);

    List<User> findAllFollower(String userId);
}
