package com.scott.neptune.common.config;

import com.scott.neptune.common.property.MinioProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/4 00:50
 * @Description: NeptuneBlog
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "neptune.oss.minio", name = "endpoint")
public class MinioConfig {

    private final MinioProperties minioProperties;

    public MinioConfig(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Bean
    public MinioClient minioClient() {
        try {
            MinioClient minioClient = new MinioClient(minioProperties.getEndpoint(),
                    minioProperties.getAccessKey(),
                    minioProperties.getSecretKey());

            //检测用户上传文件的容器是否已经创建
            boolean bucketExists = minioClient.bucketExists(minioProperties.getBucket());
            if (!bucketExists) {
                minioClient.makeBucket(minioProperties.getBucket());
                String readOnlyPolicy = FileUtils.readFileToString(new ClassPathResource("config/neptune-blog.ReadOnlyPolicy.json").getFile(),
                        StandardCharsets.UTF_8);
                log.info("readOnlyPolicy: {}", readOnlyPolicy);
                minioClient.setBucketPolicy(minioProperties.getBucket(), readOnlyPolicy);
            }
            return minioClient;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
