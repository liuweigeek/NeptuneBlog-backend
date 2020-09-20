package com.scott.neptune.tweetserver.domain.entity;

import com.scott.neptune.tweetclient.enumerate.TweetTypeEnum;
import com.scott.neptune.tweetserver.domain.listener.TweetAuditingListener;
import com.scott.neptune.tweetserver.domain.valueobject.TweetPublicMetricsValObj;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_tweet")
public class TweetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 发送内容
     */
    private String text;

    /**
     * 作者ID
     */
    @Column(name = "author_id")
    private Long authorId;

    /**
     * 发送时间
     */
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    /**
     * 发送设备
     */
    private String source;

    /**
     * tweet类型
     */
    @Enumerated(EnumType.STRING)
    private TweetTypeEnum type;

    /**
     * 附加信息
     */
    /*@Embedded
    private TweetEntitiesValObj entities;*/

    /**
     * 对话ID，如果当前Tweet是一条回复，则指向另一条Tweet
     */
    @Column(name = "conversation_id")
    private Long conversationId;

    /**
     * 如果当前Tweet是一条回复，则该字段指向原Tweet的Author ID
     */
    @Column(name = "in_reply_to_user_id")
    private Long inReplyToUserId;

    /**
     * 被引用的Tweet
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "referenced_tweet_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TweetEntity referencedTweet;

    /**
     * 互动统计数据
     */
    @Embedded
    private TweetPublicMetricsValObj publicMetrics;

    /*@Transient
    private ConnectionStatusValObj connectionStatus;*/

}
