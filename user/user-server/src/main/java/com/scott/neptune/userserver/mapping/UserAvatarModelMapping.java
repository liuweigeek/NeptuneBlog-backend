package com.scott.neptune.userserver.mapping;

import com.scott.neptune.common.mapping.BaseModelMapping;
import com.scott.neptune.userclient.dto.UserAvatarDto;
import com.scott.neptune.userserver.entity.UserAvatarEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

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
}
