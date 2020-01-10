package com.scott.neptune.socialserver.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.scott.neptune.userclient.dto.UserDto;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2020/1/5 13:43
 * @Description: 推文点赞
 */
@Data
@EqualsAndHashCode(callSuper = false, of = {"userId", "postId"})
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_like")
public class LikeEntity implements Serializable {

    @Version
    private static final long serialVersionUID = 1L;

    /**
     * 点赞人ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 推文ID
     */
    @TableField(value = "post_id")
    private String postId;

    /**
     * 点赞时间
     */
    @TableField(value = "create_date", jdbcType = JdbcType.TIMESTAMP)
    private Date createDate;

    /**
     * 评论人信息
     */
    @TableField(exist = false)
    private UserDto user;

}
