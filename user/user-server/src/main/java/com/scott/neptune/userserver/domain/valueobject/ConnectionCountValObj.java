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
public class ConnectionCountValObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Formula("select count(1) from t_friendship where follower_id = id")
    private Integer listedCount;

    @Formula("select count(1) from t_friendship where follower_id = id")
    private Integer favouritesCount;

    /**
     * 关注者数量
     */
    private Integer statusesCount;
}
