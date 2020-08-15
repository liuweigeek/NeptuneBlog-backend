package com.scott.neptune.userclient.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2020/8/7 10:04
 * @Description: 性别
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
    private final int code;

    public SexEnum getEnum(int code) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (sexEnum.getCode() == code) {
                return sexEnum;
            }
        }
        return null;
    }
}