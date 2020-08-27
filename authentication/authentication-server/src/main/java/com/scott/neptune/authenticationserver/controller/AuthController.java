package com.scott.neptune.authenticationserver.controller;

import com.scott.neptune.authenticationclient.dto.LoginUserInfo;
import com.scott.neptune.authenticationserver.service.AuthUserService;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<LoginUserInfo> signIn(String username, String password) {
        LoginUserInfo userDto = authUserService.signIn(username, password);
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
    public ResponseEntity<LoginUserInfo> signUp(@RequestBody UserDto userDto) {
        LoginUserInfo newUser = authUserService.signUp(userDto);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<AuthUserDto> loadUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(authUserService.loadAuthUserByUsername(username));
    }
}
