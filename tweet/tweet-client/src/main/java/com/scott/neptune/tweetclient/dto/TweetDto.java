package com.scott.neptune.tweetclient.dto;

import com.scott.neptune.userclient.dto.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/23 14:11
 * @Description:
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false, of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "tweet", description = "推文对象")
public class TweetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * 发送内容
     */
    @NotEmpty(message = "发送内容不可为空")
    @ApiModelProperty(value = "内容")
    private String text;

    /**
     * 发送人
     */
    @ApiModelProperty(value = "发送人")
    private UserDto author;

    /**
     * 发送时间
     */
    @ApiModelProperty(value = "发送时间")
    private Date createAt;

    /**
     * 发送设备
     */
    @ApiModelProperty(value = "设备")
    private String source;

    //TODO entities

    /**
     * 对话ID，如果当前Tweet是一条回复，则指向另一条Tweet
     */
    private Long conversationId;

    /**
     * 如果当前Tweet是一条回复，则该字段指向原Tweet的Author ID
     */
    private Long inReplyToUserId;

    private TweetDto referencedTweet;

    private PublicMetrics publicMetrics;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PublicMetrics implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long retweetCount;

        private Long quoteCount;

        private Long replyCount;

        private Long likeCount;
    }
}