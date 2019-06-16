package com.scott.neptune.common.util;

import com.scott.neptune.common.dto.UserDto;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * 区域工具
 */
public class LocaleUtil {

    /**
     * 根据用户信息获取区域信息
     *
     * @param userDto
     * @return
     */
    public static Locale getLocaleFromUser(UserDto userDto) {
        if (userDto == null || StringUtils.isBlank(userDto.getLangKey())) {
            return Locale.getDefault();
        }
        return Locale.forLanguageTag(userDto.getLangKey());
    }
}
