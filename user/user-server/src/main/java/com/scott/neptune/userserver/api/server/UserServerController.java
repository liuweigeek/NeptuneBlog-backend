package com.scott.neptune.userserver.api.server;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.component.UserComponent;
import com.scott.neptune.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/9/23 14:07
 * @Description: 为其他服务提供的用户接口
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "用户接口 - 面向其他服务")
@RestController
@RequestMapping("/server/user")
public class UserServerController extends BaseController {

    private final IUserService userService;
    private final UserComponent userComponent;

    /**
     * 新增用户
     *
     * @param userDto 用户对象
     * @return 保存结果
     */
    @ApiOperation(value = "新增用户")
    @PostMapping
    public ResponseEntity<UserDto> addUser(UserDto userDto) {
        UserDto newUser = userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    /**
     * 根据ID获取指定用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @ApiOperation(value = "根据ID获取指定用户")
    @ApiImplicitParam(value = "用户ID", paramType = "path", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        UserDto userDto = userService.findUserById(id, loginUser.getId());
        return ResponseEntity.ok(userDto);
    }

    /**
     * 根据用户名获取指定用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @ApiOperation(value = "根据用户名获取指定用户")
    @ApiImplicitParam(value = "用户名", paramType = "path", required = true)
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> findUserByUsername(@PathVariable String username) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        UserDto userDto = userService.findUserByUsername(username, loginUser.getId());
        return ResponseEntity.ok(userDto);
    }

    @ApiOperation(value = "根据用户名获取指定用户,用于授权接口")
    @ApiImplicitParam(value = "用户名", paramType = "path", required = true)
    @GetMapping("/authenticate/{username}")
    public ResponseEntity<AuthUserDto> findUserByUsernameForAuthenticate(@PathVariable String username) {
        AuthUserDto authUserDto = userService.findUserByUsernameForAuthenticate(username);
        return ResponseEntity.ok(authUserDto);
    }

    /**
     * 根据邮箱获取指定用户
     *
     * @param email 邮箱
     * @return 用户对象
     */
    @ApiOperation(value = "根据邮箱获取指定用户")
    @ApiImplicitParam(value = "用户名", paramType = "path", required = true)
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> findUserByEmail(@PathVariable String email) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        UserDto userDto = userService.findUserByEmail(email, loginUser.getId());
        return ResponseEntity.ok(userDto);
    }

    /**
     * 通过ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户对象列表
     */
    @ApiOperation(value = "通过ID列表获取全部用户")
    @PostMapping("/findAllUserByIdList")
    public ResponseEntity<List<UserDto>> findAllUserByIdList(@RequestBody List<Long> idList) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        List<UserDto> userDtoList = userService.findAllUserByIdList(idList, loginUser.getId());
        return ResponseEntity.ok(userDtoList);
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @ApiOperation(value = "获取当前登录用户")
    @GetMapping("/loginUser")
    public ResponseEntity<UserDto> getLoginUser() {
        UserDto userDto = userComponent.getUserFromRequest(request);
        return ResponseEntity.ok(userDto);
    }

    /**
     * 通过关键字搜索用户
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @ApiOperation(value = "通过关键字搜索用户")
    @ApiImplicitParam(value = "关键字", paramType = "path", required = true)
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> search(@PathVariable String keyword) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        List<UserDto> userDtoList = userService.findByKeyword(keyword, loginUser.getId());
        //TODO add relation state
        return ResponseEntity.ok(userDtoList);
    }
}
