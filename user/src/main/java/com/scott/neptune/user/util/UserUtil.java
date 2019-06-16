package com.scott.neptune.user.util;

import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.user.entity.UserEntity;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;
import java.util.UUID;

/**
 * 用户工具类
 */
public class UserUtil {

    /**
     * 将User实体转换为UserDto
     *
     * @param userEntity 用户对象
     * @return DTO对象
     */
    public static UserDto convertToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        userDto.setPassword(null);
        return userDto;
    }

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
                UUID.randomUUID());
    }
}
