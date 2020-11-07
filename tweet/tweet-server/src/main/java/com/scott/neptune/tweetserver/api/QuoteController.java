package com.scott.neptune.tweetserver.api;

import com.scott.neptune.common.command.OffsetPageCommand;
import com.scott.neptune.tweetclient.command.QuoteTweetCommand;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.service.IQuoteService;
import com.scott.neptune.userclient.dto.AuthUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 18:19
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final IQuoteService quoteService;

    /**
     * 转推
     *
     * @param command  转推请求
     * @param authUser 已登录用户
     * @return
     */
    @PostMapping
    public ResponseEntity<TweetDto> addQuoteTweet(QuoteTweetCommand command, AuthUserDto authUser) {
        return ResponseEntity.ok(quoteService.save(command.getText(), command.getOriginTweetId(), authUser.getId()));
    }

    /**
     * 查找指定推文的转推
     *
     * @param id      tweetId
     * @param command
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Page<TweetDto>> findQuoteTweets(@PathVariable("id") Long id, OffsetPageCommand command) {
        Page<TweetDto> retweets = quoteService.findQuotes(id, command.getOffset(), command.getLimit());
        return ResponseEntity.ok(retweets);
    }
}
