package com.scott.neptune.user.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.entity.User;

import java.util.List;

public interface IUserService {

    boolean existByUsername(String username);

    ServerResponse<User> login(String username, String password);

    ServerResponse<User> save(User user);

    User getUserById(String id);

    List<User> findUserList();

    List<User> findAllUserByIdList(List<String> idList);
}
