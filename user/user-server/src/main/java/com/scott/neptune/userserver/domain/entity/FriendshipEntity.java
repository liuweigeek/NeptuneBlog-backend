package com.scott.neptune.userserver.domain.entity;

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
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_friendship")
@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "friendship.id"),
        @NamedEntityGraph(name = "friendship.all", includeAllAttributes = true),
        @NamedEntityGraph(name = "friendship.following", attributeNodes = {@NamedAttributeNode("targetUser")}),
        @NamedEntityGraph(name = "friendship.followers", attributeNodes = {@NamedAttributeNode("sourceUser")})
})
public class FriendshipEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private FriendshipId id;

    /**
     * 关注人
     */
    @MapsId("sourceId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity sourceUser;

    /**
     * 被关注人
     */
    @MapsId("targetId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity targetUser;

    /**
     * 关注时间
     */
    @Column(name = "follow_date", columnDefinition = "TIMESTAMP")
    private Date followDate;

    /**
     * 关注来源
     */
    @Column(name = "follow_from", length = 50)
    private String followFrom;

    @Data
    @Builder
    @EqualsAndHashCode(callSuper = false, of = {"sourceId", "targetId"})
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class FriendshipId implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long sourceId;

        private Long targetId;
    }

}
