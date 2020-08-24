package com.scott.neptune.tweetserver.service;

import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:05
 * @Description: Tweet
 */
public interface ITweetService {

    TweetDto save(TweetDto tweetDto, Long authUserId);

    TweetDto findTweetById(Long tweetId);

    List<TweetDto> findAllByIdList(List<Long> tweetIds);

    Page<TweetDto> findByUserId(Long userId, long offset, int limit);

    Page<TweetDto> findByUserIdList(List<Long> userIdList, long offset, int limit);

    Page<TweetDto> findFollowingTweets(Long followerId, long offset, int limit);

    void delete(TweetEntity tweetEntity);

    void deleteById(Long tweetId);

    List<TweetDto> search(String keyword);

}
