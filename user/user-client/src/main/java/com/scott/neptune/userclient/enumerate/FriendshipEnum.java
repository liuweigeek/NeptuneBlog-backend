package com.scott.neptune.userclient.enumerate;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/15 23:11
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 关系类型
 */
@Getter
@AllArgsConstructor
public enum FriendshipEnum {

    /**
     * 未关注
     */
    UN_FOLLOW(0, "not following"),

    /**
     * 正在关注
     */
    FOLLOWING(1, "following");

    private final int code;
    private final String value;

    public static FriendshipEnum getEnum(int code) {
        return Arrays.stream(FriendshipEnum.values())
                .filter(friendship -> friendship.getCode() == code)
                .findFirst()
                .orElse(null);
    }
}