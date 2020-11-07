package com.scott.neptune.tweetserver.api;

import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.tweetclient.dto.LikeDto;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.service.ILikeService;
import com.scott.neptune.tweetserver.service.ITweetService;
import com.scott.neptune.userclient.dto.AuthUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/like")
public class LikeController {

    private final ITweetService tweetService;
    private final ILikeService likeService;

    /**
     * 点赞指定推文
     *
     * @param id       指定推文ID
     * @param authUser 已登录用户
     * @return
     */
    @PostMapping
    public ResponseEntity<LikeDto> addFriendship(@RequestParam("tweetId") Long id, AuthUserDto authUser) {
        TweetDto tweet = tweetService.findTweetById(id);
        if (tweet == null) {
            throw new RestException("推文不存在", HttpStatus.NOT_FOUND);
        }
        LikeDto likeDto = LikeDto.builder()
                .tweetId(id)
                .userId(authUser.getId())
                .build();
        likeService.save(likeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeDto);
    }

    /**
     * 取消点赞指定推文
     *
     * @param id       指定推文ID
     * @param authUser 已登录用户
     * @return
     */
    @DeleteMapping("/{tweetId}")
    public ResponseEntity<LikeDto> deleteFriendship(@PathVariable("tweetId") Long id, AuthUserDto authUser) {
        TweetDto tweet = tweetService.findTweetById(id);
        if (tweet == null) {
            throw new RestException("推文不存在", HttpStatus.NOT_FOUND);
        }
        likeService.delete(id, authUser.getId());
        return ResponseEntity.noContent().build();
    }

}
