package com.scott.neptune.userserver.api;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.service.IAvatarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/9 18:42
 * @Description: NeptuneBlog
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/avatars")
public class UserAvatarController extends BaseController {

    private final IAvatarService avatarService;

    /**
     * 上传头像
     *
     * @param file     头像
     * @param authUser 已登录用户
     * @return
     */
    @PostMapping
    public ResponseEntity<UserDto> uploadAvatar(@RequestParam("file") MultipartFile file, AuthUserDto authUser) {
        UserDto userDto = avatarService.generateAvatar(authUser.getId(), file);
        return ResponseEntity.ok(userDto);
    }
}
