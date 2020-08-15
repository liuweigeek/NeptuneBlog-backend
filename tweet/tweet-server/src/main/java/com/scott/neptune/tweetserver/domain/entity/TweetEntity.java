package com.scott.neptune.tweetserver.domain.entity;

import com.scott.neptune.tweetserver.domain.listener.TweetAuditingListener;
import com.scott.neptune.tweetserver.domain.valueobject.TweetCount;
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
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
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
     * 发送人信息
     */
    @Transient
    private UserDto user;

    /**
     * 发送时间
     */
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Column(name = "is_quote_status")
    private Boolean isQuoteStatus;

    @Column(name = "quoted_status_id")
    private Long quotedStatusId;

    @Transient
    private TweetEntity quotedStatus;

    /**
     * 统计数据
     */
    @Embedded
    private TweetCount count;

    //coming soon
    /*@Column(name = "in_reply_to_status_id")
    private Long inReplyToStatusId;

    @Column(name = "in_reply_to_user_id")
    private Long inReplyToUserId;

    @Column(name = "in_reply_to_screen_name")
    private String inReplyToScreenName;*/

}
