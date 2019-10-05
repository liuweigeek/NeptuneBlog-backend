package com.scott.neptune.user.api.server;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.component.UserComponent;
import com.scott.neptune.user.service.IUserService;
import com.scott.neptune.userapi.dto.UserDto;
import com.scott.neptune.userapi.entity.UserEntity;
import com.scott.neptune.userapi.mapping.UserModelMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 14:07
 * @Description: 为其他服务提供的用户接口
 */
@Api(tags = "用户接口 - 面向其他服务")
@Slf4j
@RestController
@RequestMapping("/userServer")
public class UserServerController extends BaseController {

    @Resource
    private IUserService userService;
    @Resource
    private UserComponent userComponent;
    @Resource
    private UserModelMapping userModelMapping;

    /**
     * 获取指定用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @ApiOperation(value = "获取指定用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "String")
    @GetMapping(value = "/getUserById/{id}")
    public ServerResponse<UserDto> getUserById(@PathVariable String id) {
        UserEntity userEntity = userService.getUserById(id);

        if (Objects.isNull(userEntity)) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        return ServerResponse.createBySuccess(userModelMapping.convertToDto(userEntity));
    }

    /**
     * 通过ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户对象列表
     */
    @ApiOperation(value = "通过ID列表获取全部用户")
    @GetMapping(value = "/findAllUserByIdList")
    public List<UserDto> findAllUserByIdList(List<String> idList) {
        return userService.findAllUserByIdList(idList)
                .stream()
                .map(userModelMapping::convertToDto)
                .collect(toList());
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @ApiOperation(value = "获取当前登录用户")
    @GetMapping(value = "/getLoginUser")
    public ServerResponse<UserDto> getLoginUser() {
        UserDto userDto = userComponent.getUserFromRequest(request);
        return ServerResponse.createBySuccess(userDto);
    }
}
