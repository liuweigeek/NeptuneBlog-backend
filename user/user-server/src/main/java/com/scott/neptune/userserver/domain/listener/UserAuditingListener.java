package com.scott.neptune.userserver.domain.listener;

import com.scott.neptune.userserver.domain.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2020/8/6 15:30
 * @Description:
 */
@Slf4j
public class UserAuditingListener {

    @PrePersist
    public void onPreSave(UserEntity userEntity) {
        log.info("onPreSave => screenName: [{}]", userEntity.getScreenName());
        userEntity.setCreateAt(new Date());
    }

    @PreUpdate
    public void onPreUpdate(UserEntity userEntity) {
        log.info("onPreUpdate => screenName: [{}]", userEntity.getScreenName());
    }

    @PreRemove
    public void onPreRemove(UserEntity userEntity) {
        log.info("onPreRemove => screenName: [{}]", userEntity.getScreenName());
    }

    @PostPersist
    public void onPostSave(UserEntity userEntity) {
        log.info("onPostSave => screenName: [{}]", userEntity.getScreenName());
    }

    @PostRemove
    public void onPostRemove(UserEntity userEntity) {
        log.info("onPostRemove => screenName: [{}]", userEntity.getScreenName());
    }
}
