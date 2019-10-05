package com.scott.neptune.userapi.mapping;

import com.scott.neptune.userapi.dto.UserDto;
import com.scott.neptune.userapi.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class UserModelMapping {

    public UserDto convertToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    public List<UserDto> convertToDtoList(List<UserEntity> userEntityList) {
        return userEntityList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
