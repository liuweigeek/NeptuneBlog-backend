package com.scott.neptune.common.command;

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
public class StartIdCommand<T> {

    private static final long serialVersionUID = 1L;

    private long startId = 0;

    private int count = 20;

    private T data;
}
