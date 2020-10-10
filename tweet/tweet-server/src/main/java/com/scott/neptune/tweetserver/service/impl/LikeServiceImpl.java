package com.scott.neptune.tweetserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.tweetclient.dto.LikeDto;
import com.scott.neptune.tweetserver.convertor.LikeConvertor;
import com.scott.neptune.tweetserver.domain.entity.LikeEntity;
import com.scott.neptune.tweetserver.repository.LikeRepository;
import com.scott.neptune.tweetserver.service.ILikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:04
 * @Description: Quote tweet
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class LikeServiceImpl implements ILikeService {

    private final LikeRepository likeRepository;
    private final LikeConvertor likeConvertor;

    @Override
    public LikeDto save(LikeDto likeDto) {
        LikeEntity likeEntity = likeConvertor.convertToEntity(likeDto);
        return likeRepository.findById(LikeEntity.LikeId.builder()
                .tweetId(likeDto.getTweetId())
                .userId(likeDto.getUserId())
                .build())
                .map(likeConvertor.convertToDto())
                .orElseGet(() -> {
                    likeEntity.setCreateAt(new Date());
                    likeRepository.save(likeEntity);
                    return likeConvertor.convertToDto(likeEntity);
                });
    }

    @Override
    public void delete(Long tweetId, Long userId) {
        try {
            likeRepository.deleteById(LikeEntity.LikeId.builder()
                    .tweetId(tweetId).userId(userId).build());
        } catch (Exception e) {
            log.error("取消点赞失败: ", e);
            throw new NeptuneBlogException("取消点赞失败", e);
        }
    }
}
