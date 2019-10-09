package com.scott.neptune.user.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.userapi.dto.UserDto;
import com.scott.neptune.userapi.entity.UserEntity;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:40
 * @Description: IUserService
 */
public interface IUserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    ServerResponse<UserDto> login(String email, String password);

    ServerResponse<UserEntity> save(UserEntity userEntity);

    UserEntity getUserById(String id);

    UserEntity getUserByUsername(String username);

    List<UserEntity> findUserList();

    List<UserEntity> findAllUserByIdList(List<String> idList);
}