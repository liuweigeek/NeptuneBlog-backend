package com.scott.neptune.authenticationclient.config;

import com.scott.neptune.authenticationclient.resolver.AuthUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: scott
 * @Date: 2020/2/1 18:10
 * @Description: Web配置
 */
@Configuration
public class AuthenticationWebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthUserArgumentResolver());
    }

}
