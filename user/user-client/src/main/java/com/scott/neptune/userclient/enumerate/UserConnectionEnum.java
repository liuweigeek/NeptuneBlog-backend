package com.scott.neptune.userclient.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserConnectionEnum {

    /**
     * following
     */
    FOLLOWING("following"),
    /**
     * following requested
     */
    FOLLOWING_REQUESTED("followingRequested"),
    /**
     * followed by
     */
    FOLLOWED_BY("followedBy"),
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
    public static UserConnectionEnum getEnum(String name) {
        return Arrays.stream(UserConnectionEnum.values())
                .filter(connection -> StringUtils.equals(connection.getName(), name))
                .findFirst()
                .orElse(null);
    }
}