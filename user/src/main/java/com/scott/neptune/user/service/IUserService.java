package com.scott.neptune.user.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.entity.UserEntity;

import java.util.List;

public interface IUserService {

    boolean existByUsername(String username);

    ServerResponse<UserEntity> login(String username, String password);

    ServerResponse<UserEntity> save(UserEntity userEntity);

    UserEntity getUserById(String id);

    UserEntity getUserByUsername(String username);

    List<UserEntity> findUserList();

    List<UserEntity> findAllUserByIdList(List<String> idList);
}
