package com.scott.neptune.userserver.api;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.userclient.command.FriendshipRequest;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userserver.service.IFriendshipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/friends")
public class FriendController extends BaseController {

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
    public ResponseEntity<Page<FriendshipDto>> ids(FriendshipRequest request, AuthUserDto authUser) {

        Long whomUserId = Optional.ofNullable(request.getUserId())
                .orElseGet(authUser::getId);
        //TODO only query ids
        return ResponseEntity.ok(friendshipService.findFriends(whomUserId, request.getCursor(), request.getCount()));
    }

    /**
     * 获取已关注用户列表
     *
     * @param request  指定用户视角
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "获取已关注用户列表")
    @GetMapping("/list")
    public ResponseEntity<Page<FriendshipDto>> list(FriendshipRequest request, AuthUserDto authUser) {

        Long whomUserId = Optional.ofNullable(request.getUserId())
                .orElseGet(authUser::getId);
        //TODO only query ids
        return ResponseEntity.ok(friendshipService.findFriends(whomUserId, request.getCursor(), request.getCount()));
    }
}
