package com.scott.neptune.user.repository;

import com.scott.neptune.user.entity.FriendRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRelationRepository extends JpaRepository<FriendRelation, String> {

    boolean existsByAuthorIdAndTargetId(String authorId, String targetId);

    boolean deleteByAuthorIdAndTargetId(String authorId, String targetId);

    FriendRelation getByAuthorIdAndTargetId(String authorId, String targetId);

    List<FriendRelation> findAllByAuthorId(String authorId);

    List<FriendRelation> findAllByTargetId(String targetId);
}
