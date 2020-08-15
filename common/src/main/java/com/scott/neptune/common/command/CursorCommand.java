package com.scott.neptune.common.command;

import lombok.Data;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2020/8/9 22:36
 * @Description:
 */
@Data
public class CursorCommand {

    private long cursor = 0;

    private int count = 20;
}
