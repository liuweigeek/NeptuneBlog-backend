package com.scott.neptune.tweetserver.service;

import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import org.springframework.data.domain.Page;

import java.util.Collection;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:05
 * @Description: Tweet
 */
public interface ITweetService {

    TweetDto save(TweetDto tweetDto, Long authUserId);

    TweetDto findTweetById(Long tweetId);

    Collection<TweetDto> findAllByIdList(Collection<Long> tweetIds);

    Page<TweetDto> findByAuthorId(Long authorId, long offset, int limit);

    Page<TweetDto> findByAuthorIdList(Collection<Long> authorIdList, long offset, int limit);

    Page<TweetDto> findFollowingTweets(Long followerId, long offset, int limit);

    void delete(TweetEntity tweetEntity);

    void deleteById(Long tweetId);

    Collection<TweetDto> search(String keyword);

}
