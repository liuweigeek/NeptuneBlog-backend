package com.scott.neptune.common.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
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
    @Value("${neptune.oss.minio.endpoint}")
    private String endpoint;

    /**
     * Minio accessKey
     */
    @Value("${neptune.oss.minio.access-key}")
    private String accessKey;

    /**
     * Minio secretKey
     */
    @Value("${neptune.oss.minio.secret-key}")
    private String secretKey;

    /**
     * 默认bucket
     */
    @Value("${neptune.oss.minio.bucket}")
    private String bucket;

}
