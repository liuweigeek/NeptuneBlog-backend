package com.scott.neptune.user.entity;

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
public class User implements Serializable {

    @Id
    @Column(length = 32)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不可为空")
    @Column(name = "username", unique = true, length = 32)
    private String username;

    /**
     * 真实姓名
     */
    @NotEmpty(message = "真实姓名不可为空")
    @Column(name = "real_name", length = 32)
    private String realName;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不可为空")
    @Column(name = "password", length = 64)
    private String password;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不可为空")
    @Column(name = "age", precision = 3)
    private Integer age;

    /**
     * 性别
     * {@link SexEnum}
     */
    @NotNull(message = "性别不可为空")
    @Column(name = "sex", columnDefinition = "tinyint", precision = 1)
    private Integer sex;

    /**
     * 注册时间
     */
    @Column(name = "register_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;

    /**
     * 登录时间
     */
    @Column(name = "login_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;

    /**
     * 登录token信息
     */
    @Column(name = "token", length = 256)
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
