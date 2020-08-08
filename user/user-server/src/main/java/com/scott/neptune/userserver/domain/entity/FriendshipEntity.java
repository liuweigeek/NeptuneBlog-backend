package com.scott.neptune.userserver.domain.entity;

import com.scott.neptune.userserver.domain.aggregate.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/6 15:59
 * @Description: 关注关系
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false, of = {"sourceUser", "targetUser"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_friendship")
@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "friendship.all", attributeNodes = {@NamedAttributeNode("sourceUser"), @NamedAttributeNode("targetUser")}),
        @NamedEntityGraph(name = "friendship.friends", attributeNodes = {@NamedAttributeNode("targetUser")}),
        @NamedEntityGraph(name = "friendship.followers", attributeNodes = {@NamedAttributeNode("sourceUser")})
})
//@IdClass(FriendshipEntity.FriendshipId.class)
public class FriendshipEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private FriendshipId id;

    /**
     * 关注人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("source_id")
    @JoinColumn(name = "source_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserEntity sourceUser;

    /**
     * 被关注人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("target_id")
    @JoinColumn(name = "target_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserEntity targetUser;

    /**
     * 关注时间
     */
    @Column(name = "follow_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date followDate;

    /**
     * 关注来源
     */
    @Column(name = "follow_from")
    private String followFrom;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Embeddable
    public static class FriendshipId implements Serializable {

        @Column(name = "source_id")
        private Long sourceId;

        @Column(name = "target_id")
        private Long targetId;
    }

}
