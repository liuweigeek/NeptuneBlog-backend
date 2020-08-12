package com.scott.neptune.userserver.model;

import com.scott.neptune.common.base.BaseStorageInfo;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2020/8/11 17:01
 * @Description:
 */
public class UserBackgroundStorageInfo extends BaseStorageInfo {

    private final long userId;

    public UserBackgroundStorageInfo(long userId) {
        this.userId = userId;
    }

    @Override
    public String getBusinessType() {
        return BusinessTypeEnum.USER_BACKGROUND.getName();
    }

    @Override
    public String buildFolder() {
        return "user-background" + SEPARATOR + userId;
    }
}
