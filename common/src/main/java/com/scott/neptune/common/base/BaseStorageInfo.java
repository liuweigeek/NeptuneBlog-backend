package com.scott.neptune.common.base;

import com.scott.neptune.common.property.MinioProperties;
import com.scott.neptune.common.util.SpringContextUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * @Author: scott
 * @Email: <a href="wliu@fleetup.com">scott</a>
 * @Date: 2020/8/11 15:44
 * @Description:
 */
public abstract class BaseStorageInfo {

    private final MinioProperties minioProperties = SpringContextUtils.containsBean("minioProperties") ?
            SpringContextUtils.getBean(MinioProperties.class) : null;

    public abstract String getBusinessType();

    public abstract String getFolder(String... args);

    public String getBucket() {
        return Optional.ofNullable(minioProperties)
                .map(MinioProperties::getBucket)
                .orElse("neptune-blog");
    }

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
            for (BusinessTypeEnum businessTypeEnum : BusinessTypeEnum.values()) {
                if (businessTypeEnum.getCode() == code) {
                    return businessTypeEnum;
                }
            }
            return null;
        }

        public static BusinessTypeEnum getEnumOrDefault(int code) {
            return Optional.ofNullable(getEnum(code)).orElse(BusinessTypeEnum.DEFAULT);
        }
    }
}
