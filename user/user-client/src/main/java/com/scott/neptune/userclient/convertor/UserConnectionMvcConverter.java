package com.scott.neptune.userclient.convertor;

import com.scott.neptune.userclient.enumerate.UserConnectionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;


/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/17 00:18
 * @Description:
 */
@Slf4j
public class UserConnectionMvcConverter implements Converter<String, UserConnectionEnum> {
    @Override
    public UserConnectionEnum convert(String name) {
        return UserConnectionEnum.getEnum(name);
    }
}