package com.scott.neptune.userserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import com.scott.neptune.userserver.domain.valueobject.UserAvatarValObj;
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
public class UserConvertor extends BaseConvertor<UserEntity, UserDto> {

    @Override
    protected Function<UserEntity, UserDto> functionConvertToDto() {
        return entity -> {
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(entity, dto);
            if (entity.getUserAvatarValObj() != null) {
                dto.setSmallAvatar(entity.getUserAvatarValObj().getSmallAvatarUrl());
                dto.setMediumAvatar(entity.getUserAvatarValObj().getMediumAvatarUrl());
                dto.setLargeAvatar(entity.getUserAvatarValObj().getLargeAvatarUrl());
            }
            return dto;
        };
    }

    @Override
    protected Function<UserDto, UserEntity> functionConvertToEntity() {
        return dto -> {
            UserEntity entity = new UserEntity();
            BeanUtils.copyProperties(dto, entity);
            UserAvatarValObj userAvatarValObj = UserAvatarValObj.builder()
                    .smallAvatarUrl(dto.getSmallAvatar())
                    .mediumAvatarUrl(dto.getMediumAvatar())
                    .largeAvatarUrl(dto.getLargeAvatar())
                    .build();
            entity.setUserAvatarValObj(userAvatarValObj);
            return entity;
        };
    }
}
