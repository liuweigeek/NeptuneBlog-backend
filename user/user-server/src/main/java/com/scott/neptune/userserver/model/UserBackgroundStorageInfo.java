package com.scott.neptune.userserver.model;

import com.scott.neptune.common.base.BaseStorageInfo;

/**
 * @Author: scott
 * @Email: <a href="wliu@fleetup.com">scott</a>
 * @Date: 2020/8/11 17:01
 * @Description:
 */
public class UserBackgroundStorageInfo extends BaseStorageInfo {
    @Override
    public String getBusinessType() {
        return "avatar";
    }

    @Override
    public String getFolder(String... args) {
        return "user-avatar";
    }
}
