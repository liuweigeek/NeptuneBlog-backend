package com.scott.neptune.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/7/28 17:15
 * @Description:
 */
@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int status;

    private String message;

    private ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ApiErrorResponse createByMessage(String message) {
        return new ApiErrorResponse(0, message);
    }
}
