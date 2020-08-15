package com.scott.neptune.userserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class AuthUserConvertor extends BaseConvertor<UserEntity, AuthUserDto> {

    @Override
    protected Function<UserEntity, AuthUserDto> getFunctionInstanceToDto() {
        return entity -> {
            AuthUserDto dto = new AuthUserDto();
            BeanUtils.copyProperties(entity, dto);
            //TODO fill the authorization info
            dto.setActive(true);
            dto.setLocked(false);
            dto.setExpired(false);
            dto.setEnabled(true);
            dto.setAuthorities(new String[]{});
            return dto;
        };
    }

    @Override
    protected Function<AuthUserDto, UserEntity> getFunctionInstanceToEntity() {
        return dto -> {
            UserEntity entity = new UserEntity();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        };
    }
}
