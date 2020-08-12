package com.scott.neptune.tweetserver.model;

import com.scott.neptune.common.base.BaseStorageInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2020/8/11 17:01
 * @Description:
 */
public class TweetImageStorageInfo extends BaseStorageInfo {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final long userId;
    private final long tweetId;
    private final LocalDate createAt;

    public TweetImageStorageInfo(long userId, long tweetId, LocalDate createAt) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.createAt = createAt;
    }

    @Override
    public String getBusinessType() {
        return BusinessTypeEnum.TWEET_IMAGE.getName();
    }

    @Override
    public String buildFolder() {
        return "tweet_image" + SEPARATOR
                + dateTimeFormatter.format(createAt) + SEPARATOR
                + userId + SEPARATOR
                + tweetId;
    }
}
