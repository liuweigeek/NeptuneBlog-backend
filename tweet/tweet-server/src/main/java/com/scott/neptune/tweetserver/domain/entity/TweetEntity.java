package com.scott.neptune.tweetserver.domain.entity;

import com.scott.neptune.tweetserver.domain.listener.TweetAuditingListener;
import com.scott.neptune.tweetserver.domain.valueobject.ConnectionStatusValObj;
import com.scott.neptune.tweetserver.domain.valueobject.QuotedValObj;
import com.scott.neptune.tweetserver.domain.valueobject.ReplyValObj;
import com.scott.neptune.tweetserver.domain.valueobject.RetweetedValObj;
import com.scott.neptune.tweetserver.domain.valueobject.TweetCountValObj;
import com.scott.neptune.tweetserver.domain.valueobject.TweetEntitiesValObj;
import com.scott.neptune.tweetserver.domain.valueobject.TweetExtendedEntitiesValObj;
import com.scott.neptune.userclient.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/23 14:11
 * @Description: 推文
 */
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(TweetAuditingListener.class)
@Table(name = "t_tweet")
public class TweetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 作者ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 发送内容
     */
    private String text;

    /**
     * 发送设备
     */
    private String source;

    /**
     * 发送时间
     */
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    /**
     * 发送人信息
     */
    @Transient
    private UserDto user;

    /**
     * 本条Tweet转发的Retweet
     */
    @Embedded
    private RetweetedValObj retweeted;

    /**
     * 本条Tweet引用的Retweet
     */
    @Embedded
    private QuotedValObj quotedTweet;

    /**
     * 如果本条Tweet是一条回复，则包含回复信息
     */
    @Embedded
    private ReplyValObj reply;

    /**
     * 互动统计数据
     */
    @Embedded
    private TweetCountValObj count;

    /**
     * 附加信息
     */
    @Embedded
    private TweetEntitiesValObj entities;

    /**
     * TODO is this necessary?
     */
    @Embedded
    private TweetExtendedEntitiesValObj extendedEntities;

    @Transient
    private ConnectionStatusValObj connectionStatus;

}
