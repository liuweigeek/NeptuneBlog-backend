package com.scott.neptune.authentication.service;

import com.scott.neptune.authentication.convertor.AuthUserConvertor;
import com.scott.neptune.authentication.domain.AuthUser;
import com.scott.neptune.authentication.jwt.JwtTokenProvider;
import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2020/7/27 10:02
 * @Description:
 */
@RequiredArgsConstructor
@Service
public class AuthUserService implements UserDetailsService {

    //TODO injection
    private final UserClient userClient;
    private final AuthUserConvertor authUserConvertor;
    //TODO injection
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO change to username
        AuthUserDto authUserDto = userClient.findUserByScreenNameForAuthenticate(username);
        if (authUserDto == null) {
            throw new RestException("指定用不不存在", HttpStatus.NOT_FOUND);
        }
        return authUserConvertor.convertToEntity(authUserDto);
    }

    public UserDto signIn(String username, String password) {

        AuthUser authUser = this.loadUserByUsername(username);

        //ensure it's includes field of password
        if (!passwordEncoder.matches(password, authUser.getPassword())) {
            throw new NeptuneBlogException("密码错误");
        }
        String token = jwtTokenProvider.createToken(authUser, null);
        UserDto userDto = new UserDto();
        //TODO use convertor
        BeanUtils.copyProperties(authUser, userDto);
        userDto.setToken(token);
        return userDto;
    }

    public UserDto signUp(UserDto userDto) {
        return userClient.addUser(userDto);
    }
}
