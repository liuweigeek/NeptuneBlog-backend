package com.scott.neptune.user.service.impl;

import com.google.common.collect.Lists;
import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.entity.User;
import com.scott.neptune.user.repository.UserRepository;
import com.scott.neptune.user.service.IUserService;
import com.scott.neptune.user.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断指定用户名是否存在
     *
     * @param username 用户名
     * @return 判断结果
     */
    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public ServerResponse<User> login(String username, String password) {

        boolean userExist = userRepository.existsByUsername(username);
        if (!userExist) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //TODO 加密
        String md5Password = password;
        User user = userRepository.findByUsernameAndPassword(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setLoginDate(new Date());
        user.setToken(UserUtil.generateTokenByUser(user));
        userRepository.save(user);

        UserDto userDto = UserUtil.convertToDto(user);
        redisTemplate.opsForValue().set(user.getToken(), userDto);
        redisTemplate.expire(user.getToken(), 30, TimeUnit.MINUTES);
        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess("登录成功", user);
    }

    /**
     * 保存用户
     *
     * @param user 用户对象
     * @return 保存结果
     */
    @Override
    public ServerResponse<User> save(User user) {
        user.setRegisterDate(new Date());
        return ServerResponse.createBySuccess(userRepository.save(user));
    }

    /**
     * 通过ID获取用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @Override
    public User getUserById(String id) {
        return userRepository.getOne(id);
    }

    /**
     * 获取全部用户
     *
     * @return 用户列表
     */
    @Override
    public List<User> findUserList() {
        return userRepository.findAll();
    }

    /**
     * 通过用户ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户对象列表
     */
    @Override
    public List<User> findAllUserByIdList(List<String> idList) {
        if (idList == null || idList.size() <= 0) {
            return Lists.newArrayListWithCapacity(0);
        }

        return userRepository.findAllByIdIn(idList);
    }
}
