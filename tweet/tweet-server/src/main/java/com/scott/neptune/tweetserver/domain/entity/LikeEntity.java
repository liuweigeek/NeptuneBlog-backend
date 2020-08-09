package com.scott.neptune.tweetserver.domain.entity;

import com.scott.neptune.userclient.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2020/1/5 13:43
 * @Description: 推文点赞
 */
@Data
@EqualsAndHashCode(callSuper = false, of = {"userId", "postId"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_like")
public class LikeEntity implements Serializable {

    //TODO needs a promary key

    /**
     * 点赞人ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 推文ID
     */
    @Column(name = "post_id")
    private String postId;

    /**
     * 点赞时间
     */
    @Column(name = "like_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date likeDate;

    /**
     * 评论人信息
     */
    @Transient
    private UserDto user;

}
