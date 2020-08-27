package com.scott.neptune.authenticationserver.service;

import com.scott.neptune.authenticationclient.dto.LoginUserInfo;
import com.scott.neptune.authenticationclient.jwt.JwtTokenProvider;
import com.scott.neptune.authenticationserver.convertor.AuthUserConvertor;
import com.scott.neptune.authenticationserver.domain.AuthUser;
import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/7/27 10:02
 * @Description:
 */
@RequiredArgsConstructor
@Service
public class AuthUserService implements UserDetailsService {

    private final UserClient userClient;
    private final AuthUserConvertor authUserConvertor;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserDto authUserDto = userClient.getUserByUsernameForAuthenticate(username);
        if (authUserDto == null) {
            throw new RestException("指定用户不存在", HttpStatus.NOT_FOUND);
        }
        return authUserConvertor.convertToEntity(authUserDto);
    }

    public AuthUserDto loadAuthUserByUsername(String username) {
        return authUserConvertor.convertToDto(this.loadUserByUsername(username));
    }

    public LoginUserInfo signIn(String username, String password) {

        AuthUserDto authUser = this.loadAuthUserByUsername(username);
        //ensure it's includes field of password
        if (!passwordEncoder.matches(password, authUser.getPassword())) {
            throw new NeptuneBlogException("密码错误");
        }
        String token = jwtTokenProvider.createToken(authUser, null);
        LoginUserInfo loginUserInfo = LoginUserInfo.newInstance(userClient.getUserById(authUser.getId()));
        loginUserInfo.setToken(token);
        return loginUserInfo;
    }

    public LoginUserInfo signUp(UserDto userDto) {
        return LoginUserInfo.newInstance(userClient.addUser(userDto));
    }
}
