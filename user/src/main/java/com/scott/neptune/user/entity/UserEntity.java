package com.scott.neptune.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "t_user")
@ApiModel(value = "user", description = "user entity")
public class UserEntity implements Serializable {

    @Id
    @Column(length = 32)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不可为空")
    @Column(name = "username", unique = true, length = 32)
    @ApiModelProperty(name = "username", value = "用户名", required = true, dataType = "String")
    private String username;

    /**
     * 真实姓名
     */
    @NotEmpty(message = "真实姓名不可为空")
    @Column(name = "real_name", length = 32)
    @ApiModelProperty(name = "realName", value = "真实姓名", dataType = "String")
    private String realName;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不可为空")
    @Column(name = "password", length = 64)
    @ApiModelProperty(name = "password", value = "密码", required = true, dataType = "String")
    private String password;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不可为空")
    @Column(name = "age", precision = 3)
    @ApiModelProperty(name = "age", value = "年龄", dataType = "int")
    private Integer age;

    /**
     * 性别
     * {@link SexEnum}
     */
    @NotNull(message = "性别不可为空")
    @Column(name = "sex", columnDefinition = "tinyint", precision = 1)
    @ApiModelProperty(name = "sex", value = "性别", dataType = "int")
    private Integer sex;

    /**
     * 注册时间
     */
    @Column(name = "register_date")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(hidden = true)
    private Date registerDate;

    /**
     * 登录时间
     */
    @Column(name = "login_date")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(hidden = true)
    private Date loginDate;

    /**
     * 语言
     */
    @Column(name = "lang_key", length = 6)
    @ApiModelProperty(name = "langKey", value = "语言", dataType = "String")
    private String langKey;

    /**
     * 登录token信息
     */
    @Column(name = "token", length = 256)
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
}
