package com.scott.neptune.tweetserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class TweetConvertor extends BaseConvertor<TweetEntity, TweetDto> {

    @Override
    protected Function<TweetEntity, TweetDto> getFunctionInstanceToDto() {
        return entity -> {
            TweetDto dto = new TweetDto();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        };
    }

    @Override
    protected Function<TweetDto, TweetEntity> getFunctionInstanceToEntity() {
        return dto -> {
            TweetEntity entity = new TweetEntity();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        };
    }
}
