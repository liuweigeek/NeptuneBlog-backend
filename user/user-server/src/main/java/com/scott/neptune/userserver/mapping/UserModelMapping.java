package com.scott.neptune.userserver.mapping;

import com.scott.neptune.common.mapping.BaseModelMapping;
import com.scott.neptune.userapi.dto.UserAvatarDto;
import com.scott.neptune.userapi.dto.UserDto;
import com.scott.neptune.userserver.entity.UserAvatarEntity;
import com.scott.neptune.userserver.entity.UserEntity;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
     * convert entity list to dto list
     *
     * @param entityList
     * @return
     */
    @Override
    public List<UserDto> convertToDtoList(List<UserEntity> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        return entityList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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

    /**
     * convert dto list to entity list
     *
     * @param dtoList
     * @return
     */
    @Override
    public List<UserEntity> convertToEntityList(List<UserDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.emptyList();
        }
        return dtoList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
