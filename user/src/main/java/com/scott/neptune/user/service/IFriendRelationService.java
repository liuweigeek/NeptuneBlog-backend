package com.scott.neptune.user.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.entity.FriendRelation;
import com.scott.neptune.user.entity.UserEntity;

import java.util.List;

public interface IFriendRelationService {

    ServerResponse save(FriendRelation friendRelation);

    FriendRelation getRelation(String authorId, String targetId);

    boolean delete(FriendRelation friendRelation);

    boolean deleteByAuthorAndTarget(String authorId, String targetId);

    List<UserEntity> findAllFollowing(String userId);

    List<UserEntity> findAllFollower(String userId);
}
