package com.scott.neptune.user.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/11/3 17:38
 * @Description: NeptuneBlog
 */
@Configuration
public class FeignMultipartSupportConfig {

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder();
    }
}
