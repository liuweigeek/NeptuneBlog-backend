package com.scott.neptune.userserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userserver.domain.aggregate.UserEntity;
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
        return friendshipEntity -> {
            FriendshipDto dto = new FriendshipDto();
            BeanUtils.copyProperties(friendshipEntity, dto);
            dto.setSourceId(friendshipEntity.getSourceUser().getId());
            dto.setTargetId(friendshipEntity.getTargetUser().getId());
            return dto;
        };
    }

    @Override
    protected Function<FriendshipDto, FriendshipEntity> getFunctionInstanceToEntity() {
        return friendshipDto -> {
            FriendshipEntity entity = new FriendshipEntity();
            BeanUtils.copyProperties(friendshipDto, entity);
            entity.setSourceUser(UserEntity.builder().id(friendshipDto.getSourceId()).build());
            entity.setTargetUser(UserEntity.builder().id(friendshipDto.getTargetId()).build());
            return entity;
        };
    }
}
