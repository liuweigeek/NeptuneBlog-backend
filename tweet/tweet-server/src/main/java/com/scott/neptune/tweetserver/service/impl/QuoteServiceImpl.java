package com.scott.neptune.tweetserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.model.OffsetPageable;
import com.scott.neptune.common.util.AssertUtils;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetclient.enumerate.TweetTypeEnum;
import com.scott.neptune.tweetserver.convertor.TweetConvertor;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.tweetserver.repository.TweetRepository;
import com.scott.neptune.tweetserver.service.IQuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:04
 * @Description: Quote tweet
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Service
public class QuoteServiceImpl implements IQuoteService {

    private final TweetRepository tweetRepository;
    private final TweetConvertor tweetConvertor;

    @Override
    public TweetDto save(String text, Long tweetId, Long authUserId) {
        TweetEntity originTweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NeptuneBlogException("指定推文不存在"));

        TweetEntity quoteTweetEntity = TweetEntity.builder()
                .type(TweetTypeEnum.quoted)
                .text(text)
                .referencedTweet(originTweet)
                .authorId(authUserId)
                .build();
        tweetRepository.save(quoteTweetEntity);
        return tweetConvertor.convertToDto(quoteTweetEntity);
    }

    @Override
    public Page<TweetDto> findQuotes(Long tweetId, long offset, int limit) {
        AssertUtils.assertNotNull(tweetId, "请指定推文ID");
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("createAt")));
        return tweetRepository.findTweetsByTweetId(tweetId, TweetTypeEnum.quoted, pageable).map(tweetConvertor::convertToDto);
    }
}
