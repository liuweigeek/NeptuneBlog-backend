package com.scott.neptune.userserver.repository;

import com.scott.neptune.userserver.UserServerApplicationTests;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class UserRepositoryTest extends UserServerApplicationTests {

    @Resource
    private UserRepository userRepository;

    @Test
    void testGetUserById() {
        UserEntity userEntity = userRepository.getOne(11L);
        Assertions.assertNotNull(userEntity);
    }

}