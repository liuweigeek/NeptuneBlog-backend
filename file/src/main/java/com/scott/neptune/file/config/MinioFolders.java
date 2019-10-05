package com.scott.neptune.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/4 18:33
 * @Description: NeptuneBlog
 */
@Component
@ConfigurationProperties(prefix = "folders")
public class MinioFolders {

    private String userAvatarSm;

    private String userAvatarMd;

    private String postImageSm;

    private String postImageOrigin;

}
