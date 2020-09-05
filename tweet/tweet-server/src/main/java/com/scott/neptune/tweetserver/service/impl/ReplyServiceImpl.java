package com.scott.neptune.tweetserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.model.OffsetPageable;
import com.scott.neptune.common.util.AssertUtils;
import com.scott.neptune.tweetclient.command.ReplyCommand;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetclient.enumerate.TweetTypeEnum;
import com.scott.neptune.tweetserver.convertor.TweetConvertor;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.tweetserver.repository.TweetRepository;
import com.scott.neptune.tweetserver.service.IReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:04
 * @Description: Reply tweet
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class ReplyServiceImpl implements IReplyService {

    private final TweetRepository tweetRepository;
    private final TweetConvertor tweetConvertor;

    @Override
    public TweetDto save(ReplyCommand replyCommand, Long authUserId) {
        TweetEntity originTweet = tweetRepository.findById(replyCommand.getOriginTweetId())
                .orElseThrow(() -> new NeptuneBlogException("指定推文不存在"));

        TweetEntity quoteTweetEntity = TweetEntity.builder()
                .type(TweetTypeEnum.quoted)
                .text(replyCommand.getText())
                .conversationId(replyCommand.getConversationId())
                .inReplyToUserId(replyCommand.getInReplyToUserId())
                .referencedTweet(originTweet)
                .authorId(authUserId)
                .build();
        tweetRepository.save(quoteTweetEntity);
        return tweetConvertor.convertToDto(quoteTweetEntity);
    }

    @Override
    public Page<TweetDto> findReplies(Long tweetId, long offset, int limit) {
        AssertUtils.assertNotNull(tweetId, "请指定推文ID");
        Pageable pageable = OffsetPageable.of(offset, limit, Sort.by(Sort.Order.desc("createAt")));
        return tweetRepository.findTweetsByReferencedTweetId(tweetId, TweetTypeEnum.replied_to, pageable)
                .map(tweetConvertor::convertToDto);
    }

    @Override
    public void delete(Long replyId, Long authUserId) {
        AssertUtils.assertNotNull(replyId, "请指定要删除的回复");

        TweetEntity reply = tweetRepository.findOne((root, query, criteriaBuilder) ->
                query.where(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("type").as(String.class), TweetTypeEnum.replied_to),
                                criteriaBuilder.equal(root.get("authorId").as(Long.class), authUserId))
                ).getRestriction())
                .orElseThrow(() -> new NeptuneBlogException("指定回复不存在"));

        tweetRepository.deleteById(reply.getId());
    }
}
