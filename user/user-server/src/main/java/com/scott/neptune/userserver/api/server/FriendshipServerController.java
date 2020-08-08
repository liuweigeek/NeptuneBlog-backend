package com.scott.neptune.userserver.api.server;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.component.UserComponent;
import com.scott.neptune.userserver.service.IFriendshipService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:12
 * @Description: 为其他服务提供的用户接口
 */
@Api(tags = "好友关系接口 - 面向其他服务")
@Slf4j
@RefreshScope
@RestController
@RequestMapping(path = "server/friendship")
public class FriendshipServerController extends BaseController {

    private final UserComponent userComponent;
    private final IFriendshipService friendshipService;

    public FriendshipServerController(UserComponent userComponent, IFriendshipService friendshipService) {
        this.userComponent = userComponent;
        this.friendshipService = friendshipService;
    }

    /**
     * 获取全部已关注用户
     *
     * @return 用户对象
     */
    @GetMapping(path = "getFollowingUsers")
    public ResponseEntity<List<FriendshipDto>> getFollowingUsers(FriendshipDto friendshipDto) {

        UserDto currentUser = userComponent.getUserFromRequest(request);
        List<FriendshipDto> followingUsers = friendshipService.findAllFriends(currentUser.getId());
        return ResponseEntity.ok(followingUsers);
    }

    /**
     * 获取全部已关注用户Id
     *
     * @return 用户对象
     */
    @GetMapping(path = "getFollowingUserIds")
    public ResponseEntity<List<Long>> getFollowingUserIds() {

        UserDto currentUser = userComponent.getUserFromRequest(request);
        List<Long> followingUserIds = friendshipService.findAllFriends(currentUser.getId()).stream()
                .map(FriendshipDto::getTargetId)
                .collect(toList());
        return ResponseEntity.ok(followingUserIds);
    }

}
