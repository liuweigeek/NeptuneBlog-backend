package com.scott.neptune.userclient.command;

import com.scott.neptune.common.command.CursorCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/15 20:08
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "user search request", description = "用户搜索请求")
public class UserSearchRequest extends CursorCommand {

    @NotBlank
    @ApiModelProperty(value = "关键字", required = true)
    private String q;
}
