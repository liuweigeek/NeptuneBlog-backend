package com.scott.neptune.common.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/6 00:40
 * @Description: NeptuneBlog
 */
@Data
@ToString
public class Pageable implements Serializable {

    private int pageNumber = 1;
    private int pageSize = 30;
}
