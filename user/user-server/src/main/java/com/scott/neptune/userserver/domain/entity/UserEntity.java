package com.scott.neptune.userserver.domain.entity;

import com.scott.neptune.userclient.enumerate.GenderEnum;
import com.scott.neptune.userserver.domain.listener.UserAuditingListener;
import com.scott.neptune.userserver.domain.valueobject.UserAvatarValObj;
import com.scott.neptune.userserver.domain.valueobject.UserPublicMetricsValObj;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/23 09:03
 * @Description: 用户
 */
@Data
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(UserAuditingListener.class)
@Table(name = "t_user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 注册时间
     */
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    /**
     * 自我介绍
     */
    private String description;

    /**
     * 密码
     */
    private String password;

    /**
     * 出生日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    /**
     * 性别
     *
     * @see GenderEnum
     */
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    /**
     * 指定Tweet ID
     */
    @Column(name = "pinned_tweet_id")
    private Long pinnedTweetId;

    /**
     * 背景图片
     */
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    /**
     * 语言
     */
    @Column(name = "lang")
    private String lang;

    /**
     * 头像列表
     */
    @Embedded
    private UserAvatarValObj userAvatarValObj;

    /**
     * 正在关注和关注者统计
     */
    @Embedded
    private UserPublicMetricsValObj friendshipCount;

}
