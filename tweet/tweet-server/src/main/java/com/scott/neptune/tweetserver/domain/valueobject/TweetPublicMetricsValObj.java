package com.scott.neptune.tweetserver.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
@Embeddable
public class TweetPublicMetricsValObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "retweet_count")
    private Long retweetCount;

    @Column(name = "quote_count")
    private Long quoteCount;

    @Column(name = "reply_count")
    private Long replyCount;

    @Column(name = "like_count")
    private Long likeCount;
}
