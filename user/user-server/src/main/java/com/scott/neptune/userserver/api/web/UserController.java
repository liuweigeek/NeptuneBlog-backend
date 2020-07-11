package com.scott.neptune.userserver.api.web;

import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.common.util.HeaderUtils;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.component.UserComponent;
import com.scott.neptune.userserver.entity.UserEntity;
import com.scott.neptune.userserver.mapping.UserModelMapping;
import com.scott.neptune.userserver.service.IFriendRelationService;
import com.scott.neptune.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 13:54
 * @Description: 用户接口
 */
@Slf4j
@Api(tags = "用户接口 - 面向前端")
@RefreshScope
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;
    @Resource
    private IFriendRelationService friendRelationService;
    @Resource
    private UserComponent userComponent;
    @Resource
    private UserModelMapping userModelMapping;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 注册
     *
     * @param userDto       用户对象
     * @param bindingResult 校验结果
     * @return 注册结果
     */
    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public ServerResponse register(@Validated(UserDto.Register.class) @RequestBody UserDto userDto,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMsgList = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(toList());

            return ServerResponse.createByErrorMessage(errorMsgList.toString());
        }
        UserEntity userEntity = userModelMapping.convertToEntity(userDto);
        ServerResponse<UserDto> registerRes = userService.save(userEntity);
        if (registerRes.isFailed()) {
            return registerRes;
        }
        userDto = registerRes.getData();
        redisTemplate.opsForValue().set(userEntity.getToken(), userDto, 30, TimeUnit.MINUTES);
        HeaderUtils.set(response, Constant.Login.CURRENT_USER, userDto.getToken());
        return ServerResponse.createBySuccess(userDto);
    }

    /**
     * 用户登录
     *
     * @param userDto 用户信息
     * @return 登录结果
     */
    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public ServerResponse login(@Validated(UserDto.Login.class) @RequestBody UserDto userDto,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMsgList = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(toList());

            return ServerResponse.createByErrorMessage(errorMsgList.toString());
        }

        if (!userService.existsByEmail(userDto.getEmail())) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        ServerResponse<UserDto> loginResponse = userService.login(userDto.getEmail(), userDto.getPassword());
        if (!loginResponse.isSuccess()) {
            return loginResponse;
        }

        UserDto loginUserDto = loginResponse.getData();
        HeaderUtils.set(response, Constant.Login.CURRENT_USER, loginUserDto.getToken());
        return ServerResponse.createBySuccess(loginUserDto);
    }

    /**
     * 用户退出登录
     *
     * @return 退出登录结果
     */
    @ApiOperation(value = "用户退出登录")
    @PostMapping(value = "/logout")
    public ServerResponse<String> logout() {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        redisTemplate.delete(loginUser.getToken());
        return ServerResponse.createBySuccess();
    }

    /**
     * 获取当前登陆用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "/getLoginUserInfo")
    public ServerResponse<UserDto> getLoginUserInfo() {
        UserDto userDto = userComponent.getUserFromRequest(request);
        return ServerResponse.createBySuccess(userDto);
    }

    /**
     * 获取指定用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取指定用户信息")
    @GetMapping(value = "/getUserInfo/{userId}")
    public ServerResponse<UserDto> getUserInfo(@PathVariable("userId") String userId) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        UserEntity userEntity = userService.getUserById(userId, loginUser.getId());
        if (Objects.isNull(userEntity)) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        UserDto userDto = userModelMapping.convertToDto(userEntity);
        return ServerResponse.createBySuccess(userDto);
    }

    /**
     * 获取指定用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取指定用户信息")
    @GetMapping(value = "/getByUsername/{username}")
    public ServerResponse<UserDto> getByUsername(@PathVariable("username") String username) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        UserEntity userEntity = userService.getUserByUsername(username, loginUser.getId());
        if (Objects.isNull(userEntity)) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        UserDto userDto = userModelMapping.convertToDto(userEntity);
        return ServerResponse.createBySuccess(userDto);
    }

    /**
     * 获取全部用户列表
     *
     * @return 用户列表
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping(value = "/list")
    public ServerResponse list() {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        List<UserDto> userDtoList = userService.findUserList(loginUser.getId())
                .stream()
                .map(userModelMapping::convertToDto)
                .collect(toList());
        return ServerResponse.createBySuccess(userDtoList);
    }

}
