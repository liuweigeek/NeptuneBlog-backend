package com.scott.neptune.common.command;

import lombok.Data;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 22:36
 * @Description:
 */
@Data
public class CursorCommand {

    private static final long serialVersionUID = 1L;

    private long cursor = 0;

    private int count = 20;
}
