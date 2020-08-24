package com.scott.neptune.userserver.api;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserAvatarDto;
import com.scott.neptune.userserver.service.IAvatarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/9 18:42
 * @Description: NeptuneBlog
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "文件接口")
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
    @ApiOperation(value = "上传头像")
    @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form", dataTypeClass = MultipartFile.class)
    @PostMapping("uploadAvatar")
    public ResponseEntity<List<UserAvatarDto>> uploadAvatar(@RequestParam("file") MultipartFile file, AuthUserDto authUser) {
        List<UserAvatarDto> userAvatarDtoList = avatarService.generateAvatar(authUser.getId(), file);
        return ResponseEntity.ok(userAvatarDtoList);
    }
}
