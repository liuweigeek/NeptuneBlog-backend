package com.scott.neptune.tweetclient.command;

import com.scott.neptune.common.command.OffsetPageCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/15 20:08
 * @Description:
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "tweet search request", description = "推文搜索请求")
public class TweetSearchRequest extends OffsetPageCommand {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @ApiModelProperty(value = "关键字", required = true)
    private String q;
}
