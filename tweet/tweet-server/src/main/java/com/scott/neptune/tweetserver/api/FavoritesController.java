package com.scott.neptune.tweetserver.api;

import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.service.ITweetService;
import com.scott.neptune.userclient.dto.AuthUserDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 18:19
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "Favorite接口")
@RestController
public class FavoritesController {

    private final ITweetService tweetService;

    /**
     * TODO create a retweet
     *
     * @param statusId tweetId
     * @param authUser 已登录用户
     * @return
     */
    @PostMapping("/statuses/retweet/{id}")
    public ResponseEntity<TweetDto> addRetweet(@PathVariable("id") Long statusId, AuthUserDto authUser) {
        return ResponseEntity.ok(new TweetDto());
    }

    /**
     * TODO get a retweet
     *
     * @param statusId tweetId
     * @param authUser 已登录用户
     * @return
     */
    @GetMapping("/statuses/retweet/{id}")
    public ResponseEntity<List<TweetDto>> findRetweets(@PathVariable("id") Long statusId, AuthUserDto authUser) {
        return ResponseEntity.ok(Collections.singletonList(new TweetDto()));
    }

}
