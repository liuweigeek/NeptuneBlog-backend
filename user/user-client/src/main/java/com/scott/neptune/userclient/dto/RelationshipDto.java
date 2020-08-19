package com.scott.neptune.userclient.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
@ApiModel(value = "relationship", description = "与登录用户的关系")
public class RelationshipDto {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String screenName;

    private String name;

    @Builder.Default
    private List<String> connections = Lists.newArrayListWithExpectedSize(6);

    public RelationshipDto(Long id, String screenName, String name, String connection) {
        this.id = id;
        this.screenName = screenName;
        this.name = name;
        this.connections.add(connection);
    }

    public void addConnection(String connection) {
        this.connections.add(connection);
    }

    @Getter
    @AllArgsConstructor
    public enum ConnectionEnum {

        /**
         * following
         */
        FOLLOWING("following"),
        /**
         * following requested
         */
        FOLLOWING_REQUESTED("following_requested"),
        /**
         * followed by
         */
        FOLLOWED_BY("followed_by"),
        /**
         * none
         */
        NONE("none"),
        /**
         * blocking
         */
        BLOCKING("blocking"),
        /**
         * muting
         */
        MUTING("muting");

        private final String name;
    }
}
