package com.scott.neptune.tweetserver.model;

import com.scott.neptune.common.base.BaseStorageInfo;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/11 17:01
 * @Description:
 */
@RequiredArgsConstructor
public class TweetImageStorageInfo extends BaseStorageInfo {

    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final long userId;
    private final long tweetId;
    private final LocalDate createAt;

    @Override
    public String getBusinessType() {
        return BusinessTypeEnum.TWEET_IMAGE.getName();
    }

    @Override
    public String buildFolder() {
        return "tweet_image" + SEPARATOR
                + DATE_TIME_FORMATTER.format(createAt) + SEPARATOR
                + userId + SEPARATOR
                + tweetId;
    }
}
