package com.scott.neptune.userclient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.scott.neptune.userclient.enumerate.GenderEnum;
import com.scott.neptune.userclient.enumerate.UserConnectionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/23 09:39
 * @Description: UserDto对象
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false, of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private Long id;

    /**
     * 邮箱地址
     */
    @NotEmpty(message = "邮箱地址不可为空", groups = {Login.class, Register.class})
    @Email(message = "邮箱格式不正确", groups = {Login.class, Register.class})
    private String email;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不可为空", groups = Register.class)
    private String username;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不可为空", groups = Register.class)
    private String name;

    /**
     * 自我介绍
     */
    private String description;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不可为空", groups = {Login.class, Register.class})
    @Length(min = 8, max = 16, message = "密码长度应该为8到16位", groups = Register.class)
    private String password;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "出生日期", groups = Register.class)
    private Date birthday;

    /**
     * 性别
     */
    @NotNull(message = "性别不可为空", groups = Register.class)
    private GenderEnum gender;

    /**
     * 注册时间
     */
    private Date createAt;

    /**
     * 语言
     */
    private String lang;

    /**
     * 小尺寸头像
     */
    private String smallAvatar;

    /**
     * 中等尺寸头像
     */
    private String mediumAvatar;

    /**
     * 大尺寸头像
     */
    private String largeAvatar;

    /**
     * 正在关注用户数量
     */
    private Integer followingCount;

    /**
     * 关注者数量
     */
    private Integer followersCount;

    @Builder.Default
    private Collection<UserConnectionEnum> connections = Lists.newArrayListWithExpectedSize(2);

    public void addConnection(UserConnectionEnum connectionEnum) {
        this.connections.add(connectionEnum);
    }

    public interface Register extends Default {
    }

    public interface Login extends Default {
    }

}
