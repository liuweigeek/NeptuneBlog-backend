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
        log.info("onPreSave => username: [{}]", userEntity.getUsername());
        userEntity.setRegisterDate(new Date());
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
