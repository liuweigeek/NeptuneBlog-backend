package com.scott.neptune.userserver.config;

import com.scott.neptune.userserver.convertor.UserGenderMvcConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/9/3 16:41
 * @Description:
 */
@Configuration
public class UserWebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserGenderMvcConverter());
    }
}
