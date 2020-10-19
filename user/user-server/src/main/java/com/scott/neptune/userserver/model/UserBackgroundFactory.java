package com.scott.neptune.userserver.model;

import com.scott.neptune.common.base.BaseStorageInfo;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/11 17:01
 * @Description:
 */
public class UserBackgroundFactory extends BaseStorageInfo {

    public static String buildFolder(final long userId) {
        return "user-background" + SEPARATOR + userId;
    }
}
