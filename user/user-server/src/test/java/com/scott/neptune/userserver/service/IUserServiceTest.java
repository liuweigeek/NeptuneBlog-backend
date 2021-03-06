package com.scott.neptune.userserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/26 21:39
 * @Description:
 */
@SpringBootTest
class IUserServiceTest {

    @Resource
    private IUserService userService;

    @Test
    void existsByUsername() {
        boolean exists = userService.existsByUsername("liuweigeek");
        Assertions.assertFalse(exists);
    }

    @Test
    void existsByEmail() {
    }

    @Test
    void save() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void findUserByUsername() {
    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void findAllUserByIdList() {
    }

    @Test
    void findAllUserByUsernameList() {
    }

    @Test
    void findUserByUsernameForAuthenticate() {
    }

    @Test
    void search() {
    }
}