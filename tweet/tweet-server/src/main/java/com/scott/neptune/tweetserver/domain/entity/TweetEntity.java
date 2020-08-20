package com.scott.neptune.tweetserver.domain.entity;

import com.scott.neptune.tweetserver.domain.listener.TweetAuditingListener;
import com.scott.neptune.tweetserver.domain.valueobject.ConnectionStatusValObj;
import com.scott.neptune.tweetserver.domain.valueobject.ReferencedTweetValObj;
import com.scott.neptune.tweetserver.domain.valueobject.TweetEntitiesValObj;
import com.scott.neptune.tweetserver.domain.valueobject.TweetPublicMetricsValObj;
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
     * 发送内容
     */
    private String text;

    /**
     * attachments
     */

    /**
     * 作者ID
     */
    @Column(name = "author_id")
    private Long authorId;

    /**
     * 发送人信息
     */
    @Transient
    private UserDto author;

    /**
     * 对话ID，若当前Tweet为Reply，则指向另一条Tweet
     */
    @Column(name = "conversation_id")
    private Long conversationId;

    /**
     * 发送时间
     */
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    /**
     * 附加信息
     */
    @Embedded
    private TweetEntitiesValObj entities;

    /**
     * 如果当前Tweet是一条回复，则该字段指向原Tweet的Author ID
     */
    @Column(name = "in_reply_to_user_id")
    private Long inReplyToUserId;

    /**
     * 互动统计数据
     */
    @Embedded
    private TweetPublicMetricsValObj publicMetrics;

    /**
     * 被引用的Tweet
     */
    @OneToMany(mappedBy = "referencedBy", fetch = FetchType.LAZY)
    private List<ReferencedTweetValObj> referencedTweets;

    /**
     * 发送设备
     */
    private String source;

    @Transient
    private ConnectionStatusValObj connectionStatus;

}
