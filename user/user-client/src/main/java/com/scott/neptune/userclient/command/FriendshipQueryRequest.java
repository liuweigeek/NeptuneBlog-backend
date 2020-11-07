package com.scott.neptune.userclient.command;

import com.scott.neptune.common.command.OffsetPageCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/15 20:08
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipQueryRequest extends OffsetPageCommand {

    private static final long serialVersionUID = 1L;

    private String username;
}
