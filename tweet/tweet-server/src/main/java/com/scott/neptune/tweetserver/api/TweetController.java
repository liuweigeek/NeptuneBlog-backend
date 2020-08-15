package com.scott.neptune.tweetserver.api;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.service.ITweetService;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * 推文接口
 *
 * @author scott
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "推文接口")
@RestController
@RequestMapping("/tweets")
public class TweetController extends BaseController {

    private final UserClient userClient;
    private final ITweetService tweetService;

    /**
     * 发送推文
     *
     * @param tweetDto
     * @return
     */
    @ApiOperation(value = "发送推文")
    @PostMapping("/update")
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
    @GetMapping("/followingPosts")
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
    @GetMapping("/getPostsByUserId")
    public ResponseEntity<Page<TweetDto>> getPostsByUserId(Long userId) {
        //TODO parameters for pageable
        Page<TweetDto> tweetDtoPage = tweetService.findByUserId(userId, 0, 0);
        return ResponseEntity.ok(tweetDtoPage);
    }

    /**
     * 通过关键字搜索推文
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @ApiOperation(value = "通过关键字搜索用户")
    @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "path", required = true)
    @GetMapping(value = "/search/{keyword}")
    public ResponseEntity<Collection<TweetDto>> search(@PathVariable String keyword) {
        List<TweetDto> tweetDtoList = tweetService.findByKeyword(keyword);
        return ResponseEntity.ok(tweetDtoList);
    }
}
