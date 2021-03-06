package com.scott.neptune.userserver.convertor;

import com.scott.neptune.common.base.BaseConvertor;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import com.scott.neptune.userserver.domain.valueobject.UserAvatarValObj;
import com.scott.neptune.userserver.domain.valueobject.UserPublicMetricsValObj;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class UserConvertor extends BaseConvertor<UserEntity, UserDto> {

    @Override
    protected Function<UserEntity, UserDto> functionConvertToDto() {
        return entity -> {
            UserDto dto = new UserDto();
            dto.setId(entity.getId());
            dto.setEmail(entity.getEmail());
            dto.setUsername(entity.getUsername());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setBirthday(entity.getBirthday());
            dto.setGender(entity.getGender());
            dto.setCreateAt(entity.getCreateAt());
            dto.setLang(entity.getLang());
            if (entity.getPublicMetrics() != null) {
                dto.setFollowingCount(entity.getPublicMetrics().getFollowingCount());
                dto.setFollowersCount(entity.getPublicMetrics().getFollowersCount());
            } else {
                dto.setFollowingCount(0);
                dto.setFollowersCount(0);
            }
            if (entity.getUserAvatarValObj() != null) {
                dto.setSmallAvatar(entity.getUserAvatarValObj().getSmallAvatarUrl());
                dto.setMediumAvatar(entity.getUserAvatarValObj().getMediumAvatarUrl());
                dto.setLargeAvatar(entity.getUserAvatarValObj().getLargeAvatarUrl());
            }
            return dto;
        };
    }

    @Override
    protected Function<UserDto, UserEntity> functionConvertToEntity() {
        return dto -> {
            UserEntity entity = new UserEntity();
            entity.setId(dto.getId());
            entity.setEmail(dto.getEmail());
            entity.setUsername(dto.getUsername());
            entity.setName(dto.getName());
            entity.setPassword(dto.getPassword());
            entity.setDescription(dto.getDescription());
            entity.setBirthday(dto.getBirthday());
            entity.setGender(dto.getGender());
            entity.setCreateAt(dto.getCreateAt());
            entity.setLang(dto.getLang());
            UserPublicMetricsValObj publicMetricsValObj = UserPublicMetricsValObj.builder()
                    .followingCount(dto.getFollowingCount())
                    .followersCount(dto.getFollowersCount())
                    .build();
            entity.setPublicMetrics(publicMetricsValObj);
            UserAvatarValObj userAvatarValObj = UserAvatarValObj.builder()
                    .smallAvatarUrl(dto.getSmallAvatar())
                    .mediumAvatarUrl(dto.getMediumAvatar())
                    .largeAvatarUrl(dto.getLargeAvatar())
                    .build();
            entity.setUserAvatarValObj(userAvatarValObj);
            return entity;
        };
    }
}
