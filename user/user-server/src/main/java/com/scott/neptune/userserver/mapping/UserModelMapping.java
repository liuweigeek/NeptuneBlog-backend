package com.scott.neptune.userserver.mapping;

import com.scott.neptune.common.mapping.BaseModelMapping;
import com.scott.neptune.userclient.dto.UserAvatarDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.entity.UserAvatarEntity;
import com.scott.neptune.userserver.entity.UserEntity;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class UserModelMapping extends BaseModelMapping<UserEntity, UserDto> {

    /**
     * convert entity to dto
     *
     * @param entity
     * @return
     */
    @Override
    public UserDto convertToDto(UserEntity entity) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(entity, dto);
        if (CollectionUtils.isNotEmpty(entity.getAvatarList())) {
            for (UserAvatarEntity avatar : entity.getAvatarList()) {
                int sizeType = avatar.getSizeType();
                if (UserAvatarDto.SizeTypeEnum.SMALL.getCode() == sizeType) {
                    dto.setSmallAvatar(avatar.getUrl());
                } else if (UserAvatarDto.SizeTypeEnum.NORMAL.getCode() == sizeType) {
                    dto.setNormalAvatar(avatar.getUrl());
                } else if (UserAvatarDto.SizeTypeEnum.LARGE.getCode() == sizeType) {
                    dto.setLargeAvatar(avatar.getUrl());
                }
            }
        }

        return dto;
    }

    /**
     * convert dto to entity
     *
     * @param dto
     * @return
     */
    @Override
    public UserEntity convertToEntity(UserDto dto) {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
