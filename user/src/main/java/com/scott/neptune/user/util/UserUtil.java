package com.scott.neptune.user.util;

import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.user.entity.User;
import org.springframework.beans.BeanUtils;

/**
 * 用户工具类
 */
public class UserUtil {

    /**
     * 将User实体转换为UserDto
     *
     * @param user 用户对象
     * @return DTO对象
     */
    public static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    /**
     * 生成token信息
     *
     * @param user
     * @return
     */
    public static String generateTokenByUser(User user) {

        return String.format("%s:%s", Constant.Login.CURRENT_USER, user.getId());
    }
}
