package com.scott.neptune.user.controller;

import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.component.UserComponent;
import com.scott.neptune.user.entity.User;
import com.scott.neptune.user.service.IUserService;
import com.scott.neptune.user.util.HeaderUtil;
import com.scott.neptune.user.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 用户接口
 */
@Slf4j
@RestController
@RefreshScope
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;
    @Resource
    private UserComponent userComponent;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 注册
     *
     * @param user          用户对象
     * @param bindingResult 校验结果
     * @return 注册结果
     */
    @PostMapping(value = "/register")
    public ServerResponse register(@Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMsgList = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(toList());

            return ServerResponse.createByErrorMessage(errorMsgList.toString());
        }

        if (userService.existByUsername(user.getUsername())) {
            String errorMessage = String.format(messageSource.getMessage("error.userExist", null, userComponent.getUserLocale(null)), user.getUsername());
            return ServerResponse.createByErrorMessage(errorMessage);
        }
        ServerResponse registerResponse = userService.save(user);
        if (registerResponse.isSuccess()) {
            return ServerResponse.createBySuccess();
        } else {
            return registerResponse;
        }
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping(value = "/login")
    public ServerResponse login(String username, String password) {

        if (!userService.existByUsername(username)) {
            return ServerResponse.createByErrorMessage(messageSource.getMessage("error.userNotFound", null, userComponent.getUserLocale(null)));
        }

        //TODO 密码加密
        ServerResponse<User> loginResponse = userService.login(username, password);
        if (!loginResponse.isSuccess()) {
            return loginResponse;
        }

        User loginUser = loginResponse.getData();
        HeaderUtil.set(response, Constant.Login.CURRENT_USER, loginUser.getToken());
        return ServerResponse.createBySuccess(UserUtil.convertToDto(loginUser));
    }

    /**
     * 用户退出登录
     *
     * @return 退出登录结果
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
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
    @GetMapping(value = "/getUserInfo")
    public ServerResponse<UserDto> getUserInfo() {

        UserDto userDto = userComponent.getUserFromRequest(request);
        return ServerResponse.createBySuccess(userDto);
    }

    /**
     * 获取全部用户列表
     *
     * @return 用户列表
     */
    @GetMapping(value = "/list")
    public ServerResponse list() {

        List<UserDto> userDtoList = userService.findUserList()
                .stream()
                .map(UserUtil::convertToDto)
                .collect(toList());
        return ServerResponse.createBySuccess(userDtoList);
    }

}
