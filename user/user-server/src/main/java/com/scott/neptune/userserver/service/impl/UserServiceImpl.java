package com.scott.neptune.userserver.service.impl;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.component.FriendshipComponent;
import com.scott.neptune.userserver.convertor.AuthUserEntityConvertor;
import com.scott.neptune.userserver.convertor.SimpleUserConvertor;
import com.scott.neptune.userserver.convertor.UserConvertor;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import com.scott.neptune.userserver.domain.valueobject.UserPublicMetricsValObj;
import com.scott.neptune.userserver.repository.UserPublicMetricsRepository;
import com.scott.neptune.userserver.repository.UserRepository;
import com.scott.neptune.userserver.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.Collection;
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
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserPublicMetricsRepository userPublicMetricsRepository;
    private final UserConvertor userConvertor;
    private final SimpleUserConvertor simpleUserConvertor;
    private final AuthUserEntityConvertor authUserEntityConvertor;
    private final PasswordEncoder passwordEncoder;
    private final FriendshipComponent friendshipComponent;

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
        UserPublicMetricsValObj userPublicMetrics = UserPublicMetricsValObj.builder()
                .user(userEntity)
                .followingCount(0)
                .followersCount(0)
                .tweetCount(0)
                .likedCount(0)
                .build();
        userPublicMetricsRepository.save(userPublicMetrics);
        userEntity.setPublicMetrics(userPublicMetrics);
        return userConvertor.convertToDto(userEntity);
    }

    /**
     * 通过ID获取用户
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    @Override
    public UserDto findUserById(Long userId, Long loginUserId, boolean includeRelations) {
        if (userId == null) {
            throw new RestException("请指定要查询的用户", HttpStatus.BAD_REQUEST);
        }
        return userRepository.findOne((root, query, criteriaBuilder) -> {
            root.fetch("publicMetrics", JoinType.LEFT);
            return query.where(criteriaBuilder.equal(root.get("id").as(Long.class), userId)).getRestriction();
        })
                .map(userConvertor::convertToDto)
                .map(userDto -> {
                    if (includeRelations) {
                        friendshipComponent.fillUserConnection(userDto, loginUserId);
                    }
                    return userDto;
                })
                .orElseThrow(() -> new NeptuneBlogException("指定用户不存在"));
    }

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public UserDto findUserByUsername(String username, Long loginUserId, boolean includeRelations) {
        if (StringUtils.isBlank(username)) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }

        return userRepository.findOne((root, query, criteriaBuilder) -> {
            root.fetch("publicMetrics", JoinType.LEFT);
            return query.where(criteriaBuilder.equal(root.get("username").as(String.class), username)).getRestriction();
        })
                .map(userConvertor::convertToDto)
                .map(userDto -> {
                    if (includeRelations) {
                        friendshipComponent.fillUserConnection(userDto, loginUserId);
                    }
                    return userDto;
                })
                .orElseThrow(() -> new NeptuneBlogException("指定用户不存在"));
    }


    /**
     * 通过邮箱获取用户
     *
     * @param email 邮箱
     * @return 用户对象
     */
    @Override
    public UserDto findUserByEmail(String email, Long loginUserId, boolean includeRelations) {
        if (StringUtils.isBlank(email)) {
            throw new NeptuneBlogException("请指定要查询的用户");
        }

        return userRepository.findOne((root, query, criteriaBuilder) -> {
            root.fetch("publicMetrics", JoinType.LEFT);
            return query.where(criteriaBuilder.equal(root.get("email").as(String.class), email)).getRestriction();
        })
                .map(userConvertor::convertToDto)
                .map(userDto -> {
                    if (includeRelations) {
                        friendshipComponent.fillUserConnection(userDto, loginUserId);
                    }
                    return userDto;
                })
                .orElseThrow(() -> new NeptuneBlogException("指定用户不存在"));
    }

    /**
     * 通过用户ID列表获取全部用户
     *
     * @param ids 用户ID列表
     * @return 用户对象列表
     */
    @Cacheable(cacheNames = "user:all", key = "#ids")
    @Override
    public Collection<UserDto> findAllUserByIdList(Collection<Long> ids, Long loginUserId, boolean includeRelations) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        Collection<UserDto> userDtoList = userRepository.findAll((root, query, criteriaBuilder) -> query.where(root.get("id").as(Long.class).in(ids)).getRestriction()).stream()
                .map(simpleUserConvertor.convertToDto())
                .collect(Collectors.toList());

        if (includeRelations) {
            friendshipComponent.fillUserConnections(userDtoList, loginUserId);
        }
        return userDtoList;
    }

    /**
     * 通过用户名列表获取全部用户
     *
     * @param usernameList 用户名列表
     * @return 用户对象列表
     */
    @Override
    public Collection<UserDto> findAllUserByUsernameList(Collection<String> usernameList, Long loginUserId, boolean includeRelations) {
        if (CollectionUtils.isEmpty(usernameList)) {
            return Collections.emptyList();
        }
        Collection<UserDto> userDtoList = userRepository.findAll((root, query, criteriaBuilder) -> query.where(root.get("username").as(String.class).in(usernameList)).getRestriction()).stream()
                .map(simpleUserConvertor.convertToDto())
                .collect(Collectors.toList());
        if (includeRelations) {
            friendshipComponent.fillUserConnections(userDtoList, loginUserId);
        }
        return userDtoList;
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
                .orElseThrow(() -> new NeptuneBlogException("指定用户不存在"));
    }

    /**
     * 通过关键字搜索用户
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @Override
    public Collection<UserDto> search(String keyword, Long loginUserId, boolean includeRelations) {
        if (StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }
        List<UserDto> userDtoList = userRepository.findAll((root, query, criteriaBuilder) ->
                query.where(
                        criteriaBuilder.or(
                                criteriaBuilder.like(root.get("username").as(String.class), "%" + keyword + "%"),
                                criteriaBuilder.like(root.get("name").as(String.class), "%" + keyword + "%"),
                                criteriaBuilder.equal(root.get("email").as(String.class), keyword))
                ).orderBy(criteriaBuilder.asc(root.get("createAt").as(Date.class)))
                        .getRestriction()).stream()
                .map(simpleUserConvertor.convertToDto())
                .collect(Collectors.toList());
        if (includeRelations) {
            friendshipComponent.fillUserConnections(userDtoList, loginUserId);
        }
        return userDtoList;
    }
}
