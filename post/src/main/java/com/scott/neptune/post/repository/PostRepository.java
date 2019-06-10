package com.scott.neptune.post.repository;

import com.scott.neptune.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {

    Page<Post> findAllByUserId(String userId, Pageable pageable);

    Page<Post> findAllByUserIdIn(List<String> userIdList, Pageable pageable);

}
