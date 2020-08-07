package com.scott.neptune.authentication.service;

import com.scott.neptune.authentication.convertor.AuthUserConvertor;
import com.scott.neptune.authentication.domain.AuthUser;
import com.scott.neptune.authentication.jwt.JwtTokenProvider;
import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: scott
 * @Email: <a href="wliu@fleetup.com">scott</a>
 * @Date: 2020/7/27 10:02
 * @Description:
 */
@Service
public class AuthUserService implements UserDetailsService {

    private final UserClient userClient;
    private final AuthUserConvertor authUserConvertor;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthUserService(UserClient userClient,
                           AuthUserConvertor authUserConvertor,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userClient = userClient;
        this.authUserConvertor = authUserConvertor;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO change to username
        AuthUserDto authUserDto = userClient.findUserByUsernameForAuthenticate(username);
        if (authUserDto == null) {
            throw new RestException("指定用不不存在", HttpStatus.NOT_FOUND);
        }
        return authUserConvertor.convertToEntity().apply(authUserDto);
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
