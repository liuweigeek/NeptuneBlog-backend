package com.scott.neptune.socialserver.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.socialserver.entity.LikeEntity;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2020/1/5 14:20
 * @Description: ILikeService
 */
public interface ILikeService {

    ServerResponse save(LikeEntity likeEntity);

    LikeEntity getLikeByUserIdAndPostId(String userId, String postId);

    boolean delete(LikeEntity likeEntity);

    boolean deleteByUserIdAndPostId(String userId, String postId);

}
