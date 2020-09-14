package com.scott.neptune.userclient.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
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

    private String username;

    private String name;

    @Builder.Default
    private List<ConnectionEnum> connections = Lists.newArrayListWithExpectedSize(6);

    public RelationshipDto(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public RelationshipDto(Long id, String username, String name, ConnectionEnum connectionEnum) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.connections.add(connectionEnum);
    }

    public void addConnection(ConnectionEnum connectionEnum) {
        this.connections.add(connectionEnum);
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

        @JsonValue
        private final String name;

        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        public static ConnectionEnum getEnum(String name) {
            return Arrays.stream(ConnectionEnum.values())
                    .filter(connection -> StringUtils.equals(connection.getName(), name))
                    .findFirst()
                    .orElse(null);
        }
    }
}
