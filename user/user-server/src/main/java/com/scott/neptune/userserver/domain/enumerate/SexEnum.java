package com.scott.neptune.userserver.domain.enumerate;

import com.scott.neptune.userclient.dto.UserDto;
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

    public UserDto.SexEnum getEnum(int code) {
        for (UserDto.SexEnum sexEnum : UserDto.SexEnum.values()) {
            if (sexEnum.getCode() == code) {
                return sexEnum;
            }
        }
        return null;
    }
}