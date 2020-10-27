package com.scott.neptune.tweetserver.repository;

import com.scott.neptune.tweetclient.enumerate.TweetTypeEnum;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 15:39
 * @Description:
 */
public interface TweetRepository extends JpaRepository<TweetEntity, Long>,
        JpaSpecificationExecutor<TweetEntity> {

    /**
     * 查找指定Tweet相关的retweet，quote，reply
     *
     * @param tweetId
     * @param type
     * @param pageable
     * @return
     */
    @Query(value = "select t.* from tb_tweet t where t.type = :type and t.referenced_tweet_id = :tweetId", nativeQuery = true)
    Page<TweetEntity> findTweetsByReferencedTweetId(@Param("tweetId") Long tweetId, @Param("type") TweetTypeEnum type, Pageable pageable);

}
