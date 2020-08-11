package com.scott.neptune.userserver.model;

import com.scott.neptune.common.base.BaseStorageInfo;

/**
 * @Author: scott
 * @Email: <a href="wliu@fleetup.com">scott</a>
 * @Date: 2020/8/11 17:01
 * @Description:
 */
public class UserAvatarStorageInfo extends BaseStorageInfo {

    private final long userId;
    private final String size;

    public UserAvatarStorageInfo(long userId, String size) {
        this.userId = userId;
        this.size = size;
    }

    @Override
    public String getBusinessType() {
        return BusinessTypeEnum.AVATAR.getName();
    }

    @Override
    public String buildFolder() {
        return "user_avatar" + SEPARATOR + userId + SEPARATOR + size;
    }
}
