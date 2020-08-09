package com.scott.neptune.fileserver.config;

import com.scott.neptune.fileserver.model.AvatarProp;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@Component
@ConfigurationProperties(prefix = "file.avatar")
public class AvatarProps {

    private String extensionName;

    private List<AvatarProp> sizes = new ArrayList<>();

}
