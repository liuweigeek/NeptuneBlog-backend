package com.scott.neptune.tweetserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.tweetserver.domain.valueobject.TweetPublicMetricsValObj;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
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

    private final UserClient userClient;
    private boolean retrieveAuthor = true;

    @Override
    protected Function<TweetEntity, TweetDto> functionConvertToDto() {
        return entity -> {
            TweetDto dto = new TweetDto();
            dto.setId(entity.getId());
            dto.setText(entity.getText());
            dto.setCreateAt(entity.getCreateAt());
            dto.setSource(entity.getSource());
            dto.setConversationId(entity.getConversationId());
            dto.setInReplyToUserId(entity.getInReplyToUserId());
            dto.setReferencedTweet(this.functionConvertToDto().apply(entity.getReferencedTweet()));
            if (entity.getPublicMetrics() != null) {
                TweetDto.PublicMetrics publicMetrics = new TweetDto.PublicMetrics();
                publicMetrics.setRetweetCount(entity.getPublicMetrics().getRetweetCount());
                publicMetrics.setQuoteCount(entity.getPublicMetrics().getQuoteCount());
                publicMetrics.setReplyCount(entity.getPublicMetrics().getReplyCount());
                publicMetrics.setLikeCount(entity.getPublicMetrics().getLikeCount());
                dto.setPublicMetrics(publicMetrics);
            }
            if (retrieveAuthor && entity.getAuthorId() != null) {
                UserDto author = userClient.getUserById(entity.getAuthorId());
                if (author != null) {
                    dto.setAuthor(author);
                }
            }
            return dto;
        };
    }

    @Override
    protected Function<TweetDto, TweetEntity> functionConvertToEntity() {
        return dto -> {
            TweetEntity entity = new TweetEntity();
            entity.setId(dto.getId());
            entity.setText(dto.getText());
            entity.setCreateAt(dto.getCreateAt());
            entity.setSource(dto.getSource());
            entity.setConversationId(dto.getConversationId());
            entity.setInReplyToUserId(dto.getInReplyToUserId());
            entity.setReferencedTweet(this.functionConvertToEntity().apply(dto.getReferencedTweet()));
            if (entity.getPublicMetrics() != null) {
                TweetPublicMetricsValObj publicMetrics = new TweetPublicMetricsValObj();
                publicMetrics.setRetweetCount(dto.getPublicMetrics().getRetweetCount());
                publicMetrics.setQuoteCount(dto.getPublicMetrics().getQuoteCount());
                publicMetrics.setReplyCount(dto.getPublicMetrics().getReplyCount());
                publicMetrics.setLikeCount(dto.getPublicMetrics().getLikeCount());
                entity.setPublicMetrics(publicMetrics);
            }
            if (dto.getAuthor() != null) {
                entity.setAuthorId(dto.getAuthor().getId());
            }
            return entity;
        };
    }

    @Override
    protected Function<Collection<TweetEntity>, Collection<TweetDto>> functionConvertToDtoList() {
        return entities -> {
            retrieveAuthor = false;
            Collection<Long> authorIds = entities.stream().map(TweetEntity::getAuthorId).collect(Collectors.toList());
            Map<Long, UserDto> authorMap = userClient.findUsersByIds(StringUtils.join(authorIds, ",")).stream()
                    .collect(Collectors.toMap(UserDto::getId, userDto -> userDto));
            return entities.stream()
                    .map(entity -> {
                        TweetDto dto = this.functionConvertToDto().apply(entity);
                        dto.setAuthor(authorMap.get(entity.getAuthorId()));
                        return dto;
                    })
                    .collect(Collectors.toList());
        };
    }

    @Override
    protected Function<Collection<TweetDto>, Collection<TweetEntity>> functionConvertToEntityList() {
        return dtos -> {
            retrieveAuthor = false;
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
