package com.scott.neptune.userserver.service;

import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/9/23 09:40
 * @Description: IUserService
 */
public interface IUserService {

    boolean existsByScreenName(String screenName);

    boolean existsByEmail(String email);

    UserDto save(UserDto userDto);

    UserDto findUserById(Long userId, Long loginUserId);

    UserDto findUserByScreenName(String screenName, Long loginUserId);

    UserDto findUserByEmail(String email, Long loginUserId);

    List<UserDto> findByKeyword(String keyword, Long loginUserId);

    List<UserDto> findAllUserByIdList(List<Long> idList, Long loginUserId);

    List<UserDto> findAllUserByScreenNameList(List<String> screenNameList, Long loginUserId);

    AuthUserDto findUserByScreenNameForAuthenticate(String screenName);

    //TODO remove soon
//    ServerResponse<List<UserAvatarEntity>> uploadAvatar(MultipartFile avatarFile, UserDto userDto);
}
