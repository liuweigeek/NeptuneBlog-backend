package com.scott.neptune.file.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/30 14:16
 * @Description: NeptuneBlog
 */
@Data
@Component
public class MinioProperties {

    /**
     * Minio host
     */
    @Value("${minio.endpoint}")
    private String endpoint;

    /**
     * Minio accessKey
     */
    @Value("${minio.access-key}")
    private String accessKey;

    /**
     * Minio secretKey
     */
    @Value("${minio.secret-key}")
    private String secretKey;

    /**
     * 默认bucket
     */
    @Value("${minio.bucket}")
    private String bucket;

}
