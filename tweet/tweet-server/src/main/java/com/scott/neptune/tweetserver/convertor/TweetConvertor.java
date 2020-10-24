package com.scott.neptune.tweetserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.tweetserver.domain.valueobject.TweetPublicMetricsValObj;
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
    protected Function<TweetEntity, TweetDto> functionConvertToDto() {
        return entity -> {
            TweetDto dto = new TweetDto();
            dto.setId(entity.getId());
            dto.setText(entity.getText());
            dto.setCreateAt(entity.getCreateAt());
            dto.setSource(entity.getSource());
            dto.setConversationId(entity.getConversationId());
            dto.setInReplyToUserId(entity.getInReplyToUserId());
            dto.setReferencedTweet(this.convertToDto(entity.getReferencedTweet()));
            if (entity.getPublicMetrics() != null) {
                TweetDto.PublicMetrics publicMetrics = new TweetDto.PublicMetrics();
                publicMetrics.setRetweetCount(entity.getPublicMetrics().getRetweetCount());
                publicMetrics.setQuoteCount(entity.getPublicMetrics().getQuoteCount());
                publicMetrics.setReplyCount(entity.getPublicMetrics().getReplyCount());
                publicMetrics.setLikeCount(entity.getPublicMetrics().getLikeCount());
                dto.setPublicMetrics(publicMetrics);
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
            entity.setReferencedTweet(this.convertToEntity(dto.getReferencedTweet()));
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
}
