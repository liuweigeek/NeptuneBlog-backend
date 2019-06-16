package com.scott.neptune.user.repository;

import com.scott.neptune.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    /**
     * 根据用户名判断用户是否存在
     *
     * @param username 用户名
     * @return 判断结果
     */
    boolean existsByUsername(String username);

    /**
     * 通过用户获取指定用户
     *
     * @param username
     * @return
     */
    UserEntity getByUsername(String username);

    /**
     * 根据用户名和密码获取指定用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    UserEntity getByUsernameAndPassword(String username, String password);

    /**
     * 根据用户ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户列表
     */
    List<UserEntity> findAllByIdIn(List<String> idList);

}
