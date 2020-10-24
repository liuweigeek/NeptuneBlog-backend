package com.scott.neptune.userserver.repository;

import com.scott.neptune.userserver.domain.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserRepositoryTest {

    @Resource
    private UserRepository userRepository;

    @Test
    void testGetUserById() {
        UserEntity userEntity = userRepository.getOne(11L);
        Assertions.assertNotNull(userEntity);
    }

}