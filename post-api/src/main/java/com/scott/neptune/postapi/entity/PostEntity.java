package com.scott.neptune.postapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.scott.neptune.common.dto.BaseDto;
import com.scott.neptune.userapi.dto.UserDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 14:11
 * @Description: 推文
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_post")
public class PostEntity extends BaseDto {

    @Version
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(type = IdType.UUID)
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * 发送人ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(hidden = true)
    private String userId;

    /**
     * 发送内容
     */
    @NotEmpty(message = "发送内容不可为空")
    @TableField(value = "content")
    @ApiModelProperty(name = "content", value = "内容")
    private String content;

    /**
     * 发送设备
     */
    @TableField(value = "device")
    @ApiModelProperty(name = "device", value = "设备")
    private String device;

    /**
     * 发送时间
     */
    @TableField(value = "create_date", jdbcType = JdbcType.TIMESTAMP)
    @ApiModelProperty(hidden = true)
    private Date createDate;

    /**
     * 更新时间
     */
    @TableField(value = "update_date", update = "now()", jdbcType = JdbcType.TIMESTAMP)
    @ApiModelProperty(hidden = true)
    private Date updateDate;

    /**
     * 发送人信息
     */
    @TableField(exist = false)
    private UserDto author;

}
