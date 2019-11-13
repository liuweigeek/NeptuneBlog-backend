package com.scott.neptune.userserver.mapping;

import com.scott.neptune.common.mapping.BaseModelMapping;
import com.scott.neptune.userapi.dto.FriendRelationDto;
import com.scott.neptune.userserver.entity.FriendRelationEntity;
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
public class FriendRelationModelMapping extends BaseModelMapping<FriendRelationEntity, FriendRelationDto> {

    /**
     * convert entity to dto
     *
     * @param entity
     * @return
     */
    @Override
    public FriendRelationDto convertToDto(FriendRelationEntity entity) {
        FriendRelationDto dto = new FriendRelationDto();
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
    public List<FriendRelationDto> convertToDtoList(List<FriendRelationEntity> entityList) {
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
    public FriendRelationEntity convertToEntity(FriendRelationDto dto) {
        FriendRelationEntity entity = new FriendRelationEntity();
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
    public List<FriendRelationEntity> convertToEntityList(List<FriendRelationDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.emptyList();
        }
        return dtoList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
