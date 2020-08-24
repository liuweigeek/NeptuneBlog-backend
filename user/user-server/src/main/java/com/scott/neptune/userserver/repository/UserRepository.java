package com.scott.neptune.userserver.repository;

import com.scott.neptune.userserver.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/7/28 16:19
 * @Description:
 */
public interface UserRepository extends JpaRepository<UserEntity, Long>,
        JpaSpecificationExecutor<UserEntity> {

    /**
     * 根据用户ID列表获取全部用户
     *
     * @param ids 用户ID列表
     * @return
     */
    Collection<UserEntity> findAllByIdIn(Collection<Long> ids);

    /**
     * 根据用户名列表获取全部用户
     *
     * @param usernames 用户名列表
     * @return
     */
    Collection<UserEntity> findAllByUsernameIn(@Param("usernames") Collection<String> usernames);

    /**
     * 更新登录时间
     *
     * @param loginDate
     * @param userId
     * @return
     */
    @Modifying
    @Query(value = "update UserEntity u set u.loginDate = :loginDate where u.id = :userId")
    int updateLoginDateByUserId(@Param("loginDate") Date loginDate, @Param("userId") Long userId);

}
