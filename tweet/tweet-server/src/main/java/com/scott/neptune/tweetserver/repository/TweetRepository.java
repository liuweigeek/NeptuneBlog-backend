package com.scott.neptune.tweetserver.repository;

import com.scott.neptune.tweetclient.enumerate.TweetTypeEnum;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface TweetRepository extends JpaRepository<TweetEntity, Long>,
        JpaSpecificationExecutor<TweetEntity> {

    Page<TweetEntity> findByAuthorId(@Param("authorId") Long authorId, Pageable pageable);

    Page<TweetEntity> findByAuthorIdIn(@Param("authorIds") Collection<Long> authorIds, Pageable pageable);

    @Query(value = "select t.* from t_tweet t where t.type = :type and t.referenced_tweet_id = :tweetId", nativeQuery = true)
    Page<TweetEntity> findTweetsByTweetId(@Param("tweetId") Long tweetId, @Param("type") TweetTypeEnum type, Pageable pageable);

}
