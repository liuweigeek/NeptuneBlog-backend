package com.scott.neptune.userserver.repository;

import com.scott.neptune.userserver.domain.valueobject.UserPublicMetricsValObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/10/10 20:26
 * @Description:
 */
public interface UserPublicMetricsRepository extends JpaRepository<UserPublicMetricsValObj, Long>,
        JpaSpecificationExecutor<UserPublicMetricsValObj> {

    UserPublicMetricsValObj getByUserId(@Param("userId") Long userId);
}
