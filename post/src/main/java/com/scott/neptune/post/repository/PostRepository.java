package com.scott.neptune.post.repository;

import com.scott.neptune.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, String> {

    Page<PostEntity> findAllByUserId(String userId, Pageable pageable);

    Page<PostEntity> findAllByUserIdIn(List<String> userIdList, Pageable pageable);

}
