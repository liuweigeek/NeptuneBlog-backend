package com.scott.neptune.tweetserver.repository;

import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2020/8/9 15:39
 * @Description:
 */
public interface TweetRepository extends JpaRepository<TweetEntity, Long>,
        JpaSpecificationExecutor<TweetEntity> {

    @Query("from TweetEntity t where t.userId = :userId")
    Page<TweetEntity> findByUserId(@Param("userId") Long userId, Pageable pageable);

    Page<TweetEntity> findByUserIdIn(@Param("userId") List<Long> userIds, Pageable pageable);
}
