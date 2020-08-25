package com.scott.neptune.tweetserver.service;

import com.scott.neptune.tweetclient.command.ReplyCommand;
import com.scott.neptune.tweetclient.dto.TweetDto;
import org.springframework.data.domain.Page;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:04
 * @Description: Reply tweet
 */
public interface IReplyService {

    TweetDto save(ReplyCommand replyCommand, Long authUserId);

    Page<TweetDto> findReplies(Long tweetId, long offset, int limit);

    void delete(Long replyId, Long authUserId);

}
