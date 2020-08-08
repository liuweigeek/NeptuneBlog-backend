package com.scott.neptune.common.exception;

import lombok.Getter;

/**
 * @Author: scott
 * @Email: <a href="wliu@fleetup.com">scott</a>
 * @Date: 2020/7/27 10:37
 * @Description: 业务自定义运行时异常
 */
@Getter
public class NeptuneBlogException extends RuntimeException {

    private final static int DEFAULT_CODE = 0;

    private final String message;

    private final int code;

    public NeptuneBlogException(Exception e) {
        super(e);
        this.message = e.getMessage();
        this.code = DEFAULT_CODE;
    }

    public NeptuneBlogException(String message) {
        super(message);
        this.message = message;
        this.code = DEFAULT_CODE;
    }

    public NeptuneBlogException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public NeptuneBlogException(String message, Throwable e) {
        super(message, e);
        this.message = message;
        this.code = DEFAULT_CODE;
    }

    public NeptuneBlogException(String message, int code, Throwable e) {
        super(message, e);
        this.message = message;
        this.code = code;
    }

}
