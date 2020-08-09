package com.scott.neptune.userserver.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@ConfigurationProperties(prefix = "neptune.file.avatar")
public class AvatarProperties {

    private String extensionName;

    private List<AvatarValueObject> sizes = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AvatarValueObject {

        private Integer sizeType;
        private Integer width;
        private Integer height;
    }

}
