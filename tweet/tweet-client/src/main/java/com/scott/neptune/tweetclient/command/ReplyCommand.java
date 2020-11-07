package com.scott.neptune.tweetclient.command;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/25 23:28
 * @Description:
 */
@Data
@Builder
public class ReplyCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long originTweetId;

    private String text;

    private Long conversationId;

    private Long inReplyToUserId;
}