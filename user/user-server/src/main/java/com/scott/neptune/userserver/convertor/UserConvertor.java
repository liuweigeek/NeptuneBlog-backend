package com.scott.neptune.userserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.domain.aggregate.UserEntity;
import com.scott.neptune.userserver.domain.valueobject.UserAvatarValObj;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class UserConvertor extends BaseConvertor<UserEntity, UserDto> {

    @Override
    protected Function<UserEntity, UserDto> getFunctionInstanceToDto() {
        return userEntity -> {
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(userEntity, dto);
            if (userEntity.getUserAvatarValObj() != null) {
                dto.setSmallAvatar(userEntity.getUserAvatarValObj().getSmallAvatarUrl());
                dto.setNormalAvatar(userEntity.getUserAvatarValObj().getNormalAvatarUrl());
                dto.setLargeAvatar(userEntity.getUserAvatarValObj().getLargeAvatarUrl());
            }
            return dto;
        };
    }

    @Override
    protected Function<UserDto, UserEntity> getFunctionInstanceToEntity() {
        return dto -> {
            UserEntity entity = new UserEntity();
            BeanUtils.copyProperties(dto, entity);
            UserAvatarValObj userAvatarValObj = UserAvatarValObj.builder()
                    .smallAvatarUrl(dto.getSmallAvatar())
                    .normalAvatarUrl(dto.getNormalAvatar())
                    .largeAvatarUrl(dto.getLargeAvatar())
                    .build();
            entity.setUserAvatarValObj(userAvatarValObj);
            return entity;
        };
    }
}
