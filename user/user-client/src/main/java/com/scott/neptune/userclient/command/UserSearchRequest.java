package com.scott.neptune.userclient.command;

import com.scott.neptune.common.command.OffsetPageCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/15 20:08
 * @Description:
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "user search request", description = "用户搜索请求")
public class UserSearchRequest extends OffsetPageCommand {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @ApiModelProperty(value = "关键字", required = true)
    private String q;
}
