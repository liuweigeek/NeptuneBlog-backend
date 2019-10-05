package com.scott.neptune.userapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @author scott
 */
@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"fromId", "toId"})
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_friend_relation")
public class FriendRelation {

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
