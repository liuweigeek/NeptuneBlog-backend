package com.scott.neptune.postapi.dto;

import com.scott.neptune.common.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 14:11
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostDto extends BaseDto {

    /**
     * Id
     */
    private String id;

    /**
     * 发送人ID
     */
    private String userId;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送设备
     */
    private String device;

    /**
     * 发送时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 发送人Id
     */
    private String authorId;

    /**
     * 用户名
     */
    private String authorUsername;

    /**
     * 性别
     */
    private Integer authorSex;

}
