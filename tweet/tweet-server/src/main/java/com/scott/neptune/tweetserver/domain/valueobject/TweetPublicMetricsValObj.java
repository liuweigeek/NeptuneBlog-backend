package com.scott.neptune.tweetserver.domain.valueobject;

import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 16:20
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_tweet_public_metrics")
public class TweetPublicMetricsValObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long tweetId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TweetEntity tweet;

    @Column(name = "retweet_count")
    private Integer retweetCount;

    @Column(name = "quote_count")
    private Integer quoteCount;

    @Column(name = "reply_count")
    private Integer replyCount;

    @Column(name = "like_count")
    private Integer likeCount;
}
