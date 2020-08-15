package com.scott.neptune.userserver.api.web;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.userclient.command.FriendshipRequest;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userserver.component.UserComponent;
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
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/9/24 17:33
 * @Description: 好友关系接口
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "好友关系接口 - 面向前端")
@RestController
@RequestMapping("/friends")
public class FriendController extends BaseController {

    private final IFriendshipService friendshipService;
    private final UserComponent userComponent;

    /**
     * 获取关注ID列表
     *
     * @return 关注ID列表
     */
    @ApiOperation(value = "获取关注列表")
    @GetMapping("/ids")
    public ResponseEntity<Page<FriendshipDto>> ids(FriendshipRequest request) {

        Long whomUserId = Optional.ofNullable(request.getUserId())
                .orElseGet(userComponent.getUserFromRequest(httpServletRequest)::getId);
        //TODO only query ids
        return ResponseEntity.ok(friendshipService.findFriends(whomUserId, request.getCursor(), request.getCount()));
    }

    /**
     * 获取关注列表
     *
     * @return 关注列表
     */
    @ApiOperation(value = "获取关注列表")
    @GetMapping("/list")
    public ResponseEntity<Page<FriendshipDto>> list(FriendshipRequest request) {

        Long whomUserId = Optional.ofNullable(request.getUserId())
                .orElseGet(userComponent.getUserFromRequest(httpServletRequest)::getId);
        //TODO only query ids
        return ResponseEntity.ok(friendshipService.findFriends(whomUserId, request.getCursor(), request.getCount()));
    }
}
