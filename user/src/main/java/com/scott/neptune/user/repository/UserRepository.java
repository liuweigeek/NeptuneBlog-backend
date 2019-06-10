package com.scott.neptune.user.repository;

import com.scott.neptune.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 根据用户名判断用户是否存在
     *
     * @param username 用户名
     * @return 判断结果
     */
    boolean existsByUsername(String username);

    /**
     * 根据用户名和密码获取指定用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User findByUsernameAndPassword(String username, String password);

    /**
     * 根据用户ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户列表
     */
    List<User> findAllByIdIn(List<String> idList);

}
