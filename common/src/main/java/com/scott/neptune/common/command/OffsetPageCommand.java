package com.scott.neptune.common.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 22:36
 * @Description:
 */
@Data
@ApiModel(value = "offset page command", description = "分页请求")
public class OffsetPageCommand {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "偏移量")
    private long offset = 0;

    @ApiModelProperty(value = "条目数")
    private int limit = 20;

}
