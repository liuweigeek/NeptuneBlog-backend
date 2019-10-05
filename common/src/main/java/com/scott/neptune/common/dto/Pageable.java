package com.scott.neptune.common.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/6 00:40
 * @Description: NeptuneBlog
 */
@Data
@ToString
public class Pageable {

    private int pageNumber = 1;
    private int pageSize = 30;
}
