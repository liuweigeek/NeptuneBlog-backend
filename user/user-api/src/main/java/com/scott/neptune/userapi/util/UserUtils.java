package com.scott.neptune.userapi.util;

import com.scott.neptune.userapi.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/3 23:10
 * @Description: NeptuneBlog
 */
@Component
public class UserUtils {

    /**
     * 根据用户信息获取区域信息
     *
     * @param userDto
     * @return
     */
    public Locale getLocaleFromUser(UserDto userDto) {
        if (userDto == null || StringUtils.isBlank(userDto.getLangKey())) {
            return Locale.getDefault();
        }
        return Locale.forLanguageTag(userDto.getLangKey());
    }
}
