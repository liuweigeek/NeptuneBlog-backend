package com.scott.neptune.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/30 14:16
 * @Description: NeptuneBlog
 */
@Data
@Component
@ConfigurationProperties(prefix = "neptune.oss.minio")
public class MinioProperties {

    /**
     * Minio host
     */
    private String endpoint;

    /**
     * Minio accessKey
     */
    private String accessKey;

    /**
     * Minio secretKey
     */
    private String secretKey;

    /**
     * 默认bucket
     */
    private String bucket;

}
