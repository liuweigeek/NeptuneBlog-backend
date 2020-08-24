package com.scott.neptune.userserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userserver.domain.entity.FriendshipEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class FriendshipConvertor extends BaseConvertor<FriendshipEntity, FriendshipDto> {

    @Override
    protected Function<FriendshipEntity, FriendshipDto> functionConvertToDto() {
        return entity -> {
            FriendshipDto dto = new FriendshipDto();
            dto.setSourceId(entity.getId().getSourceId());
            dto.setTargetId(entity.getId().getTargetId());
            dto.setFollowDate(entity.getFollowDate());
            dto.setFollowFrom(entity.getFollowFrom());
            return dto;
        };
    }

    @Override
    protected Function<FriendshipDto, FriendshipEntity> functionConvertToEntity() {
        return dto -> {
            FriendshipEntity entity = new FriendshipEntity();

            entity.setId(FriendshipEntity.FriendshipId.builder()
                    .sourceId(dto.getSourceId()).targetId(dto.getTargetId())
                    .build());
            entity.setFollowDate(dto.getFollowDate());
            entity.setFollowFrom(dto.getFollowFrom());
            return entity;
        };
    }
}
