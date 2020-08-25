package com.scott.neptune.tweetserver.service;

import com.scott.neptune.tweetclient.dto.TweetDto;
import org.springframework.data.domain.Page;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:04
 * @Description: Quote tweet
 */
public interface IQuoteService {

    TweetDto save(String text, Long tweetId, Long authUserId);

    Page<TweetDto> findQuotes(Long tweetId, long offset, int limit);
}
