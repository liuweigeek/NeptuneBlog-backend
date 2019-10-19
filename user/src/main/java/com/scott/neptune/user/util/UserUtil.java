package com.scott.neptune.user.util;

import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.util.UUIDUtils;
import com.scott.neptune.user.entity.UserEntity;

import java.util.Calendar;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:14
 * @Description: 用户工具类
 */
public class UserUtil {

    /**
     * 生成token信息
     *
     * @param userEntity
     * @return
     */
    public static String generateTokenByUser(UserEntity userEntity) {
        return String.format("%s:%s-%s-%s", Constant.Login.CURRENT_USER,
                userEntity.getId(),
                Calendar.getInstance().getTimeInMillis(),
                UUIDUtils.generateUUID());
    }
}
