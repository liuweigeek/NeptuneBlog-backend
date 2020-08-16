package com.scott.neptune.authentication.controller;

import com.scott.neptune.authentication.service.AuthUserService;
import com.scott.neptune.userclient.dto.UserDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/7/27 09:59
 * @Description:
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserService authUserService;

    public AuthController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<UserDto> signIn(String username, String password) {
        UserDto userDto = authUserService.signIn(username, password);
        return ResponseEntity.ok(userDto);
    }

    /**
     * 注册
     *
     * @param userDto 用户对象
     * @return 注册结果
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) {

        UserDto newUser = authUserService.signUp(userDto);
        return ResponseEntity.ok(newUser);
    }
}
