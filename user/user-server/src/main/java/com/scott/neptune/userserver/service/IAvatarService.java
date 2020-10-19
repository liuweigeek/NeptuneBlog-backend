package com.scott.neptune.userserver.service;

import com.scott.neptune.userclient.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/29 15:21
 * @Description: NeptuneBlog
 */
public interface IAvatarService {

    UserDto generateAvatar(Long userId, MultipartFile imageFile);

    UserDto generateAvatar(Long userId, File imageFile);
}
