package com.scott.neptune.tweetserver.domain.listener;

import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
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
public class TweetAuditingListener {

    @PrePersist
    public void onPreSave(TweetEntity tweetEntity) {
        log.info("onPreSave => tweet: [{}]", tweetEntity.getText());
        tweetEntity.setCreateAt(new Date());
    }

    @PreUpdate
    public void onPreUpdate(TweetEntity tweetEntity) {
        log.info("onPreUpdate => tweet: [{}]", tweetEntity.getText());
    }

    @PreRemove
    public void onPreRemove(TweetEntity tweetEntity) {
        log.info("onPreRemove => tweet: [{}]", tweetEntity.getText());
    }

    @PostPersist
    public void onPostSave(TweetEntity tweetEntity) {
        log.info("onPostSave => tweet: [{}]", tweetEntity.getText());
    }

    @PostRemove
    public void onPostRemove(TweetEntity tweetEntity) {
        log.info("onPostRemove => tweet: [{}]", tweetEntity.getText());
    }
}
