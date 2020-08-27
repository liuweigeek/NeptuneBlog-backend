package com.scott.neptune.userserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class AuthUserEntityConvertor extends BaseConvertor<UserEntity, AuthUserDto> {

    @Override
    protected Function<UserEntity, AuthUserDto> functionConvertToDto() {
        return entity -> {
            AuthUserDto dto = new AuthUserDto();
            dto.setId(entity.getId());
            dto.setUsername(entity.getUsername());
            dto.setName(entity.getName());
            dto.setEmail(entity.getEmail());
            dto.setPassword(entity.getPassword());
            dto.setActive(true);
            dto.setLocked(false);
            dto.setExpired(false);
            dto.setEnabled(true);
            dto.setAuthorities(new String[]{});
            return dto;
        };
    }

    @Override
    protected Function<AuthUserDto, UserEntity> functionConvertToEntity() {
        return dto -> {
            UserEntity entity = new UserEntity();
            entity.setId(dto.getId());
            entity.setUsername(dto.getUsername());
            entity.setName(dto.getName());
            entity.setEmail(dto.getEmail());
            entity.setPassword(dto.getPassword());
            return entity;
        };
    }
}
