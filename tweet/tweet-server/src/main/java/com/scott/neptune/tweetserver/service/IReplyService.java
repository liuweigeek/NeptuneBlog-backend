package com.scott.neptune.tweetserver.service;

import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:04
 * @Description: Reply tweet
 */
public interface IReplyService {

    TweetDto save(TweetDto tweetDto, Long authUserId);

    TweetDto findTweetById(Long tweetId);

    List<TweetDto> findAllByIdList(List<Long> tweetIds);

    Page<TweetDto> findByUserId(Long userId, int offset, int limit);

    Page<TweetDto> findByUserIdList(List<Long> userIdList, int offset, int limit);

    void delete(TweetEntity tweetEntity);

    void deleteById(Long tweetId);

    List<TweetDto> search(String keyword);

}
