package com.scott.neptune.tweetclient.command;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/18 23:28
 * @Description:
 */
@Data
public class StatusesUpdateRequest {

    private static final long serialVersionUID = 1L;

    private String status;

    private Long inReplyToStatusId;

    private String mediaIds;

    private BigDecimal lat;

    private BigDecimal lng;
}
