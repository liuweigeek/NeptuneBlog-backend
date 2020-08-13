package com.scott.neptune.userserver.api.web;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.userclient.dto.UserAvatarDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.component.UserComponent;
import com.scott.neptune.userserver.service.IAvatarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/10/9 18:42
 * @Description: NeptuneBlog
 */
@Api(tags = "文件接口 - 面向其他服务")
@Slf4j
@RestController
@RequestMapping(path = "avatars")
public class UserAvatarController extends BaseController {

    private final IAvatarService avatarService;
    private final UserComponent userComponent;

    public UserAvatarController(IAvatarService avatarService, UserComponent userComponent) {
        this.avatarService = avatarService;
        this.userComponent = userComponent;
    }

    /**
     * 上传头像
     *
     * @return
     */
    @ApiOperation(value = "上传头像")
    @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form", dataTypeClass = MultipartFile.class)
    @RequestMapping(path = "uploadAvatar", method = RequestMethod.POST)
    public ResponseEntity<List<UserAvatarDto>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        List<UserAvatarDto> userAvatarDtoList = avatarService.generateAvatar(loginUser.getId(), file);
        return ResponseEntity.ok(userAvatarDtoList);
    }
}
