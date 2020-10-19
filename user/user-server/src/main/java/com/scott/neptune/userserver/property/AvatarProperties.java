package com.scott.neptune.userserver.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/29 15:08
 * @Description: NeptuneBlog
 */
@Data
@Component
@ConfigurationProperties(prefix = "neptune.user.avatar")
public class AvatarProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private String extension;

    private AvatarSizeValObj small;

    private AvatarSizeValObj medium;

    private AvatarSizeValObj large;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AvatarSizeValObj implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;
        private Integer width;
        private Integer height;
    }
}