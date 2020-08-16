package com.scott.neptune.userserver.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

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
public class FriendshipCountValObj implements Serializable {

    /**
     * 正在关注用户数量
     */
    @Formula("select count(1) from t_friend_relation where follower_id = id")
    private Integer friendsCount;

    /**
     * 关注者数量
     */
    @Formula("select count(1) from t_friend_relation where follower_id = id")
    private Integer followersCount;
}
