package com.scott.neptune.common.dto;

import io.swagger.annotations.ApiModelProperty;
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

    /**
     * 当前页码
     */
    @ApiModelProperty(name = "current", value = "当前页码")
    private int current = 1;

    /**
     * 单页数据量
     */
    @ApiModelProperty(name = "size", value = "单页数据量")
    private int size = 30;
}
