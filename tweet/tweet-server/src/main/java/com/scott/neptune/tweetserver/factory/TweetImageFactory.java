package com.scott.neptune.tweetserver.factory;

import com.scott.neptune.common.base.BaseStorageInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/11 17:01
 * @Description:
 */
public class TweetImageFactory extends BaseStorageInfo {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String buildFolder(final long userId, final long tweetId, final LocalDate createAt) {
        return "tweet-image" + SEPARATOR
                + DATE_TIME_FORMATTER.format(createAt) + SEPARATOR
                + userId + SEPARATOR
                + tweetId;
    }
}
