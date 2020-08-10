package com.scott.neptune.tweetserver.api.web;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.service.ITweetService;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 推文接口
 *
 * @author scott
 */
@Slf4j
@Api(tags = "推文接口 - 面向前端")
@RestController
@RequestMapping(path = "tweets")
public class TweetController extends BaseController {

    private final UserClient userClient;
    private final ITweetService tweetService;

    public TweetController(UserClient userClient, ITweetService tweetService) {
        this.userClient = userClient;
        this.tweetService = tweetService;
    }

    /**
     * 发送推文
     *
     * @param tweetDto
     * @return
     */
    @ApiOperation(value = "发送推文")
    @PostMapping(path = "update")
    public ResponseEntity<TweetDto> update(@RequestBody TweetDto tweetDto) {
        UserDto loginUser = userClient.getLoginUser();
        tweetDto = tweetService.save(tweetDto, loginUser);
        tweetDto.setUser(loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetDto);
    }

    /**
     * 获取关注用户的推文
     *
     * @return
     */
    @ApiOperation(value = "获取关注用户的推文")
    @GetMapping(path = "followingPosts")
    public ResponseEntity<Page<TweetDto>> getFollowingPosts() {
        UserDto loginUser = userClient.getLoginUser();
        //TODO parameters for pageable
        Page<TweetDto> tweetDtoPage = tweetService.findByUserId(loginUser.getId(), 0, 0);
        return ResponseEntity.ok(tweetDtoPage);
    }

    /**
     * 获取指定用户的推文
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取指定用户的推文")
    //TODO rebuild the key for cache
    //@Cacheable(value = Constant.CacheKey.TWEET, key = "#userId+'.'+#postDto.getCurrent()+'.'+#postDto.getSize()")
    @GetMapping(path = "getPostsByUserId")
    public ResponseEntity<Page<TweetDto>> getPostsByUserId(Long userId) {
        //TODO parameters for pageable
        Page<TweetDto> tweetDtoPage = tweetService.findByUserId(userId, 0, 0);
        return ResponseEntity.ok(tweetDtoPage);
    }
}
