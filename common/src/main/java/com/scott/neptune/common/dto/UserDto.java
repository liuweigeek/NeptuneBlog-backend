package com.scott.neptune.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

/**
 * UserDto对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDto {

    /**
     * Id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     * {@link SexEnum}
     */
    private Integer sex;

    /**
     * 注册时间
     */
    private Date registerDate;

    /**
     * 最近登录时间
     */
    private Date loginDate;

    /**
     * 语言
     */
    private String langKey;

    /**
     * 登录Token信息
     */
    private String token;


    @AllArgsConstructor
    public enum SexEnum {

        /**
         * male
         */
        MALE(1),
        /**
         * female
         */
        FEMALE(2),

        /**
         * unknown
         */
        UNKNOWN(3);

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
