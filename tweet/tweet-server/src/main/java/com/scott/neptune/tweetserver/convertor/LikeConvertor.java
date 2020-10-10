package com.scott.neptune.tweetserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.tweetclient.dto.LikeDto;
import com.scott.neptune.tweetserver.domain.entity.LikeEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class LikeConvertor extends BaseConvertor<LikeEntity, LikeDto> {

    @Override
    protected Function<LikeEntity, LikeDto> functionConvertToDto() {
        return entity -> {
            LikeDto dto = new LikeDto();
            dto.setTweetId(entity.getId().getTweetId());
            dto.setUserId(entity.getId().getUserId());
            dto.setCreateAt(entity.getCreateAt());
            dto.setSource(entity.getSource());
            return dto;
        };
    }

    @Override
    protected Function<LikeDto, LikeEntity> functionConvertToEntity() {
        return dto -> {
            LikeEntity entity = new LikeEntity();

            entity.setId(LikeEntity.LikeId.builder()
                    .tweetId(dto.getTweetId()).userId(dto.getUserId())
                    .build());
            entity.setCreateAt(dto.getCreateAt());
            entity.setSource(dto.getSource());
            return entity;
        };
    }
}
