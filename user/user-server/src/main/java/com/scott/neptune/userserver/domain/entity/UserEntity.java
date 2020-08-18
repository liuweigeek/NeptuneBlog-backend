package com.scott.neptune.userserver.domain.entity;

import com.scott.neptune.userclient.enumerate.GenderEnum;
import com.scott.neptune.userserver.domain.listener.UserAuditingListener;
import com.scott.neptune.userserver.domain.valueobject.ConnectionCountValObj;
import com.scott.neptune.userserver.domain.valueobject.FriendshipCountValObj;
import com.scott.neptune.userserver.domain.valueobject.UserAvatarValObj;
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
     * 邮箱地址
     */
    private String email;

    /**
     * 用户名
     */
    private String screenName;

    /**
     * 用户昵称
     */
    private String name;

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
     * avatar
     */
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    /**
     * banner
     */
    @Column(name = "profile_banner_url")
    private String profileBannerUrl;

    /**
     * 注册时间
     */
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    /**
     * 最近登录时间
     */
    @Column(name = "login_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;

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
    private FriendshipCountValObj friendshipCount;

    @Embedded
    private ConnectionCountValObj connectionCount;
}
