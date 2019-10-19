package com.scott.neptune.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.scott.neptune.common.dto.Pageable;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/6 15:59
 * @Description: 关注关系
 */
@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = false, of = {"fromId", "toId"})
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_friend_relation")
public class FriendRelation extends Pageable {

    /**
     * 关注人
     */
    @TableField(value = "from_id")
    private String fromId;

    /**
     * 被关注人
     */
    @TableField(value = "to_id")
    private String toId;

    /**
     * 关注时间
     */
    @TableField(value = "follow_date", jdbcType = JdbcType.TIMESTAMP)
    private Date followDate;

    /**
     * 关注来源
     */
    @TableField(value = "follow_from")
    private String followFrom;

}
