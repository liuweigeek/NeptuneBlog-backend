package com.scott.neptune.authenticationserver.convertor;

import com.scott.neptune.authenticationserver.domain.AuthUser;
import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.userclient.dto.AuthUserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class AuthUserConvertor extends BaseConvertor<AuthUser, AuthUserDto> {

    @Override
    protected Function<AuthUser, AuthUserDto> getFunctionInstanceToDto() {
        return userEntity -> {
            AuthUserDto dto = new AuthUserDto();
            BeanUtils.copyProperties(userEntity, dto);
            return dto;
        };
    }

    @Override
    protected Function<AuthUserDto, AuthUser> getFunctionInstanceToEntity() {
        return dto -> {
            AuthUser entity = new AuthUser();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        };
    }
}
