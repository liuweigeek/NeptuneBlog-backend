package com.scott.neptune.tweetserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.model.OffsetPageable;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.convertor.TweetConvertor;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.tweetserver.repository.TweetRepository;
import com.scott.neptune.tweetserver.service.IQuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 保存推文
     *
     * @param tweetDto   推文
     * @param authUserId 用户ID
     * @return 保存结果
     */
    @Override
    public TweetDto save(TweetDto tweetDto, Long authUserId) {
        TweetEntity tweetEntity = tweetConvertor.convertToEntity(tweetDto);
        tweetEntity.setAuthorId(authUserId);
        tweetRepository.save(tweetEntity);
        return tweetConvertor.convertToDto(tweetEntity);
    }

    /**
     * 获取指定推文
     *
     * @param tweetId
     * @return
     */
    @Override
    public TweetDto findTweetById(Long tweetId) {
        if (tweetId == null) {
            return null;
        }
        return tweetRepository.findById(tweetId)
                .map(tweetConvertor.convertToDto())
                .orElseThrow(() -> new NeptuneBlogException("指定推文不存在"));
    }

    /**
     * 获取指定推文列表
     *
     * @param tweetIds
     * @return
     */
    @Override
    public List<TweetDto> findAllByIdList(List<Long> tweetIds) {
        if (CollectionUtils.isEmpty(tweetIds)) {
            return Collections.emptyList();
        }
        return tweetRepository.findAllById(tweetIds).stream()
                .map(tweetConvertor.convertToDto())
                .collect(Collectors.toList());
    }

    /**
     * 获取指定用户的推文
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public Page<TweetDto> findByUserId(Long userId, int offset, int limit) {
        if (userId == null) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("createAt")));
        return tweetRepository.findByUserId(userId, pageable).map(tweetConvertor.convertToDto());
    }

    /**
     * 根据用户ID列表获取对应推文
     *
     * @param userIdList
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public Page<TweetDto> findByUserIdList(List<Long> userIdList, int offset, int limit) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("createAt")));
        return tweetRepository.findByUserIdIn(userIdList, pageable).map(tweetConvertor.convertToDto());
    }

    /**
     * 删除推文
     *
     * @param tweetEntity 推文
     * @return 删除结果
     */
    @Override
    public void delete(TweetEntity tweetEntity) {
        try {
            tweetRepository.deleteById(tweetEntity.getId());
        } catch (Exception e) {
            log.error("删除推文失败: ", e);
            throw new NeptuneBlogException("删除推文失败: ", e);
        }
    }

    /**
     * 删除推文
     *
     * @param tweetId 推文ID
     * @return 删除结果
     */
    @Override
    public void deleteById(Long tweetId) {
        try {
            tweetRepository.deleteById(tweetId);
        } catch (Exception e) {
            log.error("删除推文失败: ", e);
            throw new NeptuneBlogException("删除推文失败: ", e);
        }
    }

    /**
     * 根据关键字查找推文
     *
     * @param keyword
     * @return
     */
    @Override
    public List<TweetDto> search(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }
        return tweetRepository.findAll((root, query, criteriaBuilder) ->
                query.where(
                        criteriaBuilder.or(
                                criteriaBuilder.like(root.get("text").as(String.class), "%" + keyword + "%")))
                        .orderBy(criteriaBuilder.desc(root.get("createAt").as(Date.class)))
                        .getRestriction()).stream()
                .map(tweetConvertor.convertToDto())
                .collect(Collectors.toList());
    }

}
