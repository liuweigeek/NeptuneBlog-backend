package com.scott.neptune.userclient.config;

import com.scott.neptune.userclient.convertor.RelationshipConnectionMvcConverter;
import com.scott.neptune.userclient.convertor.UserGenderMvcConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: scott
 * @Date: 2020/2/1 18:10
 * @Description: Web配置
 */
@Configuration
public class UserWebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new RelationshipConnectionMvcConverter());
        registry.addConverter(new UserGenderMvcConverter());
    }
}
