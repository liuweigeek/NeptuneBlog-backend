package com.scott.neptune.user.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.entity.UserAvatarEntity;
import com.scott.neptune.user.entity.UserEntity;
import com.scott.neptune.userapi.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

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

    ServerResponse<UserDto> save(UserEntity userEntity);

    UserEntity getUserById(String id, String loginUserId);

    UserEntity getUserByUsername(String username, String loginUserId);

    List<UserEntity> findByKeyword(String keyword, String loginUserId);

    List<UserEntity> findUserList(String loginUserId);

    List<UserEntity> findAllUserByIdList(List<String> idList, String loginUserId);

    ServerResponse<List<UserAvatarEntity>> uploadAvatar(MultipartFile avatarFile, UserDto userDto);
}
