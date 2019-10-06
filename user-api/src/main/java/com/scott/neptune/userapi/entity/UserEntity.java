package com.scott.neptune.userapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value = "user", description = "user entity")
public class UserEntity implements Serializable {

    @Version
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(type = IdType.UUID)
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * 邮箱地址
     */
    @NotEmpty(message = "邮箱地址不可为空", groups = {Login.class, Register.class})
    @Email(message = "邮箱格式不正确", groups = {Login.class, Register.class})
    @TableField(value = "email")
    @ApiModelProperty(name = "email", value = "邮箱地址", required = true)
    private String email;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不可为空", groups = Register.class)
    @TableField(value = "username")
    @ApiModelProperty(name = "username", value = "用户名", required = true)
    private String username;

    /**
     * 真实姓名
     */
    //@NotEmpty(message = "真实姓名不可为空", groups = Register.class)
    @TableField(value = "real_name")
    @ApiModelProperty(name = "realName", value = "真实姓名")
    private String realName;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不可为空", groups = {Login.class, Register.class})
    @Length(min = 8, max = 16, message = "密码长度应该为8到16位", groups = Register.class)
    @TableField(value = "password")
    @ApiModelProperty(name = "password", value = "密码", required = true)
    private String password;

    /**
     * 出生日期
     */
    @NotNull(message = "出生日期", groups = Register.class)
    @TableField(value = "birthday", jdbcType = JdbcType.TIMESTAMP)
    @ApiModelProperty(name = "birthday", value = "出生日期")
    private Date birthday;

    /**
     * 性别
     * {@link SexEnum}
     */
    @NotNull(message = "性别不可为空", groups = Register.class)
    @TableField(value = "sex")
    @ApiModelProperty(name = "sex", value = "性别")
    private Integer sex;

    /**
     * 注册时间
     */
    @TableField(value = "register_date", jdbcType = JdbcType.TIMESTAMP, fill = FieldFill.INSERT)
    @ApiModelProperty(hidden = true)
    private Date registerDate;

    /**
     * 登录时间
     */
    @TableField(value = "login_date", jdbcType = JdbcType.TIMESTAMP)
    @ApiModelProperty(hidden = true)
    private Date loginDate;

    /**
     * 语言
     */
    @TableField(value = "lang_key")
    @ApiModelProperty(name = "langKey", value = "语言")
    private String langKey;

    /**
     * 登录token信息
     */
    @TableField(value = "token")
    @ApiModelProperty(hidden = true)
    private String token;

    /**
     * 性别
     */
    @AllArgsConstructor
    public enum SexEnum {

        /**
         * 男
         */
        MALE(1),

        /**
         * 女
         */
        FEMALE(2);

        @Getter
        private int code;

        public SexEnum getEnum(int code) {
            for (SexEnum sexEnum : SexEnum.values()) {
                if (sexEnum.getCode() == code) {
                    return sexEnum;
                }
            }
            return null;
        }
    }

    public interface Register extends Default {
    }

    public interface Login extends Default {
    }

}
