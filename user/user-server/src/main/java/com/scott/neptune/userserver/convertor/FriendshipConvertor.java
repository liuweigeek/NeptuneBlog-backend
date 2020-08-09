package com.scott.neptune.userserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import com.scott.neptune.userserver.domain.entity.FriendshipEntity;
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
public class FriendshipConvertor extends BaseConvertor<FriendshipEntity, FriendshipDto> {

    @Override
    protected Function<FriendshipEntity, FriendshipDto> getFunctionInstanceToDto() {
        return entity -> {
            FriendshipDto dto = new FriendshipDto();
            BeanUtils.copyProperties(entity, dto);
            dto.setSourceId(entity.getSourceUser().getId());
            dto.setTargetId(entity.getTargetUser().getId());
            return dto;
        };
    }

    @Override
    protected Function<FriendshipDto, FriendshipEntity> getFunctionInstanceToEntity() {
        return dto -> {
            FriendshipEntity entity = new FriendshipEntity();
            BeanUtils.copyProperties(dto, entity);
            entity.setSourceUser(UserEntity.builder().id(dto.getSourceId()).build());
            entity.setTargetUser(UserEntity.builder().id(dto.getTargetId()).build());
            return entity;
        };
    }
}
