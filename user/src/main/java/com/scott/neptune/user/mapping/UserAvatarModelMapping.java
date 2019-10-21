package com.scott.neptune.user.mapping;

import com.scott.neptune.common.mapping.BaseModelMapping;
import com.scott.neptune.user.entity.UserAvatarEntity;
import com.scott.neptune.userapi.dto.UserAvatarDto;
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
public class UserAvatarModelMapping extends BaseModelMapping<UserAvatarEntity, UserAvatarDto> {

    /**
     * convert entity to dto
     *
     * @param entity
     * @return
     */
    @Override
    public UserAvatarDto convertToDto(UserAvatarEntity entity) {
        UserAvatarDto dto = new UserAvatarDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * convert entity list to dto list
     *
     * @param entityList
     * @return
     */
    @Override
    public List<UserAvatarDto> convertToDtoList(List<UserAvatarEntity> entityList) {
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
    public UserAvatarEntity convertToEntity(UserAvatarDto dto) {
        UserAvatarEntity entity = new UserAvatarEntity();
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
    public List<UserAvatarEntity> convertToEntityList(List<UserAvatarDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.emptyList();
        }
        return dtoList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
