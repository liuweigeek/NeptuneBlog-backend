package com.scott.neptune.userserver.mapping;

import com.scott.neptune.common.mapping.BaseModelMapping;
import com.scott.neptune.userclient.dto.FriendRelationDto;
import com.scott.neptune.userserver.entity.FriendRelationEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

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
}
