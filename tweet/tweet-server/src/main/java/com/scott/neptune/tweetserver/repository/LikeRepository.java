package com.scott.neptune.tweetserver.repository;

import com.scott.neptune.tweetserver.domain.entity.LikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 15:39
 * @Description:
 */
public interface LikeRepository extends JpaRepository<LikeEntity, LikeEntity.LikeId>,
        JpaSpecificationExecutor<LikeEntity> {

    @Query("from LikeEntity l where l.id.tweetId = :tweetId")
    Page<LikeEntity> findLikesOfTweet(@Param("tweetId") Long tweetId, Pageable pageable);

    @Query("from LikeEntity f where f.id.tweetId = :tweetId")
    Collection<LikeEntity> findAllLikesOfTweet(@Param("tweetId") Long tweetId, Sort sort);

    @Query("from LikeEntity l where l.id.userId = :userId")
    Page<LikeEntity> findLikesByUser(@Param("userId") Long userId, Pageable pageable);

    @Query("from LikeEntity f where f.id.userId = :userId")
    Collection<LikeEntity> findAllLikesByUser(@Param("userId") Long userId, Sort sort);

}