package com.scott.neptune.userclient.command;

import com.scott.neptune.common.command.OffsetPageCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/15 20:08
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "friendship query request", description = "用户关系请求")
public class FriendshipQueryRequest extends OffsetPageCommand {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;
}
