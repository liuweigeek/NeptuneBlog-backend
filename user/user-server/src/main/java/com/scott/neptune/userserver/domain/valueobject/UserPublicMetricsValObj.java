package com.scott.neptune.userserver.domain.valueobject;

import com.scott.neptune.userserver.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Entity
@Table(name = "tb_user_public_metrics")
public class UserPublicMetricsValObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @MapsId("userId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    /**
     * 正在关注用户数量
     */
    @Formula("(select count(*) from tb_friendship t where t.source_id = userId)")
    private Integer followingCount;

    /**
     * 关注者数量
     */
    @Formula("(select count(*) from tb_friendship t where t.target_id = userId)")
    private Integer followersCount;

    @Column(name = "tweet_count")
    private Integer tweetCount;

    @Column(name = "liked_count")
    private Integer likedCount;
}
