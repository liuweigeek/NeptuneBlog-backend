package com.scott.neptune.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 15:08
 * @Description: NeptuneBlog
 */
@Data
@Component
@ConfigurationProperties(prefix = "neptune.file")
public class FileProps {

    private String tempFolder;

}
