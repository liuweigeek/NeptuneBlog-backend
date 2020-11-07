package com.scott.neptune.tweetclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/6 15:59
 * @Description: 关注关系
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false, of = {"tweetId", "userId"})
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 推文ID
     */
    private Long tweetId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 点赞时间
     */
    private Date createAt;

    /**
     * 点赞设备
     */
    private String source;

}
