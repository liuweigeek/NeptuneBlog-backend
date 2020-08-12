package com.scott.neptune.authentication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
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

    private String nickname;

    private boolean active;

    private boolean isLocked;

    private boolean isExpired;

    private boolean isEnabled;

    private String[] authorities;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
