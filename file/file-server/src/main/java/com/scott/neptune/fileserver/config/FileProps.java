package com.scott.neptune.fileserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 15:08
 * @Description: NeptuneBlog
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "file")
public class FileProps {

    private String tempFolder;

}
