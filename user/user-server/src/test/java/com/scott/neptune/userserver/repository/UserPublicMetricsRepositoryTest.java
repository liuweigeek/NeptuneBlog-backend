package com.scott.neptune.userserver.repository;

import com.scott.neptune.userserver.domain.entity.UserEntity;
import com.scott.neptune.userserver.domain.valueobject.UserPublicMetricsValObj;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserPublicMetricsRepositoryTest {

    private final UserPublicMetricsRepository userPublicMetricsRepository;

    public UserPublicMetricsRepositoryTest(@Autowired UserPublicMetricsRepository userPublicMetricsRepository) {
        this.userPublicMetricsRepository = userPublicMetricsRepository;
    }

    @Test
    void getByUserId() {
        UserPublicMetricsValObj publicMetricsValObj = userPublicMetricsRepository.getByUserId(12L);
        UserEntity userEntity = publicMetricsValObj.getUser();

        Assertions.assertTrue(publicMetricsValObj != null && userEntity != null);
    }
}