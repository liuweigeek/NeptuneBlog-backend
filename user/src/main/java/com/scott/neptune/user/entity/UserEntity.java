package com.scott.neptune.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.scott.neptune.userapi.dto.UserDto;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:03
 * @Description: 用户
 */
@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class UserEntity implements Serializable {

    @Version
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 邮箱地址
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户名
     */

    @TableField(value = "username")
    private String username;

    /**
     * 用户昵称
     */

    @TableField(value = "nickname")
    private String nickname;

    /**
     * 头像列表
     */
    @TableField(exist = false)
    @Builder.Default
    private List<UserAvatarEntity> avatarList = Collections.emptyList();

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 出生日期
     */
    @TableField(value = "birthday", jdbcType = JdbcType.TIMESTAMP)
    private Date birthday;

    /**
     * 性别
     * {@link UserDto.SexEnum}
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 注册时间
     */
    @TableField(value = "register_date", jdbcType = JdbcType.TIMESTAMP, fill = FieldFill.INSERT)
    private Date registerDate;

    /**
     * 最近登录时间
     */
    @TableField(value = "login_date", jdbcType = JdbcType.TIMESTAMP)
    private Date loginDate;

    /**
     * 语言
     */
    @TableField(value = "lang_key")
    private String langKey;

    /**
     * 登录token信息
     */
    @TableField(value = "token")
    private String token;

    /**
     * 关系状态
     * {@link UserDto.RelationEnum}
     */
    @TableField(exist = false)
    private Integer relation;

    /**
     * 正在关注用户数量
     */
    @TableField(exist = false)
    private Integer followingCount;

    /**
     * 关注者数量
     */
    @TableField(exist = false)
    private Integer followerCount;

}
