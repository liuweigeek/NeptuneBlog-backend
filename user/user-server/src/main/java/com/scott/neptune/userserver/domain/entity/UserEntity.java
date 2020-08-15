package com.scott.neptune.userserver.domain.entity;

import com.scott.neptune.userserver.domain.enumerate.SexEnum;
import com.scott.neptune.userserver.domain.listener.UserAuditingListener;
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
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
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
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像列表
     */
    @Embedded
    private UserAvatarValObj userAvatarValObj;

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
     * {@link SexEnum}
     */
    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    /**
     * 注册时间
     */
    @Column(name = "register_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;

    /**
     * 最近登录时间
     */
    @Column(name = "login_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;

    /**
     * 语言
     */
    @Column(name = "lang_key")
    private String langKey;

    /**
     * 正在关注和关注者统计
     */
    @Embedded
    private FriendshipCountValObj friendshipCount;
}
