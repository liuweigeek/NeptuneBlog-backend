package com.scott.neptune.file.config;

import com.scott.neptune.file.model.AvatarProp;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 15:08
 * @Description: NeptuneBlog
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "file.avatar")
public class AvatarProps {

    private String extensionName;

    private List<AvatarProp> sizes = new ArrayList<>();

}
