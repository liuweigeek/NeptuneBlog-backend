package com.scott.neptune.tweetserver.domain.valueobject;

import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/8 21:39
 * @Description: NeptuneBlog
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RetweetedValObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @Column(name = "retweeted_status")
    private TweetEntity retweetedStatus;

}
