package com.scott.neptune.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @Author: scott
 * @Email: <a href="wliu@fleetup.com">scott</a>
 * @Date: 2020/7/27 10:37
 * @Description:
 */
@Getter
@AllArgsConstructor
public class RestException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;
}
