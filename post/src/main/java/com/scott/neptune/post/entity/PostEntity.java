package com.scott.neptune.post.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.scott.neptune.common.dto.Pageable;
import com.scott.neptune.userapi.dto.UserDto;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 14:11
 * @Description: 推文
 */
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_post")
public class PostEntity extends Pageable {

    @Version
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 发送人ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 发送内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 发送设备
     */
    @TableField(value = "device")
    private String device;

    /**
     * 发送时间
     */
    @TableField(value = "create_date", jdbcType = JdbcType.TIMESTAMP)
    private Date createDate;

    /**
     * 更新时间
     */
    @TableField(value = "update_date", update = "now()", jdbcType = JdbcType.TIMESTAMP)
    private Date updateDate;

    /**
     * 发送人信息
     */
    @TableField(exist = false)
    private UserDto author;

}
