package com.scott.neptune.userserver.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 17:44
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserPublicMetricsValObj implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 正在关注用户数量
     */
//    @Formula("select count(1) from friendship where sourceId = id")
    private Integer followingCount;

    /**
     * 关注者数量
     */
//    @Formula("select count(1) from friendship where targetId = id")
    private Integer followersCount;

    private Integer tweetCount;

    private Integer likedCount;
}
