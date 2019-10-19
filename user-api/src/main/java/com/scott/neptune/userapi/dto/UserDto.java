package com.scott.neptune.userapi.dto;

import com.scott.neptune.common.dto.BaseDto;
import com.scott.neptune.common.dto.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:39
 * @Description: UserDto对象
 */
@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = false, of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "user", description = "用户对象")
public class UserDto extends Pageable implements BaseDto {

    /**
     * Id
     */
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * 邮箱地址
     */
    @NotEmpty(message = "邮箱地址不可为空", groups = {Login.class, Register.class})
    @Email(message = "邮箱格式不正确", groups = {Login.class, Register.class})
    @ApiModelProperty(name = "email", value = "邮箱地址", required = true)
    private String email;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不可为空", groups = Register.class)
    @ApiModelProperty(name = "username", value = "用户名", required = true)
    private String username;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不可为空", groups = Register.class)
    @ApiModelProperty(name = "nickname", value = "昵称")
    private String nickname;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不可为空", groups = {Login.class, Register.class})
    @Length(min = 8, max = 16, message = "密码长度应该为8到16位", groups = Register.class)
    @ApiModelProperty(name = "password", value = "密码", required = true)
    private String password;

    /**
     * 出生日期
     */
    @NotNull(message = "出生日期", groups = Register.class)
    @ApiModelProperty(name = "birthday", value = "出生日期")
    private Date birthday;

    /**
     * 性别
     */
    @NotNull(message = "性别不可为空", groups = Register.class)
    @ApiModelProperty(name = "sex", value = "性别")
    private Integer sex;

    /**
     * 注册时间
     */
    @ApiModelProperty(hidden = true)
    private Date registerDate;

    /**
     * 最近登录时间
     */
    @ApiModelProperty(hidden = true)
    private Date loginDate;

    /**
     * 语言
     */
    @ApiModelProperty(name = "langKey", value = "语言")
    private String langKey;

    /**
     * 登录Token信息
     */
    @ApiModelProperty(hidden = true)
    private String token;

    /**
     * 小尺寸头像
     */
    @ApiModelProperty(name = "smallAvatar", value = "小尺寸头像")
    private String smallAvatar;

    /**
     * 正常尺寸头像
     */
    @ApiModelProperty(name = "normalAvatar", value = "正常尺寸头像")
    private String normalAvatar;

    /**
     * 大尺寸头像
     */
    @ApiModelProperty(name = "largeAvatar", value = "大尺寸头像")
    private String largeAvatar;

    public interface Register extends Default {
    }

    public interface Login extends Default {
    }
}
