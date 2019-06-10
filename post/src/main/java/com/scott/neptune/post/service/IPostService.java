package com.scott.neptune.post.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPostService {

    ServerResponse save(Post post);

    boolean delete(Post post);

    Page<Post> findByUserId(String userId, int pageNumber, int pageSize);

    Page<Post> findByUserIdList(List<String> userIdList, int pageNumber, int pageSize);

}
