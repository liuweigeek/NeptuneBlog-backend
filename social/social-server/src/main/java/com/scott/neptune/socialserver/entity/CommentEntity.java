package com.scott.neptune.socialserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.scott.neptune.userclient.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2020/1/5 13:43
 * @Description: 推文评论
 */
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_comment")
public class CommentEntity implements Serializable {

    @Version
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 评论人ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 推文ID
     */
    @TableField(value = "post_id")
    private String postId;

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
     * 评论人信息
     */
    @TableField(exist = false)
    private UserDto user;

}
