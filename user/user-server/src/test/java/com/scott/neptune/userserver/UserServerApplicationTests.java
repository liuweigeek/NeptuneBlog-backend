package com.scott.neptune.userserver;

import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServerApplicationTests {

    @Resource
    private IUserService userService;

    @Test
    public void contextLoads() {
        UserDto userDto = userService.findUserById(11L, null, false);
        Assertions.assertNotNull(userDto);
    }

}
