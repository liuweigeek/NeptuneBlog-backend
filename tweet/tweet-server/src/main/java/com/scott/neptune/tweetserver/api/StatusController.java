package com.scott.neptune.tweetserver.api;

import com.scott.neptune.tweetclient.command.StatusesUpdateRequest;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.service.ITweetService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Api(tags = "推文接口")
@RestController
@RequestMapping("/statuses")
public class StatusController {

    private final ITweetService tweetService;

    /**
     * TODO
     *
     * @param request
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<TweetDto> update(StatusesUpdateRequest request) {
        return ResponseEntity.ok(new TweetDto());
    }

    @GetMapping("/show")
    public ResponseEntity<TweetDto> show(Long statusId, Boolean includeMyRetweet) {
        //tweetService.delete()
        return ResponseEntity.ok(new TweetDto());
    }

    @PostMapping("/destroy/{id}")
    public ResponseEntity<TweetDto> destroy(@PathVariable("id") Long statusId) {
        //tweetService.delete()
        return ResponseEntity.ok(new TweetDto());
    }
}
