package com.scott.neptune.user.service.impl;

import com.google.common.collect.Lists;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.common.util.MD5Utils;
import com.scott.neptune.user.entity.UserAvatarEntity;
import com.scott.neptune.user.entity.UserEntity;
import com.scott.neptune.user.mapper.UserMapper;
import com.scott.neptune.user.mapping.UserAvatarModelMapping;
import com.scott.neptune.user.mapping.UserModelMapping;
import com.scott.neptune.user.remote.client.FileClient;
import com.scott.neptune.user.service.IUserAvatarService;
import com.scott.neptune.user.service.IUserService;
import com.scott.neptune.user.util.UserUtil;
import com.scott.neptune.userapi.dto.UserAvatarDto;
import com.scott.neptune.userapi.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:44
 * @Description:
 */
@Slf4j
@Transactional
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserModelMapping userModelMapping;
    @Resource
    private UserAvatarModelMapping userAvatarModelMapping;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private FileClient fileClient;
    @Resource
    private IUserAvatarService userAvatarService;

    /**
     * 判断指定用户名是否存在
     *
     * @param username 用户名
     * @return 判断结果
     */
    @Override
    public boolean existsByUsername(String username) {
        return userMapper.exists(UserEntity.builder().username(username).build());
    }

    /**
     * 判断指定用户邮箱是否存在
     *
     * @param email 邮箱地址
     * @return 判断结果
     */
    @Override
    public boolean existsByEmail(String email) {
        return userMapper.exists(UserEntity.builder().email(email).build());
    }

    /**
     * 用户登录
     *
     * @param email    邮箱地址
     * @param password 密码
     * @return
     */
    @Override
    public ServerResponse<UserDto> login(String email, String password) {

        UserEntity userEntity = userMapper.getOne(UserEntity.builder().email(email).build(), null);
        if (Objects.isNull(userEntity)) {
            return ServerResponse.createByErrorMessage("用户不存在，请检查邮箱地址是否正确");
        }

        String md5Password = MD5Utils.MD5EncodeUtf8(password);
        if (!StringUtils.equals(userEntity.getPassword(), md5Password)) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        userEntity.setLoginDate(new Date());
        userEntity.setToken(UserUtil.generateTokenByUser(userEntity));
        userMapper.updateById(userEntity);

        UserDto userDto = userModelMapping.convertToDto(userEntity);
        redisTemplate.opsForValue().set(userEntity.getToken(), userDto, 30, TimeUnit.MINUTES);

        return ServerResponse.createBySuccess("登录成功", userDto);
    }

    /**
     * 保存用户
     *
     * @param userEntity 用户对象
     * @return 保存结果
     */
    @Override
    public ServerResponse<UserEntity> save(UserEntity userEntity) {
        if (this.existsByEmail(userEntity.getEmail())) {
            return ServerResponse.createByErrorMessage("用户邮箱已存在");
        }
        if (this.existsByUsername(userEntity.getUsername())) {
            return ServerResponse.createByErrorMessage("用户名已存在,请更换后重试");
        }
        userEntity.setRegisterDate(new Date());
        userEntity.setPassword(MD5Utils.MD5EncodeUtf8(userEntity.getPassword()));
        try {
            userMapper.insert(userEntity);
            return ServerResponse.createBySuccess(userEntity);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("新建用户失败");
        }
    }

    /**
     * 通过ID获取用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @Override
    public UserEntity getUserById(String id, String loginUserId) {
        try {
            return userMapper.getOne(UserEntity.builder().id(id).build(), loginUserId);
        } catch (Exception e) {
            log.error("getUserByUsername exception: ", e);
            return null;
        }
    }

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public UserEntity getUserByUsername(String username, String loginUserId) {
        try {
            return userMapper.getOne(UserEntity.builder().username(username).build(), loginUserId);
        } catch (Exception e) {
            log.error("getUserByUsername exception: ", e);
            return null;
        }
    }

    /**
     * 通过关键字搜索用户
     *
     * @param keyword
     * @return
     */
    @Override
    public List<UserEntity> findByKeyword(String keyword, String loginUserId) {
        try {
            return userMapper.findByKeyword(keyword, loginUserId);
        } catch (Exception e) {
            log.error("getUserByKeyword exception: ", e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取全部用户
     *
     * @return 用户列表
     */
    @Override
    public List<UserEntity> findUserList(String loginUserId) {
        try {
            return userMapper.findAll(null, loginUserId);
        } catch (Exception e) {
            log.error("findUserList exception: ", e);
            return Collections.emptyList();
        }
    }

    /**
     * 通过用户ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户对象列表
     */
    @Override
    public List<UserEntity> findAllUserByIdList(List<String> idList, String loginUserId) {
        if (CollectionUtils.isEmpty(idList)) {
            return Lists.newArrayListWithCapacity(0);
        }
        try {
            return userMapper.findAllInUserIds(idList, loginUserId);
        } catch (Exception e) {
            log.error("findAllUserByIdList exception: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public ServerResponse<List<UserAvatarEntity>> uploadAvatar(MultipartFile avatarFile, UserDto userDto) {
        ServerResponse<List<UserAvatarDto>> uploadAvatarRes = fileClient.uploadAvatar(avatarFile);
        if (uploadAvatarRes.isFailed()) {
            return ServerResponse.createByErrorMessage(uploadAvatarRes.getMsg());
        }
        List<UserAvatarDto> avatarDtoList = uploadAvatarRes.getData();
        avatarDtoList.forEach(avatarDto -> avatarDto.setUserId(userDto.getId()));
        List<UserAvatarEntity> avatarEntityList = userAvatarModelMapping.convertToEntityList(avatarDtoList);

        userAvatarService.delete(UserAvatarEntity.builder().userId(userDto.getId()).build());
        ServerResponse<List<UserAvatarEntity>> saveRes = userAvatarService.saveList(avatarEntityList);
        if (saveRes.isFailed()) {
            return ServerResponse.createByErrorMessage("保存头像失败");
        }
        return ServerResponse.createBySuccess(saveRes.getData());
    }
}
