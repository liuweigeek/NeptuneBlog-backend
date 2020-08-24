package com.scott.neptune.authenticationserver.convertor;

import com.scott.neptune.authenticationserver.domain.AuthUser;
import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.userclient.dto.AuthUserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class AuthUserConvertor extends BaseConvertor<AuthUser, AuthUserDto> {

    @Override
    protected Function<AuthUser, AuthUserDto> functionConvertToDto() {
        return entity -> {
            AuthUserDto dto = new AuthUserDto();
            dto.setId(entity.getId());
            dto.setUsername(entity.getUsername());
            dto.setName(entity.getName());
            dto.setEmail(entity.getEmail());
            dto.setPassword(entity.getPassword());
            dto.setAuthorities(entity.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList())
                    .toArray(new String[]{}));
            dto.setEnabled(entity.isEnabled());
            dto.setActive(entity.isActive());
            dto.setLocked(entity.isLocked());
            dto.setExpired(entity.isExpired());
            return dto;
        };
    }

    @Override
    protected Function<AuthUserDto, AuthUser> functionConvertToEntity() {
        return dto -> {
            AuthUser entity = new AuthUser();
            entity.setId(dto.getId());
            entity.setUsername(dto.getUsername());
            entity.setName(dto.getName());
            entity.setEmail(dto.getEmail());
            entity.setPassword(dto.getPassword());
            entity.setAuthorities(dto.getAuthorities());
            entity.setEnabled(dto.isEnabled());
            entity.setActive(dto.isActive());
            entity.setLocked(dto.isLocked());
            entity.setExpired(dto.isExpired());
            return entity;
        };
    }
}
