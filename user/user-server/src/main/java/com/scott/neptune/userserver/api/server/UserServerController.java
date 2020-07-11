package com.scott.neptune.userserver.api.server;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.component.UserComponent;
import com.scott.neptune.userserver.entity.UserEntity;
import com.scott.neptune.userserver.mapping.UserModelMapping;
import com.scott.neptune.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RefreshScope
@RestController
@RequestMapping("/server/user")
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
    @GetMapping(value = "/{id}")
    public ServerResponse<UserDto> getUserById(@PathVariable String id) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        UserEntity userEntity = userService.getUserById(id, loginUser.getId());
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
    @PostMapping(value = "/findAllUserByIdList")
    public List<UserDto> findAllUserByIdList(@RequestBody List<String> idList) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        return userService.findAllUserByIdList(idList, loginUser.getId())
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
        if (Objects.isNull(userDto)) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return ServerResponse.createBySuccess(userDto);
    }

    /**
     * 通过关键字搜索用户
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @ApiOperation(value = "通过关键字搜索用户")
    @ApiImplicitParam(name = "keyword", value = "关键字", required = true, paramType = "path", dataType = "String")
    @GetMapping(value = "/search/{keyword}")
    public ServerResponse<List<UserDto>> search(@PathVariable String keyword) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        List<UserEntity> userEntityList = userService.findByKeyword(keyword, loginUser.getId());
        List<UserDto> userDtoList = userModelMapping.convertToDtoList(userEntityList);
        //TODO add relation state
        return ServerResponse.createBySuccess(userDtoList);
    }
}
