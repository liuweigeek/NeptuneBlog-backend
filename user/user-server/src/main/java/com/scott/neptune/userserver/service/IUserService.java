package com.scott.neptune.userserver.service;

import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;

import java.util.Collection;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/23 09:40
 * @Description: IUserService
 */
public interface IUserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserDto save(UserDto userDto);

    UserDto findUserById(Long userId, Long loginUserId);

    UserDto findUserByUsername(String username, Long loginUserId);

    UserDto findUserByEmail(String email, Long loginUserId);

    Collection<UserDto> findAllUserByIdList(Collection<Long> ids, Long loginUserId);

    Collection<UserDto> findAllUserByUsernameList(Collection<String> usernameList, Long loginUserId);

    AuthUserDto findUserByUsernameForAuthenticate(String username);

    Collection<UserDto> search(String keyword, Long loginUserId);

    //TODO remove soon
//    ServerResponse<List<UserAvatarEntity>> uploadAvatar(MultipartFile avatarFile, UserDto userDto);
}
