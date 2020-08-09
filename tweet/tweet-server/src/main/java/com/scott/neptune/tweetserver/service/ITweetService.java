package com.scott.neptune.tweetserver.service;

import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import com.scott.neptune.userclient.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITweetService {

    TweetDto save(TweetDto tweetDto, UserDto loginUser);

    boolean delete(TweetEntity tweetEntity);

    Page<TweetDto> findByUserId(Long userId, int offset, int limit);

    Page<TweetDto> findByUserIdList(List<Long> userIdList, int offset, int limit);

    List<TweetDto> findByKeyword(String keyword);

}
