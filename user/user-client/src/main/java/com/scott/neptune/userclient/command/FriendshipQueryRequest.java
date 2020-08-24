package com.scott.neptune.userclient.command;

import com.scott.neptune.common.command.OffsetPageCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/15 20:08
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FriendshipQueryRequest extends OffsetPageCommand {

    private static final long serialVersionUID = 1L;

    private Long userId;
}
