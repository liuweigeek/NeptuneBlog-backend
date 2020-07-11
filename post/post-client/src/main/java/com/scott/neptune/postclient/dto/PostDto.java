package com.scott.neptune.postclient.dto;

import com.scott.neptune.common.dto.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 14:11
 * @Description:
 */
@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = false, of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "post", description = "推文对象")
public class PostDto extends Pageable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * 发送人ID
     */
    @ApiModelProperty(hidden = true)
    private String userId;

    /**
     * 发送内容
     */
    @NotEmpty(message = "发送内容不可为空")
    @ApiModelProperty(name = "content", value = "内容")
    private String content;

    /**
     * 发送设备
     */
    @ApiModelProperty(name = "device", value = "设备")
    private String device;

    /**
     * 发送时间
     */
    @ApiModelProperty(hidden = true)
    private Date createDate;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateDate;

    /**
     * 发送人Id
     */
    @ApiModelProperty(name = "authorId", value = "发送人Id")
    private String authorId;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "authorUsername", value = "用户名")
    private String authorUsername;

    /**
     * 昵称
     */
    @ApiModelProperty(name = "authorNickname", value = "昵称")
    private String authorNickname;

    /**
     * 性别
     */
    @ApiModelProperty(name = "authorSex", value = "性别")
    private Integer authorSex;

    /**
     * 头像
     */
    @ApiModelProperty(name = "authorAvatar", value = "头像")
    private String authorAvatar;

}
