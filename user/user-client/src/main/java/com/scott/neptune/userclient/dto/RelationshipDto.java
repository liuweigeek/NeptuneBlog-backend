package com.scott.neptune.userclient.dto;

import com.google.common.collect.Lists;
import com.scott.neptune.userclient.enumerate.UserConnectionEnum;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

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
@ApiModel(value = "relationship", description = "与登录用户的关系")
public class RelationshipDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    @Builder.Default
    private Collection<UserConnectionEnum> connections = Lists.newArrayListWithExpectedSize(2);

    public RelationshipDto(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public RelationshipDto(Long id, String username, String name, UserConnectionEnum connectionEnum) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.connections.add(connectionEnum);
    }

    public RelationshipDto(Long id, String username, String name, Collection<UserConnectionEnum> connections) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.connections.addAll(connections);
    }

    public void addConnection(UserConnectionEnum connectionEnum) {
        this.connections.add(connectionEnum);
    }


}
