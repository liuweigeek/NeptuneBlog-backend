package com.scott.neptune.tweetserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@RequiredArgsConstructor
@Component
public class TweetConvertor extends BaseConvertor<TweetEntity, TweetDto> {

    private UserClient userClient;

    @Override
    protected Function<TweetEntity, TweetDto> functionConvertToDto() {
        return entity -> {
            TweetDto dto = new TweetDto();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        };
    }

    @Override
    protected Function<TweetDto, TweetEntity> functionConvertToEntity() {
        return dto -> {
            TweetEntity entity = new TweetEntity();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        };
    }

    @Override
    protected Function<Collection<TweetEntity>, Collection<TweetDto>> functionConvertToDtoList() {
        return entities -> {
            Collection<Long> authorIds = entities.stream().map(TweetEntity::getAuthorId).collect(Collectors.toList());
            Map<Long, UserDto> authorMap = userClient.findUsersByIds(StringUtils.join(authorIds, ",")).stream()
                    .collect(Collectors.toMap(UserDto::getId, userDto -> userDto));
            return entities.stream()
                    .map(entity -> {
                        TweetDto dto = functionConvertToDto().apply(entity);
                        dto.setAuthor(authorMap.get(entity.getAuthorId()));
                        return dto;
                    })
                    .collect(Collectors.toList());
        };
    }

    @Override
    protected Function<Collection<TweetDto>, Collection<TweetEntity>> functionConvertToEntityList() {
        return dtos -> {
            return dtos.stream()
                    .map(dto -> {
                        TweetEntity entity = functionConvertToEntity().apply(dto);
                        entity.setAuthorId(Optional.ofNullable(dto.getAuthor())
                                .map(UserDto::getId)
                                .orElse(null));
                        return entity;
                    })
                    .collect(Collectors.toList());
        };
    }
}
