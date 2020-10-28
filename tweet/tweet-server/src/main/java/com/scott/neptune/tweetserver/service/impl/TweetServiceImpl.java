package com.scott.neptune.tweetserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.model.OffsetPageable;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.convertor.TweetConvertor;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.tweetserver.domain.valueobject.TweetPublicMetricsValObj;
import com.scott.neptune.tweetserver.repository.TweetPublicMetricsRepository;
import com.scott.neptune.tweetserver.repository.TweetRepository;
import com.scott.neptune.tweetserver.service.ITweetService;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:05
 * @Description: Tweet
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class TweetServiceImpl implements ITweetService {

    private final TweetRepository tweetRepository;
    private final TweetPublicMetricsRepository tweetPublicMetricsRepository;
    private final TweetConvertor tweetConvertor;
    private final UserClient userClient;

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
        TweetPublicMetricsValObj tweetPublicMetricsValObj = TweetPublicMetricsValObj.builder()
                .tweet(tweetEntity)
                .retweetCount(0)
                .quoteCount(0)
                .replyCount(0)
                .likeCount(0)
                .build();
        tweetPublicMetricsRepository.save(tweetPublicMetricsValObj);
        tweetEntity.setPublicMetrics(tweetPublicMetricsValObj);
        tweetDto = tweetConvertor.convertToDto(tweetEntity);
        UserDto author = userClient.getUserById(tweetEntity.getAuthorId());
        tweetDto.setAuthor(author);
        return tweetDto;
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
                .map(entity -> {
                    TweetDto tweetDto = tweetConvertor.convertToDto(entity);
                    UserDto author = userClient.getUserById(entity.getAuthorId());
                    tweetDto.setAuthor(author);
                    return tweetDto;
                })
                .orElseThrow(() -> new NeptuneBlogException("指定推文不存在"));
    }

    /**
     * 获取指定推文列表
     *
     * @param tweetIds
     * @return
     */
    @Override
    public Collection<TweetDto> findAllByIdList(Collection<Long> tweetIds) {
        if (CollectionUtils.isEmpty(tweetIds)) {
            return Collections.emptyList();
        }
        Collection<TweetEntity> tweetEntities = tweetRepository.findAll((root, query, criteriaBuilder) -> {
            if (Long.class != query.getResultType()) {
                root.fetch("publicMetrics", JoinType.LEFT);
            }
            return query.where(root.get("id").as(Long.class).in(tweetIds)).getRestriction();
        });
        Collection<Long> authorIds = tweetEntities.stream()
                .map(TweetEntity::getAuthorId).distinct().collect(Collectors.toList());
        Map<Long, UserDto> authorMap = userClient.findUsersByIds(StringUtils.join(authorIds, ",")).stream()
                .collect(Collectors.toMap(UserDto::getId, userDto -> userDto));
        return tweetEntities.stream()
                .map(entity -> {
                    TweetDto dto = tweetConvertor.convertToDto(entity);
                    dto.setAuthor(authorMap.get(entity.getAuthorId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取指定用户的推文
     *
     * @param authorId
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public Page<TweetDto> findByAuthorId(Long authorId, long offset, int limit) {
        if (authorId == null) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("createAt")));
        Page<TweetEntity> tweetEntityPage = tweetRepository.findAll((root, query, criteriaBuilder) -> {
            if (Long.class != query.getResultType()) {
                root.fetch("publicMetrics", JoinType.LEFT);
            }
            return query.where(criteriaBuilder.equal(root.get("authorId").as(Long.class), authorId)).getRestriction();
        }, pageable);
        Map<Long, UserDto> authorMap = userClient.findUsersByIds(Long.toString(authorId)).stream()
                .collect(Collectors.toMap(UserDto::getId, userDto -> userDto));
        return tweetEntityPage.map(entity -> {
            TweetDto dto = tweetConvertor.convertToDto(entity);
            dto.setAuthor(authorMap.get(entity.getAuthorId()));
            return dto;
        });
    }

    /**
     * 根据用户ID列表获取对应推文
     *
     * @param authorIdList
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public Page<TweetDto> findByAuthorIdList(Collection<Long> authorIdList, long offset, int limit) {
        if (CollectionUtils.isEmpty(authorIdList)) {
            return Page.empty();
        }
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("createAt")));
        Page<TweetEntity> tweetEntityPage = tweetRepository.findAll((root, query, criteriaBuilder) -> {
            if (Long.class != query.getResultType()) {
                root.fetch("publicMetrics", JoinType.LEFT);
            }
            return query.where(root.get("authorId").as(Long.class).in(authorIdList)).getRestriction();
        }, pageable);
        Collection<Long> authorIds = tweetEntityPage.getContent().stream()
                .map(TweetEntity::getAuthorId).distinct().collect(Collectors.toList());
        Map<Long, UserDto> authorMap = userClient.findUsersByIds(StringUtils.join(authorIds, ",")).stream()
                .collect(Collectors.toMap(UserDto::getId, userDto -> userDto));
        return tweetEntityPage.map(entity -> {
            TweetDto dto = tweetConvertor.convertToDto(entity);
            dto.setAuthor(authorMap.get(entity.getAuthorId()));
            return dto;
        });
    }

    /**
     * 根据关注者获取关注的Tweet
     *
     * @param followerId
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public Page<TweetDto> findFollowingTweets(Long followerId, long offset, int limit) {
        Collection<Long> followingIds = userClient.findAllFollowingIds(followerId);
        followingIds.add(followerId);
        return this.findByAuthorIdList(followingIds, offset, limit);
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
    public Collection<TweetDto> search(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }
        List<TweetEntity> tweetEntities = tweetRepository.findAll((root, query, criteriaBuilder) -> {
            if (Long.class != query.getResultType()) {
                root.fetch("publicMetrics", JoinType.LEFT);
            }
            return query.where(criteriaBuilder.like(root.get("text").as(String.class), "%" + keyword + "%"))
                    .orderBy(criteriaBuilder.desc(root.get("createAt").as(Date.class)))
                    .getRestriction();
        });

        Collection<Long> authorIds = tweetEntities.stream()
                .map(TweetEntity::getAuthorId).distinct().collect(Collectors.toList());
        Map<Long, UserDto> authorMap = userClient.findUsersByIds(StringUtils.join(authorIds, ",")).stream()
                .collect(Collectors.toMap(UserDto::getId, userDto -> userDto));
        return tweetEntities.stream()
                .map(entity -> {
                    TweetDto dto = tweetConvertor.convertToDto(entity);
                    dto.setAuthor(authorMap.get(entity.getAuthorId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
