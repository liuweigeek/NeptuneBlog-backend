package com.scott.neptune.userserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.convertor.AuthUserConvertor;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/23 09:44
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserConvertor userConvertor;
    private final AuthUserConvertor authUserConvertor;
    private final PasswordEncoder passwordEncoder;

    /**
     * 判断指定用户名是否存在
     *
     * @param screenName 用户名
     * @return 判断结果
     */
    @Override
    public boolean existsByScreenName(String screenName) {
        if (StringUtils.isBlank(screenName)) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }
        ExampleMatcher screenNameExampleMatcher = ExampleMatcher.matching()
                .withMatcher("screenName", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withIgnoreNullValues();
        return userRepository.exists(Example.of(UserEntity.builder().screenName(screenName).build(), screenNameExampleMatcher));
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
        if (this.existsByScreenName(userDto.getScreenName())) {
            throw new NeptuneBlogException("用户名已存在,请更换后重试");
        }
        UserEntity userEntity = userConvertor.convertToEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        //TODO create by event
        userEntity.setCreateAt(new Date());
        userEntity.setLoginDate(new Date());

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
     * @param screenName 用户名
     * @return 用户对象
     */
    @Override
    public UserDto findUserByScreenName(String screenName, Long loginUserId) {
        if (StringUtils.isBlank(screenName)) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }
        ExampleMatcher screenNameExampleMatcher = ExampleMatcher.matching()
                .withMatcher("screenName", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withIgnoreNullValues();

        return userRepository.findOne(Example.of(UserEntity.builder().screenName(screenName).build(), screenNameExampleMatcher))
                .map(userConvertor.convertToDto())
                .orElseThrow(() -> new NeptuneBlogException("screenName not found"));
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
        ExampleMatcher screenNameExampleMatcher = ExampleMatcher.matching()
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withIgnoreNullValues();

        return userRepository.findOne(Example.of(UserEntity.builder().email(email).build(), screenNameExampleMatcher))
                .map(userConvertor.convertToDto())
                .orElseThrow(() -> new NeptuneBlogException("email not found"));
    }

    /**
     * 通过关键字搜索用户
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @Override
    public List<UserDto> findByKeyword(String keyword, Long loginUserId) {
        if (StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }
        return userRepository.findAll((root, query, criteriaBuilder) ->
                query.where(
                        criteriaBuilder.or(
                                criteriaBuilder.like(root.get("screenName").as(String.class), "%" + keyword + "%"),
                                criteriaBuilder.like(root.get("nickname").as(String.class), "%" + keyword + "%"),
                                criteriaBuilder.like(root.get("email").as(String.class), "%" + keyword + "%"))
                ).orderBy(criteriaBuilder.asc(root.get("createAt").as(Date.class)))
                        .getRestriction()).stream()
                .map(userConvertor.convertToDto())
                .collect(Collectors.toList());
    }


    /**
     * 通过用户ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户对象列表
     */
    @Override
    public List<UserDto> findAllUserByIdList(List<Long> idList, Long loginUserId) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        return userRepository.findAllByIdIn(idList).stream()
                .map(userConvertor.convertToDto())
                .collect(Collectors.toList());
    }

    /**
     * 通过用户名列表获取全部用户
     *
     * @param screenNameList 用户名列表
     * @return 用户对象列表
     */
    @Override
    public List<UserDto> findAllUserByScreenNameList(List<String> screenNameList, Long loginUserId) {
        if (CollectionUtils.isEmpty(screenNameList)) {
            return Collections.emptyList();
        }
        return userRepository.findAllByScreenNameIn(screenNameList).stream()
                .map(userConvertor.convertToDto())
                .collect(Collectors.toList());
    }

    @Override
    public AuthUserDto findUserByScreenNameForAuthenticate(String screenName) {
        if (StringUtils.isBlank(screenName)) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }
        ExampleMatcher screenNameExampleMatcher = ExampleMatcher.matching()
                .withMatcher("screenName", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withIgnoreNullValues();
        //TODO add a new convertor
        return userRepository.findOne(Example.of(UserEntity.builder().screenName(screenName).build(), screenNameExampleMatcher))
                .map(authUserConvertor::convertToDto)
                .orElseThrow(() -> new NeptuneBlogException("screenName not found"));
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
