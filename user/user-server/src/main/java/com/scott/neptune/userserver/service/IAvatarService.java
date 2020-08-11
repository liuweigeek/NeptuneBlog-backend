package com.scott.neptune.userserver.service;

import com.scott.neptune.userclient.dto.UserAvatarDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 15:21
 * @Description: NeptuneBlog
 */
public interface IAvatarService {

    List<UserAvatarDto> generateAvatar(Long userId, MultipartFile imageFile);

    List<UserAvatarDto> generateAvatar(Long userId, File imageFile);
}
