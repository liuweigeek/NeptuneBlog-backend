package com.scott.neptune.postserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.postserver.entity.PostEntity;
import com.scott.neptune.userapi.dto.UserDto;

import java.util.List;

public interface IPostService {

    ServerResponse<PostEntity> save(PostEntity postEntity, UserDto loginUser);

    boolean delete(PostEntity postEntity);

    IPage<PostEntity> findByUserId(String userId, int pageNumber, int pageSize);

    IPage<PostEntity> findByUsername(String username, int pageNumber, int pageSize);

    IPage<PostEntity> findByUserIdList(List<String> userIdList, int pageNumber, int pageSize);

    IPage<PostEntity> findByFollowerId(String followerId, int pageNumber, int pageSize);

    List<PostEntity> findByKeyword(String keyword);

}
