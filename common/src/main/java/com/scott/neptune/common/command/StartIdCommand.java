package com.scott.neptune.common.command;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 22:36
 * @Description:
 */
@Data
@SuperBuilder
public class StartIdCommand<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    @Builder.Default
    private long startId = 0;

    @Builder.Default
    private int count = 20;

    private T data;
}
