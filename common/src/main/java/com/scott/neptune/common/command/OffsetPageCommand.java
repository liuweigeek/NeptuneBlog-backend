package com.scott.neptune.common.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/9 22:36
 * @Description:
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OffsetPageCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder.Default
    private long offset = 0;

    @Builder.Default
    private int limit = 20;

}
