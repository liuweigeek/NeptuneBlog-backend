package com.scott.neptune.tweetserver.repository;

import com.scott.neptune.tweetserver.domain.valueobject.TweetPublicMetricsValObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 15:39
 * @Description:
 */
public interface TweetPublicMetricsRepository extends JpaRepository<TweetPublicMetricsValObj, Long>,
        JpaSpecificationExecutor<TweetPublicMetricsValObj> {

    /**
     * 更新retweet统计数量
     *
     * @param tweetId
     * @return
     */
    @Modifying
    @Query(value = "update tb_tweet_public_metrics t\n" +
            "    left join (select id, count(1) count from tb_tweet where type = 'retweeted' group by id) m on m.id = t.tweet_id\n" +
            "set t.retweet_count = m.count\n" +
            "where t.tweet_id = :tweetId", nativeQuery = true)
    int updateRetweetCountByTweetId(@Param("tweetId") Long tweetId);

    /**
     * 更新quote统计数量
     *
     * @param tweetId
     * @return
     */
    @Modifying
    @Query(value = "update tb_tweet_public_metrics t\n" +
            "    left join (select id, count(1) count from tb_tweet where type = 'quoted' group by id) m on m.id = t.tweet_id\n" +
            "set t.quote_count = m.count\n" +
            "where t.tweet_id = :tweetId", nativeQuery = true)
    int updateQuoteCountByTweetId(@Param("tweetId") Long tweetId);

    /**
     * 更新reply统计数量
     *
     * @param tweetId
     * @return
     */
    @Modifying
    @Query(value = "update tb_tweet_public_metrics t\n" +
            "    left join (select id, count(1) count from tb_tweet where type = 'reply' group by id) m on m.id = t.tweet_id\n" +
            "set t.reply_count = m.count\n" +
            "where t.tweet_id = :tweetId", nativeQuery = true)
    int updateReplyCountByTweetId(@Param("tweetId") Long tweetId);

    /**
     * 更新like统计数量
     *
     * @param tweetId
     * @return
     */
    @Modifying
    @Query(value = "update tb_tweet_public_metrics t\n" +
            "    left join (select tweet_id, count(1) count from tb_like group by tweet_id) l on l.tweet_id = t.tweet_id\n" +
            "set t.like_count = l.count\n" +
            "where t.tweet_id = :tweetId", nativeQuery = true)
    int updateLikeCountByTweetId(@Param("tweetId") Long tweetId);

}
