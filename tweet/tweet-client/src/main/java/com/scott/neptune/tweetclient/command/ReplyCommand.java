package com.scott.neptune.tweetclient.command;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/25 23:28
 * @Description:
 */
@Data
@Builder
@ApiModel(value = "quote tweet request", description = "转推请求")
public class ReplyCommand {

    private static final long serialVersionUID = 1L;

    private Long originTweetId;

    private String text;

    private Long conversationId;

    private Long inReplyToUserId;
}