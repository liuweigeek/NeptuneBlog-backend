package com.scott.neptune.post.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.entity.PostEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPostService {

    ServerResponse save(PostEntity postEntity);

    boolean delete(PostEntity postEntity);

    Page<PostEntity> findByUserId(String userId, int pageNumber, int pageSize);

    Page<PostEntity> findByUserIdList(List<String> userIdList, int pageNumber, int pageSize);

}
