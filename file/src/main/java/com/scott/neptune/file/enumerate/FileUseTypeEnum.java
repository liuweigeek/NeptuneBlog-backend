package com.scott.neptune.file.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/9 17:44
 * @Description: NeptuneBlog
 */
@Getter
@AllArgsConstructor
public enum FileUseTypeEnum {

    /**
     * default
     */
    DEFAULT(1, "default", "default"),

    /**
     * user's avatar image
     */
    AVATAR(2, "avatar", "avatar"),

    /**
     * background image of user's main page
     */
    USER_BACKGROUND(3, "user background", "background"),

    /**
     * image of post
     */
    POST_IMAGE(4, "image of post", "post_image");

    private int code;
    private String name;
    private String folder;

    public static FileUseTypeEnum getDefaultIfNull(FileUseTypeEnum fileUseTypeEnum) {
        if (Objects.isNull(fileUseTypeEnum)) {
            return FileUseTypeEnum.DEFAULT;
        }
        return fileUseTypeEnum;
    }

    public static FileUseTypeEnum getEnum(int code) {
        for (FileUseTypeEnum fileUseTypeEnum : FileUseTypeEnum.values()) {
            if (fileUseTypeEnum.getCode() == code) {
                return fileUseTypeEnum;
            }
        }
        return null;
    }
}
