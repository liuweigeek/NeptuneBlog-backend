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
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
     * @param authUser
     * @return
     */
    @ApiOperation(value = "获取指定用户与登录用户的关系")
    @ApiImplicitParam(name = "userIds", value = "要查询的用户ID列表，用[,]分割", paramType = "query", dataTypeClass = String.class)
    @GetMapping
    public ResponseEntity<Collection<RelationshipDto>> lookUpByIds(String userIds, @ApiIgnore AuthUserDto authUser) {
        if (StringUtils.isBlank(userIds)) {
            throw new RestException("请传入要查询的用户ID", HttpStatus.BAD_REQUEST);
        }
        List<Long> userIdList = Arrays.stream(StringUtils.split(userIds, ","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        Collection<RelationshipDto> relationshipList = friendshipService.getRelationshipByIds(userIdList, authUser.getId());
        return ResponseEntity.ok(relationshipList);
    }

    /**
     * 获取指定用户与登录用户的关系
     *
     * @param usernames
     * @param authUser
     * @return
     */
    @ApiOperation(value = "获取指定用户与登录用户的关系")
    @ApiImplicitParam(name = "usernames", value = "要查询的用户名列表，用[,]分割", paramType = "query", dataTypeClass = String.class)
    @GetMapping("/username")
    public ResponseEntity<Collection<RelationshipDto>> lookUpByUsernames(String usernames,
                                                                         @ApiIgnore AuthUserDto authUser) {

        if (StringUtils.isBlank(usernames)) {
            throw new RestException("请传入要查询的用户名", HttpStatus.BAD_REQUEST);
        }

        List<String> usernameList = usernameList = Arrays.stream(StringUtils.split(usernames, ","))
                .collect(Collectors.toList());
        Collection<RelationshipDto> relationshipList = friendshipService.getRelationshipByUsernames(usernameList, authUser.getId());
        return ResponseEntity.ok(relationshipList);
    }

    /**
     * 获取指定用户与登录用户的关系
     *
     * @param id
     * @param authUser
     * @return
     */
    @ApiOperation(value = "获取指定用户与登录用户的关系")
    @ApiImplicitParam(name = "id", value = "要查询的用户ID", paramType = "query", dataTypeClass = Long.class)
    @GetMapping("/{id}")
    public ResponseEntity<RelationshipDto> getFriendshipById(@PathVariable("id") Long id, @ApiIgnore AuthUserDto authUser) {
        Collection<RelationshipDto> relationshipList = friendshipService.getRelationshipByIds(Collections.singleton(id), authUser.getId());
        if (CollectionUtils.isEmpty(relationshipList)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(Objects.requireNonNull(relationshipList.stream().findFirst().orElse(null)));
    }

    /**
     * 关注指定用户
     *
     * @param id       指定用户ID
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "关注指定用户")
    @ApiImplicitParam(name = "id", value = "要关注的用户ID", paramType = "query", dataTypeClass = Long.class, required = true)
    @PostMapping
    public ResponseEntity<FriendshipDto> addFriendship(@RequestParam Long id, @ApiIgnore AuthUserDto authUser) {
        UserDto targetUser = userService.findUserById(id, authUser.getId());
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
     * @param id       指定用户ID
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "取消关注指定用户")
    @ApiImplicitParam(name = "id", value = "要取消关注的用户ID", paramType = "query", dataTypeClass = Long.class, required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteFriendship(@PathVariable("id") Long id, @ApiIgnore AuthUserDto authUser) {
        UserDto targetUser = userService.findUserById(id, authUser.getId());

        if (targetUser == null) {
            throw new RestException("用户不存在", HttpStatus.NOT_FOUND);
        }
        friendshipService.delete(authUser.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
