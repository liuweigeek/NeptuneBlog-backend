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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final RedisTemplate<String, Object> redisTemplate;

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
     * @param userId     用户ID
     * @param screenName 用户名
     * @param authUser   已登陆用户
     * @return 用户信息
     */
    @ApiOperation(value = "获取指定用户信息")
    @GetMapping("/show")
    public ResponseEntity<UserDto> show(Long userId, String screenName, AuthUserDto authUser) {
        if (userId != null) {
            return Optional.ofNullable(userService.findUserById(userId, authUser.getId()))
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new RestException("用户不存在", HttpStatus.NOT_FOUND));
        }
        if (StringUtils.isNotBlank(screenName)) {
            return Optional.ofNullable(userService.findUserByScreenName(screenName, authUser.getId()))
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new RestException("用户不存在", HttpStatus.NOT_FOUND));
        }
        throw new RestException("请指定要查找的用户ID或用户名", HttpStatus.BAD_REQUEST);
    }

    //TODO used?

    /**
     * 获取全部用户列表
     *
     * @param userIds     用户ID列表
     * @param screenNames 用户名列表
     * @param authUser    已登录用户
     * @return
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping("/lookup")
    public ResponseEntity<Collection<UserDto>> lookup(String userIds, String screenNames, AuthUserDto authUser) {
        if (StringUtils.isNotBlank(userIds)) {
            List<Long> ids = Stream.of(StringUtils.split(userIds, ","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<UserDto> userDtoList = userService.findAllUserByIdList(ids, authUser.getId());
            return ResponseEntity.ok(userDtoList);
        }
        if (StringUtils.isNotBlank(screenNames)) {
            List<String> userScreenNames = Stream.of(StringUtils.split(screenNames, ","))
                    .collect(Collectors.toList());
            List<UserDto> userDtoList = userService.findAllUserByScreenNameList(userScreenNames, authUser.getId());
            return ResponseEntity.ok(userDtoList);
        }
        throw new RestException("请指定要查找的用户ID或用户名", HttpStatus.BAD_REQUEST);
    }

    //TODO show specific user
    public ResponseEntity<UserDto> search() {
        return ResponseEntity.ok(new UserDto());
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
    public ResponseEntity<Collection<UserDto>> search(UserSearchRequest request, AuthUserDto authUser) {
        List<UserDto> userDtoList = userService.findByKeyword(request.getQ(), authUser.getId());
        //TODO add relation state
        return ResponseEntity.ok(userDtoList);
    }

    @ApiOperation(value = "根据用户名获取指定用户,用于授权接口")
    @ApiImplicitParam(value = "用户名", paramType = "path", required = true)
    @GetMapping("/authenticate}")
    public ResponseEntity<AuthUserDto> getUserByScreenNameForAuthenticate(String screenName) {
        AuthUserDto authUserDto = userService.findUserByScreenNameForAuthenticate(screenName);
        return ResponseEntity.ok(authUserDto);
    }

}
