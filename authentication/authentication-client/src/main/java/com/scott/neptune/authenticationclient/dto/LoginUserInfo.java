package com.scott.neptune.authenticationclient.dto;

import com.scott.neptune.userclient.dto.UserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/27 23:38
 * @Description:
 */
@Data
public class LoginUserInfo extends UserDto {

    private String token;

    public static LoginUserInfo newInstance(UserDto userDto) {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtils.copyProperties(userDto, loginUserInfo);
        return loginUserInfo;
    }
}
