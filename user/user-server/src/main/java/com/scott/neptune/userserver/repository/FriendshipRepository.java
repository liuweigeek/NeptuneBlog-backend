package com.scott.neptune.userserver.repository;

import com.scott.neptune.userserver.domain.entity.FriendshipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/7/28 16:19
 * @Description:
 */
public interface FriendshipRepository extends JpaRepository<FriendshipEntity, FriendshipEntity.FriendshipId>,
        JpaSpecificationExecutor<FriendshipEntity> {

    /**
     * find following users
     *
     * @param sourceId
     * @param pageable
     * @return
     */
    @EntityGraph(value = "friendship.following", type = EntityGraph.EntityGraphType.FETCH)
    @Query("from FriendshipEntity f where f.id.sourceId = :sourceId")
    Page<FriendshipEntity> findFollowing(@Param("sourceId") Long sourceId, Pageable pageable);

    /**
     * find follower
     *
     * @param targetId
     * @param pageable
     * @return
     */
    @EntityGraph(value = "friendship.followers", type = EntityGraph.EntityGraphType.FETCH)
    @Query("from FriendshipEntity f where f.id.targetId = :targetId")
    Page<FriendshipEntity> findFollowers(@Param("targetId") Long targetId, Pageable pageable);

    /**
     * find all following users
     *
     * @param sourceId
     * @return
     */
    @EntityGraph(value = "friendship.following", type = EntityGraph.EntityGraphType.FETCH)
    @Query("from FriendshipEntity f where f.id.sourceId = :sourceId")
    Collection<FriendshipEntity> findAllBySourceUser(@Param("sourceId") Long sourceId, Sort sort);

    /**
     * find all follower
     *
     * @param targetId
     * @return
     */
    @EntityGraph(value = "friendship.followers", type = EntityGraph.EntityGraphType.FETCH)
    @Query("from FriendshipEntity f where f.id.targetId = :targetId")
    Collection<FriendshipEntity> findAllByTargetUser(@Param("targetId") Long targetId, Sort sort);

    /**
     * find all following friends
     *
     * @param sourceId
     * @return
     */
    @EntityGraph(value = "friendship.friends", type = EntityGraph.EntityGraphType.FETCH)
    @Query("from FriendshipEntity f where f.id.sourceId = :sourceId and f.id.targetId in :targetUserIds")
    Collection<FriendshipEntity> findAllBySourceUserAndTargetUserIn(@Param("sourceId") Long sourceId,
                                                                    @Param("targetUserIds") Collection<Long> targetUserIds,
                                                                    Sort sort);

    /**
     * find all follower
     *
     * @param targetId
     * @return
     */
    @EntityGraph(value = "friendship.followers", type = EntityGraph.EntityGraphType.FETCH)
    @Query("from FriendshipEntity f where f.id.targetId = :targetId and f.id.sourceId in :sourceUserIds")
    Collection<FriendshipEntity> findAllByTargetUserAndSourceUserIn(@Param("targetId") Long targetId,
                                                                    @Param("sourceUserIds") Collection<Long> sourceUserIds,
                                                                    Sort sort);
}
