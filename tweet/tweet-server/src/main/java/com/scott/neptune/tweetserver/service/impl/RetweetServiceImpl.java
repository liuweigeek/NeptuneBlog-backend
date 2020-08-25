package com.scott.neptune.tweetserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.model.OffsetPageable;
import com.scott.neptune.common.util.AssertUtils;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetclient.enumerate.TweetTypeEnum;
import com.scott.neptune.tweetserver.convertor.TweetConvertor;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.tweetserver.repository.TweetRepository;
import com.scott.neptune.tweetserver.service.IRetweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        TweetEntity originTweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NeptuneBlogException("指定推文不存在"));

        Optional<TweetEntity> existRetweet = tweetRepository.findOne((root, query, criteriaBuilder) ->
                query.where(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("referencedTweet").as(TweetEntity.class), originTweet),
                                criteriaBuilder.equal(root.get("type").as(String.class), TweetTypeEnum.retweeted),
                                criteriaBuilder.equal(root.get("authorId").as(Long.class), authUserId))
                ).getRestriction());
        if (existRetweet.isPresent()) {
            throw new NeptuneBlogException("请勿重复转推");
        }
        TweetEntity retweetEntity = TweetEntity.builder()
                .type(TweetTypeEnum.retweeted)
                .referencedTweet(originTweet)
                .authorId(authUserId)
                .build();
        tweetRepository.save(retweetEntity);
        return tweetConvertor.convertToDto(retweetEntity);
    }

    @Override
    public Page<TweetDto> findRetweets(Long tweetId, long offset, int limit) {
        AssertUtils.assertNotNull(tweetId, "请指定推文ID");
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("createAt")));
        return tweetRepository.findTweetsByTweetId(tweetId, TweetTypeEnum.retweeted, pageable)
                .map(tweetConvertor::convertToDto);
    }

    @Override
    public void delete(Long tweetId, Long authUserId) {
        AssertUtils.assertNotNull(tweetId, "请指定要取消转推的推文");
        TweetEntity originTweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NeptuneBlogException("指定推文不存在"));

        TweetEntity retweet = tweetRepository.findOne((root, query, criteriaBuilder) ->
                query.where(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("referencedTweet").as(TweetEntity.class), originTweet),
                                criteriaBuilder.equal(root.get("type").as(String.class), TweetTypeEnum.retweeted),
                                criteriaBuilder.equal(root.get("authorId").as(Long.class), authUserId))
                ).getRestriction())
                .orElseThrow(() -> new NeptuneBlogException("指定转推不存在"));

        tweetRepository.deleteById(retweet.getId());
    }
}
