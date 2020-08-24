package com.scott.neptune.userclient.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/7 10:04
 * @Description: 性别
 */
@AllArgsConstructor
public enum GenderEnum {

    /**
     * 男
     */
    MALE(1),

    /**
     * 女
     */
    FEMALE(2);

    @Getter
    private final int code;

    public static GenderEnum getEnum(int code) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getCode() == code) {
                return genderEnum;
            }
        }
        return null;
    }
}