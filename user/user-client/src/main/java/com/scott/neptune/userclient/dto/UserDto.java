package com.scott.neptune.userclient.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "user", description = "用户对象")
public class UserDto {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * 邮箱地址
     */
    @NotEmpty(message = "邮箱地址不可为空", groups = {Login.class, Register.class})
    @Email(message = "邮箱格式不正确", groups = {Login.class, Register.class})
    @ApiModelProperty(value = "邮箱地址", required = true)
    private String email;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不可为空", groups = Register.class)
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不可为空", groups = Register.class)
    @ApiModelProperty(value = "昵称")
    private String name;

    /**
     * 自我介绍
     */
    @ApiModelProperty(value = "自我介绍")
    private String description;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不可为空", groups = {Login.class, Register.class})
    @Length(min = 8, max = 16, message = "密码长度应该为8到16位", groups = Register.class)
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    /**
     * 出生日期
     */
    @NotNull(message = "出生日期", groups = Register.class)
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    /**
     * 性别
     */
    @NotNull(message = "性别不可为空", groups = Register.class)
    @ApiModelProperty(value = "性别")
    private Integer gender;

    /**
     * 注册时间
     */
    @ApiModelProperty(hidden = true)
    private Date createAt;

    /**
     * 最近登录时间
     */
    @ApiModelProperty(hidden = true)
    private Date loginDate;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言")
    private String lang;

    /**
     * 小尺寸头像
     */
    @ApiModelProperty(value = "小尺寸头像")
    private String smallAvatar;

    /**
     * 中等尺寸头像
     */
    @ApiModelProperty(value = "正常尺寸头像")
    private String mediumAvatar;

    /**
     * 大尺寸头像
     */
    @ApiModelProperty(value = "大尺寸头像")
    private String largeAvatar;

    /**
     * 正在关注用户数量
     */
    @ApiModelProperty(hidden = true)
    private Integer friendsCount;

    /**
     * 关注者数量
     */
    @ApiModelProperty(hidden = true)
    private Integer followersCount;

    public interface Register extends Default {
    }

    public interface Login extends Default {
    }

}
