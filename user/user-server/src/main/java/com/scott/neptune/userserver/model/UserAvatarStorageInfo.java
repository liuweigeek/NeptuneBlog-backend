package com.scott.neptune.userserver.model;

import com.scott.neptune.common.base.BaseStorageInfo;
import lombok.RequiredArgsConstructor;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/11 17:01
 * @Description:
 */
@RequiredArgsConstructor
public class UserAvatarStorageInfo extends BaseStorageInfo {

    private final long userId;
    private final String size;

    @Override
    public String getBusinessType() {
        return BusinessTypeEnum.AVATAR.getName();
    }

    @Override
    public String buildFolder() {
        return "user_avatar" + SEPARATOR + userId + SEPARATOR + size;
    }
}
