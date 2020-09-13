package com.scott.neptune.authenticationserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

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
public class AuthUser implements UserDetails {

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

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            return Collections.emptyList();
        }
        return AuthorityUtils.createAuthorityList(authorities);
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !active;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !isExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return isEnabled;
    }
}
