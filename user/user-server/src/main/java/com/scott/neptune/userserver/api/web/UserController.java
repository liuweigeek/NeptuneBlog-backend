package com.scott.neptune.userserver.api.web;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.component.UserComponent;
import com.scott.neptune.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/9/23 13:54
 * @Description: 用户接口
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "用户接口 - 面向前端")
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    private final IUserService userService;
    private final UserComponent userComponent;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户退出登录
     *
     * @return 退出登录结果
     */
    @ApiOperation(value = "用户退出登录")
    @PostMapping("/logout")
    public ResponseEntity<Object> logout() {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        redisTemplate.delete(loginUser.getToken());
        return ResponseEntity.ok().build();
    }

    /**
     * 获取当前登陆用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/getLoginUserInfo")
    public ResponseEntity<UserDto> getLoginUserInfo() {
        UserDto userDto = userComponent.getUserFromRequest(request);
        return ResponseEntity.ok(userDto);
    }

    /**
     * 获取指定用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取指定用户信息")
    @GetMapping("/getUserInfo/{userId}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("userId") Long userId) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        UserDto userDto = userService.findUserById(userId, loginUser.getId());
        if (userDto == null) {
            throw new RestException("用户不存在", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userDto);
    }

    /**
     * 获取指定用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取指定用户信息")
    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<UserDto> getByUsername(@PathVariable("username") String username) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        UserDto userDto = userService.findUserByUsername(username, loginUser.getId());
        if (userDto == null) {
            throw new RestException("用户不存在", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userDto);
    }

    //TODO used?

    /**
     * 获取全部用户列表
     *
     * @return 用户列表
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> list() {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        List<UserDto> userDtoList = userService.findUserList(loginUser.getId());
        return ResponseEntity.ok(userDtoList);
    }

}
