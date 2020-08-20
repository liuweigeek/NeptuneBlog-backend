package com.scott.neptune.tweetserver.domain.valueobject;

import com.scott.neptune.tweetclient.enumerate.ReferencedTweetTypeEnum;
import com.scott.neptune.tweetserver.domain.entity.TweetEntity;
import lombok.Data;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/20 23:33
 * @Description:
 */
@Data
@Entity
@Table(name = "t_referenced_tweet")
public class ReferencedTweetValObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private ReferencedTweetTypeEnum type;

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referenced_by_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TweetEntity referencedBy;

}
