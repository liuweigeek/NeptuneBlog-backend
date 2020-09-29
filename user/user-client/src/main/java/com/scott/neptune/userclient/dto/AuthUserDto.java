package com.scott.neptune.userclient.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/7/27 10:00
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String email;

    private String name;

    private boolean active;

    private boolean isLocked;

    private boolean isExpired;

    private boolean isEnabled;

    private String[] authorities;

    private String token;

}
