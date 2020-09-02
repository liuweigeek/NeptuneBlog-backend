package com.scott.neptune.userserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.convertor.AuthUserEntityConvertor;
import com.scott.neptune.userserver.convertor.UserConvertor;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import com.scott.neptune.userserver.repository.UserRepository;
import com.scott.neptune.userserver.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/23 09:44
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserConvertor userConvertor;
    private final AuthUserEntityConvertor authUserEntityConvertor;
    private final PasswordEncoder passwordEncoder;

    /**
     * 判断指定用户名是否存在
     *
     * @param username 用户名
     * @return 判断结果
     */
    @Override
    public boolean existsByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }
        ExampleMatcher usernameExampleMatcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withIgnoreNullValues();
        return userRepository.exists(Example.of(UserEntity.builder().username(username).build(), usernameExampleMatcher));
    }

    /**
     * 判断指定用户邮箱是否存在
     *
     * @param email 邮箱地址
     * @return 判断结果
     */
    @Override
    public boolean existsByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }
        ExampleMatcher emailExampleMatcher = ExampleMatcher.matching()
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withIgnoreNullValues();
        return userRepository.exists(Example.of(UserEntity.builder().email(email).build(), emailExampleMatcher));
    }

    /**
     * 保存用户
     *
     * @param userDto 用户对象
     * @return 保存结果
     */
    @Override
    public UserDto save(UserDto userDto) {
        if (this.existsByEmail(userDto.getEmail())) {
            throw new NeptuneBlogException("用户邮箱已存在");
        }
        if (this.existsByUsername(userDto.getUsername())) {
            throw new NeptuneBlogException("用户名已存在,请更换后重试");
        }
        UserEntity userEntity = userConvertor.convertToEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);
        return userConvertor.convertToDto(userEntity);
    }

    /**
     * 通过ID获取用户
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    @Override
    public UserDto findUserById(Long userId, Long loginUserId) {
        if (userId == null) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }
        return userRepository.findById(userId)
                .map(userConvertor.convertToDto())
                .orElseThrow(() -> new NeptuneBlogException("指定用户不存在"));
    }

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public UserDto findUserByUsername(String username, Long loginUserId) {
        if (StringUtils.isBlank(username)) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }
        ExampleMatcher usernameExampleMatcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withIgnoreNullValues();

        return userRepository.findOne(Example.of(UserEntity.builder().username(username).build(), usernameExampleMatcher))
                .map(userConvertor.convertToDto())
                .orElseThrow(() -> new NeptuneBlogException("username not found"));
    }


    /**
     * 通过邮箱获取用户
     *
     * @param email 邮箱
     * @return 用户对象
     */
    @Override
    public UserDto findUserByEmail(String email, Long loginUserId) {
        if (StringUtils.isBlank(email)) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }
        ExampleMatcher usernameExampleMatcher = ExampleMatcher.matching()
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withIgnoreNullValues();

        return userRepository.findOne(Example.of(UserEntity.builder().email(email).build(), usernameExampleMatcher))
                .map(userConvertor.convertToDto())
                .orElseThrow(() -> new NeptuneBlogException("email not found"));
    }

    /**
     * 通过用户ID列表获取全部用户
     *
     * @param ids 用户ID列表
     * @return 用户对象列表
     */
    @Override
    public Collection<UserDto> findAllUserByIdList(Collection<Long> ids, Long loginUserId) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userRepository.findAllByIdIn(ids).stream()
                .map(userConvertor.convertToDto())
                .collect(Collectors.toList());
    }

    /**
     * 通过用户名列表获取全部用户
     *
     * @param usernameList 用户名列表
     * @return 用户对象列表
     */
    @Override
    public Collection<UserDto> findAllUserByUsernameList(Collection<String> usernameList, Long loginUserId) {
        if (CollectionUtils.isEmpty(usernameList)) {
            return Collections.emptyList();
        }
        return userRepository.findAllByUsernameIn(usernameList).stream()
                .map(userConvertor.convertToDto())
                .collect(Collectors.toList());
    }

    @Override
    public AuthUserDto findUserByUsernameForAuthenticate(String username) {
        if (StringUtils.isBlank(username)) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }
        ExampleMatcher usernameExampleMatcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withIgnoreNullValues();
        return userRepository.findOne(Example.of(UserEntity.builder().username(username).build(), usernameExampleMatcher))
                .map(authUserEntityConvertor::convertToDto)
                .orElseThrow(() -> new NeptuneBlogException("username not found"));
    }

    /**
     * 通过关键字搜索用户
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @Override
    public Collection<UserDto> search(String keyword, Long loginUserId) {
        if (StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }
        return userRepository.findAll((root, query, criteriaBuilder) ->
                query.where(
                        criteriaBuilder.or(
                                criteriaBuilder.like(root.get("username").as(String.class), "%" + keyword + "%"),
                                criteriaBuilder.like(root.get("nickname").as(String.class), "%" + keyword + "%"),
                                criteriaBuilder.like(root.get("email").as(String.class), "%" + keyword + "%"))
                ).orderBy(criteriaBuilder.asc(root.get("createAt").as(Date.class)))
                        .getRestriction()).stream()
                .map(userConvertor.convertToDto())
                .collect(Collectors.toList());
    }

    /**
     * 上传头像
     *
     * @param avatarFile 头像文件
     * @param userDto    用户
     * @return 上传结果
     */
/*    @Override
    public ServerResponse<List<UserAvatarEntity>> uploadAvatar(MultipartFile avatarFile, UserDto userDto) {
        ServerResponse<List<UserAvatarDto>> uploadAvatarRes = fileClient.uploadAvatar(avatarFile);
        if (uploadAvatarRes.isFailed()) {
            return ServerResponse.createByErrorMessage(uploadAvatarRes.getMsg());
        }
        List<UserAvatarDto> avatarDtoList = uploadAvatarRes.getData();
        String mediumAvatar = null;
        for (UserAvatarDto avatarDto : avatarDtoList) {
            avatarDto.setUserId(userDto.getId());
            mediumAvatar = avatarDto.getUrl();
        }
        List<UserAvatarEntity> avatarEntityList = userAvatarConvertor.convertToEntityList(avatarDtoList);
        userAvatarService.delete(UserAvatarEntity.builder().userId(userDto.getId()).build());
        try {
            userAvatarService.saveList(avatarEntityList);
            UserEntity userEntity = userConvertor.convertToEntity(userDto);
            userEntity.setAvatar(mediumAvatar);
            entityManager.merge(userEntity);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.error("uploadAvatar exception: ", e);
            return ServerResponse.createByErrorMessage("保存头像失败");
        }
    }*/
}
