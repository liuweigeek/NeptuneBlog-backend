package com.scott.neptune.userapi.dto;

import com.scott.neptune.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/8 21:39
 * @Description: NeptuneBlog
 */
@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = false, of = {"userId", "size"})
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "userAvatar", description = "用户头像")
public class UserAvatarDto implements BaseDto {

    /**
     * 用户ID
     */
    @ApiModelProperty(name = "userId", value = "用户ID")
    private String userId;

    /**
     * 图片尺寸
     */
    @ApiModelProperty(name = "size", value = "图片尺寸")
    private Integer size;

    /**
     * 图片URL
     */
    @ApiModelProperty(name = "url", value = "图片URL")
    private String url;

}
