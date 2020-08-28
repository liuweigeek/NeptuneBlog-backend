package com.scott.neptune.userserver.api;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.userclient.command.UserSearchRequest;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/23 13:54
 * @Description: 用户接口
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "用户接口")
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    private final IUserService userService;

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
     * 获取指定用户信息
     *
     * @param id       用户ID
     * @param authUser 已登陆用户
     * @return 用户信息
     */
    @ApiOperation(value = "获取指定用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", paramType = "path", dataTypeClass = Long.class)
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id, @ApiIgnore AuthUserDto authUser) {

        return Optional.ofNullable(userService.findUserById(id, authUser.getId()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RestException("用户不存在", HttpStatus.NOT_FOUND));
    }

    /**
     * 获取指定用户信息
     *
     * @param username 用户名
     * @param authUser 已登陆用户
     * @return 用户信息
     */
    @ApiOperation(value = "获取指定用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", paramType = "path", dataTypeClass = String.class)
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String username, @ApiIgnore AuthUserDto authUser) {

        return Optional.ofNullable(userService.findUserByUsername(username, authUser.getId()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RestException("用户不存在", HttpStatus.NOT_FOUND));
    }

    /**
     * 获取用户列表
     *
     * @param ids      用户ID列表
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "查询用户列表")
    @ApiImplicitParam(name = "ids", value = "用户ID列表", paramType = "form", dataTypeClass = String.class)
    @GetMapping
    public ResponseEntity<Collection<UserDto>> findUsersByIds(String ids, @ApiIgnore AuthUserDto authUser) {
        List<Long> userIds = Stream.of(StringUtils.split(ids, ","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        Collection<UserDto> userDtoList = userService.findAllUserByIdList(userIds, authUser.getId());
        return ResponseEntity.ok(userDtoList);
    }

    /**
     * 获取用户列表
     *
     * @param usernames 用户名列表
     * @param authUser  已登录用户
     * @return
     */
    @ApiOperation(value = "查询用户列表")
    @ApiImplicitParam(name = "usernames", value = "用户名列表", paramType = "form", dataTypeClass = String.class)
    @GetMapping("/username")
    public ResponseEntity<Collection<UserDto>> findUsersByUsernames(String usernames, @ApiIgnore AuthUserDto authUser) {

        List<String> usernameList = Stream.of(StringUtils.split(usernames, ","))
                .collect(Collectors.toList());
        Collection<UserDto> userDtoList = userService.findAllUserByUsernameList(usernameList, authUser.getId());
        return ResponseEntity.ok(userDtoList);
    }

    /**
     * 通过关键字搜索用户
     *
     * @param request  关键字
     * @param authUser 已登陆用户
     * @return 用户列表
     */
    @ApiOperation(value = "通过关键字搜索用户")
    @GetMapping("/search")
    public ResponseEntity<Collection<UserDto>> search(UserSearchRequest request, @ApiIgnore AuthUserDto authUser) {
        Collection<UserDto> userDtoList = userService.search(request.getQ(), authUser.getId());
        //TODO add relation state
        return ResponseEntity.ok(userDtoList);
    }

    /**
     * 根据用户名获取指定用户,用于授权
     *
     * @param username 用户名
     * @return
     */
    @ApiOperation(value = "根据用户名获取指定用户,用于授权")
    @ApiImplicitParam(name = "username", value = "用户名", paramType = "path", dataTypeClass = String.class)
    @GetMapping("/authenticate/{username}")
    public ResponseEntity<AuthUserDto> getUserByUsernameForAuthenticate(@PathVariable String username) {
        AuthUserDto authUserDto = userService.findUserByUsernameForAuthenticate(username);
        return ResponseEntity.ok(authUserDto);
    }

}
