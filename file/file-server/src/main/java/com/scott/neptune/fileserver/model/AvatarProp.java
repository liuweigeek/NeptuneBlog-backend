package com.scott.neptune.fileserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 15:20
 * @Description: NeptuneBlog
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvatarProp {

    private Integer sizeType;
    private Integer width;
    private Integer height;
}