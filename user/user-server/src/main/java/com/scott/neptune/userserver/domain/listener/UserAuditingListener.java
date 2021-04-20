package com.scott.neptune.userserver.domain.listener;

import com.scott.neptune.userserver.domain.entity.UserEntity;
import com.scott.neptune.userserver.domain.valueobject.UserPublicMetricsValObj;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/6 15:30
 * @Description:
 */
@Slf4j
public class UserAuditingListener {

    @PrePersist
    public void onPreSave(UserEntity userEntity) {
        log.info("onPreSave => username: [{}]", userEntity.getUsername());
        UserPublicMetricsValObj userPublicMetrics = UserPublicMetricsValObj.builder()
                .user(userEntity)
                .followingCount(0)
                .followersCount(0)
                .tweetCount(0)
                .likedCount(0)
                .build();
        userEntity.setPublicMetrics(userPublicMetrics);
        userEntity.setCreateAt(new Date());
    }

    @PreUpdate
    public void onPreUpdate(UserEntity userEntity) {
        log.info("onPreUpdate => username: [{}]", userEntity.getUsername());
    }

    @PreRemove
    public void onPreRemove(UserEntity userEntity) {
        log.info("onPreRemove => username: [{}]", userEntity.getUsername());
    }

    @PostPersist
    public void onPostSave(UserEntity userEntity) {
        log.info("onPostSave => username: [{}]", userEntity.getUsername());
    }

    @PostRemove
    public void onPostRemove(UserEntity userEntity) {
        log.info("onPostRemove => username: [{}]", userEntity.getUsername());
    }
}
