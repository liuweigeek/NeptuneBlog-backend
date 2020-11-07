package com.scott.neptune.searchserver.api;

import com.scott.neptune.common.annotation.RedisLock;
import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.tweetclient.client.TweetClient;
import com.scott.neptune.tweetclient.command.TweetSearchRequest;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.command.UserSearchRequest;
import com.scott.neptune.userclient.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/21 22:44
 * @Description: NeptuneBlog
 */
@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController extends BaseController {

    private final UserClient userClient;
    private final TweetClient tweetClient;

    public SearchController(UserClient userClient, TweetClient tweetClient) {
        this.userClient = userClient;
        this.tweetClient = tweetClient;
    }

    @RedisLock
    @GetMapping
    public ResponseEntity<Map<String, Object>> searchByKeyword(@RequestParam("q") String query) {

        Collection<UserDto> userDtoList = userClient.search(UserSearchRequest.builder().q(query).build());
        Collection<TweetDto> tweetDtoList = tweetClient.search(TweetSearchRequest.builder().q(query).build());

        Map<String, Object> searchResultMap = new HashMap<>();
        searchResultMap.put("users", userDtoList);
        searchResultMap.put("tweets", tweetDtoList);

        return ResponseEntity.ok(searchResultMap);
    }
}
