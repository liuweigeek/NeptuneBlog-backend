package com.scott.neptune.userclient.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/7 10:04
 * @Description: 性别
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    /**
     * 男
     */
    MALE("male"),

    /**
     * 女
     */
    FEMALE("female");

    @JsonValue
    private final String value;

    @JsonCreator
    public static GenderEnum getEnum(String value) {
        return Arrays.stream(GenderEnum.values())
                .filter(gender -> StringUtils.equals(gender.getValue(), value))
                .findFirst()
                .orElse(null);
    }
}