package com.scott.neptune.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/11 15:44
 * @Description:
 */
public abstract class BaseStorageInfo {

    protected final String SEPARATOR = "/";

    public abstract String getBusinessType();

    public abstract String buildFolder();

    @Getter
    @AllArgsConstructor
    public enum BusinessTypeEnum {

        /**
         * default
         */
        DEFAULT(1, "default", "default"),

        /**
         * user's avatar image
         */
        AVATAR(2, "avatar", "user_avatar"),

        /**
         * background image of user's main page
         */
        USER_BACKGROUND(3, "user background", "user_background"),

        /**
         * image of tweet
         */
        TWEET_IMAGE(4, "image of tweet", "tweet_image");

        private final int code;
        private final String name;
        private final String folder;

        public static BusinessTypeEnum getEnum(int code) {
            return Arrays.stream(BusinessTypeEnum.values())
                    .filter(businessType -> businessType.getCode() == code)
                    .findFirst()
                    .orElse(null);
        }

        public static BusinessTypeEnum getEnumOrDefault(int code) {
            return Optional.ofNullable(getEnum(code)).orElse(BusinessTypeEnum.DEFAULT);
        }
    }
}
