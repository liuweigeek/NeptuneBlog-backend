package com.scott.neptune.userclient.enumerate;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/15 23:11
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

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
        for (FriendshipEnum friendshipEnum : FriendshipEnum.values()) {
            if (friendshipEnum.getCode() == code) {
                return friendshipEnum;
            }
        }
        return null;
    }
}