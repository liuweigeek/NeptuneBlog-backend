package com.scott.neptune.authenticationclient.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: scott
 * @Date: 2020/7/12 19:40
 * @Description: JWT相关配置
 */
@Data
@Component
@ConfigurationProperties(prefix = JwtProperties.PREFIX)
public class JwtProperties {

    public static final String PREFIX = "jwt";

    /**
     * JWT验证请求头
     */
    private String header = "Authorization";

    /**
     * 密匙头
     */
    private String headerPrefix = "Bearer";

    /**
     * 密匙
     */
    private String secret = "defaultSecret";

    /**
     * 超时时间
     */
    private Long expiration = 604800L;

}
