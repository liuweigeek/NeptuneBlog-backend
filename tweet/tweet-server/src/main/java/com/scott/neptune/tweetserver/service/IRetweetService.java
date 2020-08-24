package com.scott.neptune.tweetserver.service;

import com.scott.neptune.tweetclient.dto.TweetDto;
import org.springframework.data.domain.Page;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:05
 * @Description: Retweet
 */
public interface IRetweetService {

    TweetDto save(Long tweetId, Long authUserId);

    Page<TweetDto> findRetweet(Long tweetId, int offset, int limit);

    void delete(Long tweetId, Long authUserId);

}
