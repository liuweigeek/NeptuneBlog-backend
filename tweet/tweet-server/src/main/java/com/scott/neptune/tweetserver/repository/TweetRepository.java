package com.scott.neptune.tweetserver.repository;

import com.scott.neptune.tweetclient.enumerate.TweetTypeEnum;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
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

    /**
     * 查询指定用户的Tweets
     *
     * @param authorId
     * @param pageable
     * @return
     */
    Page<TweetEntity> findByAuthorId(@Param("authorId") Long authorId, Pageable pageable);

    /**
     * 查询多个用户的Tweets
     *
     * @param authorIds
     * @param pageable
     * @return
     */
    Page<TweetEntity> findByAuthorIdIn(@Param("authorIds") Collection<Long> authorIds, Pageable pageable);

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

    /**
     * 更新retweet统计数量
     *
     * @param tweetId
     * @return
     */
    @Modifying
    @Query(value = "update tb_tweet t\n" +
            "    left join (select id, count(1) count from tb_tweet where type = 'retweeted' group by id) m on m.id = t.id\n" +
            "set t.retweet_count = m.count\n" +
            "where t.id = :id", nativeQuery = true)
    Integer updatePublicMetricsRetweetCountById(@Param("id") Long tweetId);

    /**
     * 更新quote统计数量
     *
     * @param tweetId
     * @return
     */
    @Modifying
    @Query(value = "update tb_tweet t\n" +
            "    " +
            "left join (select id, count(1) count from tb_tweet where type = 'quoted' group by id) m on m.id = t.id\n" +
            "set t.retweet_count = m.count\n" +
            "where t.id = :id", nativeQuery = true)
    Integer updatePublicMetricsQuoteCountById(@Param("id") Long tweetId);

    /**
     * 更新reply统计数量
     *
     * @param tweetId
     * @return
     */
    //@Modifying
    //Boolean updatePublicMetricsReplyCountById(@Param("id") Long tweetId);

    /**
     * 更新like统计数量
     *
     * @param tweetId
     * @return
     */
    //@Modifying
    //Boolean updatePublicMetricsLikeCountById(@Param("id") Long tweetId);

}
