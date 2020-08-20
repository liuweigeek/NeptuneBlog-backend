package com.scott.neptune.tweetserver.api;

import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.tweetclient.command.StatusesUpdateRequest;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.service.ITweetService;
import com.scott.neptune.userclient.dto.AuthUserDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class StatusController {

    private final ITweetService tweetService;

    /**
     * TODO
     *
     * @param request
     * @return
     */
    @PostMapping("/statuses/update")
    public ResponseEntity<TweetDto> update(StatusesUpdateRequest request, AuthUserDto authUser) {
        return ResponseEntity.ok(new TweetDto());
    }

    @GetMapping("/statuses/show/{id}")
    public ResponseEntity<TweetDto> show(@PathVariable("id") Long statusId, Boolean includeMyRetweet) {
        TweetDto tweetDto = tweetService.findTweetById(statusId);
        return ResponseEntity.ok(tweetDto);
    }

    @GetMapping("/statuses/lookup")
    public ResponseEntity<Collection<TweetDto>> lookup(String statusIds) {
        if (StringUtils.isBlank(statusIds)) {
            throw new RestException("请指定要查找的推文", HttpStatus.BAD_REQUEST);
        }
        List<Long> ids = Stream.of(StringUtils.split(statusIds, ","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<TweetDto> tweetDtoList = tweetService.findAllByIdList(ids);
        return ResponseEntity.ok(tweetDtoList);
    }

    @PostMapping("/statuses/destroy/{id}")
    public ResponseEntity<TweetDto> destroy(@PathVariable("id") Long statusId) {
        TweetDto tweetDto = tweetService.findTweetById(statusId);
        if (tweetDto == null) {
            throw new RestException("指定推文不存在", HttpStatus.NOT_FOUND);
        }
        tweetService.deleteById(tweetDto.getId());
        return ResponseEntity.ok(tweetDto);
    }
}
