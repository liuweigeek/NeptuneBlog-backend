package com.scott.neptune.tweetserver.api;

import com.scott.neptune.common.command.OffsetPageCommand;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.service.IRetweetService;
import com.scott.neptune.userclient.dto.AuthUserDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 18:19
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "Retweet接口")
@RestController
@RequestMapping("/retweet")
public class RetweetController {

    private final IRetweetService retweetService;

    /**
     * 转推
     *
     * @param id       tweetId
     * @param authUser 已登录用户
     * @return
     */
    @PostMapping
    public ResponseEntity<TweetDto> addRetweet(@RequestParam Long id, AuthUserDto authUser) {
        return ResponseEntity.ok(retweetService.save(id, authUser.getId()));
    }

    /**
     * 查找指定推文的转推
     *
     * @param id      tweetId
     * @param command
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Page<TweetDto>> findRetweets(@PathVariable("id") Long id, OffsetPageCommand command) {
        Page<TweetDto> retweets = retweetService.findRetweets(id, command.getOffset(), command.getLimit());
        return ResponseEntity.ok(retweets);
    }

    /**
     * TODO find the tweets created by me and retweeted by others
     *
     * @param authUser 已登录用户
     * @return
     */
    @GetMapping("/retweets_of_me/{id}")
    public ResponseEntity<Collection<TweetDto>> findRetweetsOfMe(AuthUserDto authUser) {
        return ResponseEntity.ok(Collections.singletonList(new TweetDto()));
    }

    /**
     * TODO 取消retweet
     *
     * @param id
     * @param authUser
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<TweetDto> removeRetweet(@PathVariable("id") Long id, AuthUserDto authUser) {
        retweetService.delete(id, authUser.getId());
        return ResponseEntity.noContent().build();
    }
}
