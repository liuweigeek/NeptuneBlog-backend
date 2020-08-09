package com.scott.neptune.tweetserver.service.impl;

import com.scott.neptune.common.dto.OffsetPageRequest;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.convertor.TweetConvertor;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.tweetserver.repository.TweetRepository;
import com.scott.neptune.tweetserver.service.ITweetService;
import com.scott.neptune.userclient.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author scott
 */
@Slf4j
@Service
public class TweetServiceImpl implements ITweetService {

    private final TweetRepository tweetRepository;
    private final TweetConvertor tweetConvertor;

    public TweetServiceImpl(TweetRepository tweetRepository, TweetConvertor tweetConvertor) {
        this.tweetRepository = tweetRepository;
        this.tweetConvertor = tweetConvertor;
    }

    /**
     * 保存推文
     *
     * @param tweetDto 推文
     * @return 保存结果
     */
    @Override
    public TweetDto save(TweetDto tweetDto, UserDto loginUser) {
        TweetEntity tweetEntity = tweetConvertor.convertToEntity(tweetDto);
        tweetEntity.setUserId(loginUser.getId());
        tweetRepository.save(tweetEntity);
        return tweetConvertor.convertToDto(tweetEntity);
    }

    /**
     * 删除推文
     *
     * @param tweetEntity 推文
     * @return 删除结果
     */
    @Override
    public boolean delete(TweetEntity tweetEntity) {

        try {
            tweetRepository.deleteById(tweetEntity.getId());
            return true;
        } catch (Exception e) {
            log.error("delete tweet exception: ", e);
            return false;
        }
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
        Pageable pageable = OffsetPageRequest.of(offset, limit, Sort.by(Sort.Order.desc("createAt")));
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
        Pageable pageable = OffsetPageRequest.of(offset, limit, Sort.by(Sort.Order.desc("createAt")));
        return tweetRepository.findByUserIdIn(userIdList, pageable).map(tweetConvertor.convertToDto());
    }

    /**
     * 根据关键字查找推文
     *
     * @param keyword
     * @return
     */
    @Override
    public List<TweetDto> findByKeyword(String keyword) {
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
