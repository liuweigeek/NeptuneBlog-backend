package com.scott.neptune.fileserver.config;

import com.scott.neptune.fileserver.property.MinioProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/4 00:50
 * @Description: NeptuneBlog
 */
@Configuration
@ConditionalOnClass(MinioClient.class)
@ConditionalOnMissingBean(name = "minioClient")
@ConditionalOnProperty(prefix = "minio", name = "bucket")
@Slf4j
public class MinioConfig {

    @Resource
    private MinioProperties minioProperties;

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
