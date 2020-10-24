package com.scott.neptune.userclient.dto;

import com.google.common.collect.Lists;
import com.scott.neptune.userclient.enumerate.UserConnectionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/6 15:59
 * @Description: 关注关系
 */
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor
public class RelationshipUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String smallAvatar;

    private String mediumAvatar;

    private String largeAvatar;

    private Date followDate;

    private String followFrom;

    private Collection<UserConnectionEnum> connections = Lists.newArrayListWithExpectedSize(2);

    public RelationshipUserDto(Long id, String username, String name,
                               String smallAvatar, String mediumAvatar, String largeAvatar) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.smallAvatar = smallAvatar;
        this.mediumAvatar = mediumAvatar;
        this.largeAvatar = largeAvatar;
    }

    public RelationshipUserDto(Long id, String username, String name,
                               String smallAvatar, String mediumAvatar, String largeAvatar,
                               Date followDate, String followFrom) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.smallAvatar = smallAvatar;
        this.mediumAvatar = mediumAvatar;
        this.largeAvatar = largeAvatar;
        this.followDate = followDate;
        this.followFrom = followFrom;
    }

    public RelationshipUserDto(Long id, String username, String name, UserConnectionEnum connectionEnum) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.connections.add(connectionEnum);
    }

    public RelationshipUserDto(Long id, String username, String name, Collection<UserConnectionEnum> connections) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.connections.addAll(connections);
    }

    public void addConnection(UserConnectionEnum connectionEnum) {
        this.connections.add(connectionEnum);
    }

    public void addRelationInfo(Date followDate, String followFrom) {
        this.followDate = followDate;
        this.followFrom = followFrom;
    }

}
