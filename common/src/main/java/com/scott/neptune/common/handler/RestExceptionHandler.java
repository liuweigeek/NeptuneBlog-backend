package com.scott.neptune.common.handler;

import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.common.model.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author: scott
 * @Email: <a href="wliu@fleetup.com">scott</a>
 * @Date: 2020/7/22 11:32
 * @Description:
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ApiErrorResponse> handleException(RestException e) {
        log.error("exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorResponse.builder().message(e.getMessage()).build());
    }

}
