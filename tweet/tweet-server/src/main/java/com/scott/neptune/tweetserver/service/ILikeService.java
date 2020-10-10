package com.scott.neptune.tweetserver.service;

import com.scott.neptune.tweetclient.dto.LikeDto;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/23 23:04
 * @Description: Reply tweet
 */
public interface ILikeService {

    LikeDto save(LikeDto likeDto);

    void delete(Long tweetId, Long userId);

}
