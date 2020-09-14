package com.scott.neptune.userclient.convertor;

import com.scott.neptune.userclient.enumerate.GenderEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;


/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/17 00:18
 * @Description:
 */
@Slf4j
public class UserGenderMvcConverter implements Converter<String, GenderEnum> {
    @Override
    public GenderEnum convert(String value) {
        return GenderEnum.getEnum(value);
    }
}