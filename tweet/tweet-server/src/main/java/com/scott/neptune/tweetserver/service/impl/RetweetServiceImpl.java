package com.scott.neptune.tweetserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetclient.enumerate.TweetTypeEnum;
import com.scott.neptune.tweetserver.convertor.TweetConvertor;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.tweetserver.repository.TweetRepository;
import com.scott.neptune.tweetserver.service.IRetweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:05
 * @Description: Retweet
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Service
public class RetweetServiceImpl implements IRetweetService {

    private final TweetRepository tweetRepository;
    private final TweetConvertor tweetConvertor;

    /**
     * save a retweet
     *
     * @param tweetId
     * @param authUserId
     * @return
     */
    @Override
    public TweetDto save(Long tweetId, Long authUserId) {
        TweetEntity tweetEntity = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NeptuneBlogException("指定推文不存在"));
        TweetEntity retweetEntity = TweetEntity.builder()
                .type(TweetTypeEnum.retweeted)
                .referencedTweet(tweetEntity)
                .build();
        tweetRepository.save(retweetEntity);
        return tweetConvertor.convertToDto(retweetEntity);
    }

    @Override
    public Page<TweetDto> findRetweet(Long tweetId, int offset, int limit) {
        return null;
    }

    @Override
    public void delete(Long tweetId, Long authUserId) {

    }
}
