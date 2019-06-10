package com.scott.neptune.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_friend_relation")
public class FriendRelation {

    @Id
    @Column(length = 32)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(name = "author_id", length = 32)
    private String authorId;

    @Column(name = "target_id", length = 32)
    private String targetId;

    @Column(name = "follow_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date followDate;

    @Column(name = "follow_from", length = 32)
    private String followFrom;

}
