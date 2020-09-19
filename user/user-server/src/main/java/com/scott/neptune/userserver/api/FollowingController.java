package com.scott.neptune.userserver.api;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.userclient.command.FriendshipQueryRequest;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userserver.service.IFriendshipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;
import java.util.Optional;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/24 17:33
 * @Description: 已关注好友接口
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "已关注好友接口")
@RestController
@RequestMapping("/following")
public class FollowingController extends BaseController {

    private final IFriendshipService friendshipService;

    /**
     * 获取已关注用户ID列表
     *
     * @param request  指定用户视角
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "获取已关注用户ID列表")
    @GetMapping("/ids")
    public ResponseEntity<Page<Long>> findFollowingIds(FriendshipQueryRequest request, @ApiIgnore AuthUserDto authUser) {

        Long whomUserId = Optional.ofNullable(request.getUserId())
                .orElseGet(authUser::getId);
        return ResponseEntity.ok(friendshipService.findFollowing(whomUserId, request.getOffset(), request.getLimit())
                .map(FriendshipDto::getTargetId));
    }

    /**
     * 获取已关注用户列表
     *
     * @param request  指定用户视角
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "获取已关注用户列表")
    @GetMapping
    public ResponseEntity<Page<FriendshipDto>> findFollowingUsers(FriendshipQueryRequest request, @ApiIgnore AuthUserDto authUser) {

        Long whomUserId = Optional.ofNullable(request.getUserId())
                .orElseGet(authUser::getId);
        return ResponseEntity.ok(friendshipService.findFollowing(whomUserId, request.getOffset(), request.getLimit()));
    }

    /**
     * 获取全部已关注用户ID列表
     *
     * @param id       指定用户视角
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "获取全部已关注用户ID列表")
    @ApiImplicitParam(name = "id", value = "指定用户视角", paramType = "form", dataTypeClass = Long.class)
    @GetMapping("/ids/all")
    public ResponseEntity<Collection<Long>> findAllFollowingIds(@RequestParam("userId") Long id, @ApiIgnore AuthUserDto authUser) {

        Long whomUserId = Optional.ofNullable(id)
                .orElseGet(authUser::getId);
        return ResponseEntity.ok(friendshipService.findAllFollowingIds(whomUserId, null));
    }

    /**
     * 获取全部已关注用户列表
     *
     * @param id       指定用户视角
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "获取全部已关注用户列表")
    @ApiImplicitParam(name = "id", value = "指定用户视角", paramType = "form", dataTypeClass = Long.class)
    @GetMapping("/all")
    public ResponseEntity<Collection<FriendshipDto>> findAllFollowingUsers(@RequestParam("userId") Long id, @ApiIgnore AuthUserDto authUser) {

        Long whomUserId = Optional.ofNullable(id)
                .orElseGet(authUser::getId);
        return ResponseEntity.ok(friendshipService.findAllFollowing(whomUserId, null));
    }
}
