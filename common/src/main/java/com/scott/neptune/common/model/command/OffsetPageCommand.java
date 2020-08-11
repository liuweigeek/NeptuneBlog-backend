package com.scott.neptune.common.model.command;

import lombok.Data;

/**
 * @Author: scott
 * @Email: <a href="wliu@fleetup.com">scott</a>
 * @Date: 2020/8/9 22:36
 * @Description:
 */
@Data
public class OffsetPageCommand<T> {

    private long offset = 0;

    private int limit = 20;

    private T data;
}
