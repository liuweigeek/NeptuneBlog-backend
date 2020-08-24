package com.scott.neptune.tweetserver.api;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.common.command.OffsetPageCommand;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.service.ITweetService;
import com.scott.neptune.userclient.dto.AuthUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final ITweetService tweetService;

    /**
     * 查询指定推文
     *
     * @param id
     * @param includeMyRetweet
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<TweetDto> findTweet(@PathVariable("id") Long id, Boolean includeMyRetweet) {
        TweetDto tweetDto = tweetService.findTweetById(id);
        return ResponseEntity.ok(tweetDto);
    }

    /**
     * 查询推文列表
     *
     * @param ids
     * @return
     */
    @GetMapping
    public ResponseEntity<Collection<TweetDto>> findTweets(@RequestParam String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new RestException("请指定要查找的推文", HttpStatus.BAD_REQUEST);
        }
        List<Long> idList = Stream.of(StringUtils.split(ids, ","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        Collection<TweetDto> tweetDtoList = tweetService.findAllByIdList(idList);
        return ResponseEntity.ok(tweetDtoList);
    }

    /**
     * 发送推文
     *
     * @param tweetDto
     * @return
     */
    @ApiOperation(value = "发送推文")
    @PostMapping
    public ResponseEntity<TweetDto> addTweet(@RequestBody TweetDto tweetDto, AuthUserDto authUser) {
        TweetDto tweet = tweetService.save(tweetDto, authUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(tweet);
    }

    /**
     * 删除推文
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除推文")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long id) {
        TweetDto tweetDto = tweetService.findTweetById(id);
        if (tweetDto == null) {
            throw new RestException("指定推文不存在", HttpStatus.NOT_FOUND);
        }
        tweetService.deleteById(tweetDto.getId());
        return ResponseEntity.noContent().build();
    }

    /**
     * 获取关注用户的推文
     *
     * @return
     */
    @ApiOperation(value = "获取关注用户的推文")
    @GetMapping("/following")
    public ResponseEntity<Page<TweetDto>> getFollowingTweets(@RequestParam OffsetPageCommand command,
                                                             AuthUserDto authUser) {
        //TODO parameters for pageable
        Page<TweetDto> tweetPage = tweetService.findFollowingTweets(authUser.getId(), command.getOffset(), command.getLimit());
        return ResponseEntity.ok(tweetPage);
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
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<TweetDto>> findByUserId(@PathVariable("userId") Long userId,
                                                       @RequestParam OffsetPageCommand command) {
        //TODO parameters for pageable
        Page<TweetDto> tweetPage = tweetService.findByAuthorId(userId, command.getOffset(), command.getLimit());
        return ResponseEntity.ok(tweetPage);
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
        Collection<TweetDto> tweetDtoList = tweetService.search(keyword);
        return ResponseEntity.ok(tweetDtoList);
    }
}
