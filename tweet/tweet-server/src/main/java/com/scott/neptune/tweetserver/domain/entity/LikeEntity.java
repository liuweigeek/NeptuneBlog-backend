package com.scott.neptune.tweetserver.domain.entity;

import com.scott.neptune.tweetserver.domain.listener.TweetAuditingListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/23 14:11
 * @Description: 推文
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(TweetAuditingListener.class)
@Table(name = "tb_like")
public class LikeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private LikeId id;

    /**
     * TweetId
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("tweetId")
    @JoinColumn(name = "tweet_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TweetEntity tweet;

    /**
     * 点赞时间
     */
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    /**
     * 点赞设备
     */
    @Column(name = "source", length = 50)
    private String source;

    @Data
    @Builder
    @EqualsAndHashCode(callSuper = false, of = {"tweetId", "userId"})
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class LikeId implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long tweetId;

        private Long userId;
    }
}
