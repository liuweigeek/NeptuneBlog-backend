package com.scott.neptune.user.service.impl;

import com.google.common.collect.Lists;
import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.common.util.LocaleUtil;
import com.scott.neptune.user.entity.UserEntity;
import com.scott.neptune.user.repository.UserRepository;
import com.scott.neptune.user.service.IUserService;
import com.scott.neptune.user.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private MessageSource messageSource;
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
    public ServerResponse<UserEntity> login(String username, String password) {

        UserEntity userEntity = userRepository.getByUsername(username);
        if (userEntity == null) {
            return ServerResponse.createByErrorMessage(messageSource.getMessage("error.userNotExist", null,
                    LocaleUtil.getLocaleFromUser(null)));
        }

        //TODO 加密
        String md5Password = password;
        if (!StringUtils.equals(userEntity.getPassword(), md5Password)) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        userEntity.setLoginDate(new Date());
        userEntity.setToken(UserUtil.generateTokenByUser(userEntity));
        userRepository.save(userEntity);

        UserDto userDto = UserUtil.convertToDto(userEntity);
        redisTemplate.opsForValue().set(userEntity.getToken(), userDto);
        redisTemplate.expire(userEntity.getToken(), 30, TimeUnit.MINUTES);
        userEntity.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess(messageSource.getMessage("success.loginSuccess", null, LocaleUtil.getLocaleFromUser(userDto)), userEntity);
    }

    /**
     * 保存用户
     *
     * @param userEntity 用户对象
     * @return 保存结果
     */
    @Override
    public ServerResponse<UserEntity> save(UserEntity userEntity) {
        userEntity.setRegisterDate(new Date());
        try {
            return ServerResponse.createBySuccess(userRepository.save(userEntity));
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage(messageSource.getMessage("error.saveUserError", null, LocaleUtil.getLocaleFromUser(null)));
        }
    }

    /**
     * 通过ID获取用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @Override
    public UserEntity getUserById(String id) {
        return userRepository.getOne(id);
    }

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    /**
     * 获取全部用户
     *
     * @return 用户列表
     */
    @Override
    public List<UserEntity> findUserList() {
        return userRepository.findAll();
    }

    /**
     * 通过用户ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户对象列表
     */
    @Override
    public List<UserEntity> findAllUserByIdList(List<String> idList) {
        if (idList == null || idList.size() <= 0) {
            return Lists.newArrayListWithCapacity(0);
        }
        return userRepository.findAllByIdIn(idList);
    }
}
