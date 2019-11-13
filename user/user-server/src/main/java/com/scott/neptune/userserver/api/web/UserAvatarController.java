package com.scott.neptune.userserver.api.web;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.userapi.dto.UserDto;
import com.scott.neptune.userserver.component.UserComponent;
import com.scott.neptune.userserver.entity.UserEntity;
import com.scott.neptune.userserver.mapping.UserModelMapping;
import com.scott.neptune.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 13:54
 * @Description: 用户接口
 */
@Slf4j
@Api(tags = "用户头像接口 - 面向前端")
@RefreshScope
@RestController
@RequestMapping("/userAvatar")
public class UserAvatarController extends BaseController {

    @Resource
    private IUserService userService;
    @Resource
    private UserModelMapping userModelMapping;
    @Resource
    private UserComponent userComponent;

    /**
     * 上传头像
     *
     * @param avatarFile 头像
     * @return 上传结果
     */
    @ApiOperation(value = "上传结果")
    @PostMapping(value = "/uploadAvatar")
    public ServerResponse<UserDto> uploadAvatar(@RequestParam("file") MultipartFile avatarFile) {

        if (Objects.isNull(avatarFile)) {
            return ServerResponse.createByErrorMessage("上传头像不可为空");
        }
        UserDto loginUser = userComponent.getUserFromRequest(request);
        ServerResponse uploadAvatarRes = userService.uploadAvatar(avatarFile, loginUser);
        if (uploadAvatarRes.isFailed()) {
            return ServerResponse.createByErrorMessage(uploadAvatarRes.getMsg());
        }
        UserEntity userEntity = userService.getUserById(loginUser.getId(), null);
        loginUser = userModelMapping.convertToDto(userEntity);
        return ServerResponse.createBySuccess(loginUser);
    }
}
