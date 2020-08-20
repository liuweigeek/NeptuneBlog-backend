package com.scott.neptune.userserver.api;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userclient.dto.RelationshipDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.service.IFriendshipService;
import com.scott.neptune.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/24 17:33
 * @Description: 好友关系接口
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "好友关系接口")
@RestController
@RequestMapping("/friendships")
public class FriendshipController extends BaseController {

    private final IUserService userService;
    private final IFriendshipService friendshipService;

    /**
     * 获取指定用户与登录用户的关系
     *
     * @param userIds
     * @param usernames
     * @param authUser
     * @return
     */
    @ApiOperation(value = "获取指定用户与登录用户的关系")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "要查询的用户ID列表，用[,]分割", paramType = "query"),
            @ApiImplicitParam(value = "要查询的用户名列表，用[,]分割", paramType = "query")
    })
    @GetMapping("/lookUp")
    public ResponseEntity<Collection<RelationshipDto>> lookUp(String userIds, String usernames,
                                                              @ApiIgnore AuthUserDto authUser) {
        List<Long> userIdList = Collections.emptyList();
        if (StringUtils.isNotBlank(userIds)) {
            userIdList = Arrays.stream(StringUtils.split(userIds, ","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }
        List<String> usernameList = Collections.emptyList();
        if (StringUtils.isNotBlank(usernames)) {
            usernameList = Arrays.stream(StringUtils.split(usernames, ","))
                    .collect(Collectors.toList());
        }
        List<RelationshipDto> relationshipList = friendshipService.getRelationship(userIdList, usernameList, authUser.getId());
        return ResponseEntity.ok(relationshipList);
    }

    /**
     * 获取指定用户与登录用户的关系
     *
     * @param userId
     * @param username
     * @param authUser
     * @return
     */
    @ApiOperation(value = "获取指定用户与登录用户的关系")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "要查询的用户ID", paramType = "query"),
            @ApiImplicitParam(value = "要查询的用户名", paramType = "query")
    })
    @GetMapping("/show")
    public ResponseEntity<RelationshipDto> show(Long userId, String username,
                                                @ApiIgnore AuthUserDto authUser) {
        return ResponseEntity.ok(new RelationshipDto());
    }

    /**
     * 关注指定用户
     *
     * @param userId   指定用户ID
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "关注指定用户")
    @ApiImplicitParam(value = "要关注的用户ID", paramType = "query", required = true)
    @PostMapping("/create")
    public ResponseEntity<FriendshipDto> create(Long userId, String username, AuthUserDto authUser) {
        UserDto targetUser = Optional.ofNullable(userId)
                .map(id -> userService.findUserById(id, authUser.getId()))
                .orElse(userService.findUserByScreenName(username, authUser.getId()));

        if (targetUser == null) {
            throw new RestException("用户不存在", HttpStatus.NOT_FOUND);
        }
        FriendshipDto friendshipDto = FriendshipDto.builder()
                .sourceId(authUser.getId())
                .targetId(targetUser.getId())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(friendshipService.save(friendshipDto));
    }

    /**
     * 取消关注指定用户
     *
     * @param userId   指定用户ID
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "取消关注指定用户")
    @ApiImplicitParam(name = "userId", value = "要取消关注的用户ID", paramType = "query", required = true)
    @PostMapping("/destroy")
    public ResponseEntity<Void> destroy(Long userId, String username, AuthUserDto authUser) {
        UserDto targetUser = Optional.ofNullable(userId)
                .map(id -> userService.findUserById(id, authUser.getId()))
                .orElse(userService.findUserByScreenName(username, authUser.getId()));

        if (targetUser == null) {
            throw new RestException("用户不存在", HttpStatus.NOT_FOUND);
        }
        friendshipService.delete(authUser.getId(), userId);
        return ResponseEntity.noContent().build();
    }
}
