package com.scott.neptune.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/12/10 14:42
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * SUCCESS
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * ERROR
     */
    ERROR(1, "ERROR"),
    /**
     * NEED_LOGIN
     */
    NEED_LOGIN(10, "NEED_LOGIN"),
    /**
     * ILLEGAL_ARGUMENT
     */
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;
}
