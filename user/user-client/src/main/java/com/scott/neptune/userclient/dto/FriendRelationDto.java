package com.scott.neptune.userclient.dto;

import com.scott.neptune.common.dto.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ApiModel(value = "friendRelation", description = "关注关系")
public class FriendRelationDto extends Pageable {

    private static final long serialVersionUID = 1L;

    /**
     * 关注人
     */
    @ApiModelProperty(name = "fromId", value = "关注人")
    private String fromId;

    /**
     * 被关注人
     */
    @ApiModelProperty(name = "toId", value = "被关注人")
    private String toId;

    /**
     * 关注时间
     */
    @ApiModelProperty(name = "followDate", value = "关注时间")
    private Date followDate;

    /**
     * 关注来源
     */
    @ApiModelProperty(name = "followFrom", value = "关注来源")
    private String followFrom;

}
