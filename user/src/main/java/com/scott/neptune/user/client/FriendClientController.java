package com.scott.neptune.user.client;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.component.UserComponent;
import com.scott.neptune.user.entity.User;
import com.scott.neptune.user.service.IFriendRelationService;
import com.scott.neptune.user.service.IUserService;
import com.scott.neptune.user.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 为其他服务提供的用户接口
 */
@Slf4j
@RestController
@RequestMapping("/friendClient")
public class FriendClientController extends BaseController {

    @Resource
    private UserComponent userComponent;
    @Resource
    private IUserService userService;
    @Resource
    private IFriendRelationService friendRelationService;

    /**
     * 获取全部已关注用户
     *
     * @return 用户对象
     */
    @GetMapping(value = "/getFollowingUsers")
    public ServerResponse getFollowingUsers() {

        UserDto currentUser = userComponent.getUserFromRequest(request);
        List<UserDto> followingUsers = friendRelationService.findAllFollowing(currentUser.getId()).stream()
                .map(UserUtil::convertToDto)
                .collect(toList());

        return ServerResponse.createBySuccess(followingUsers);
    }

    /**
     * 获取全部已关注用户Id
     *
     * @return 用户对象
     */
    @GetMapping(value = "/getFollowingUserIds")
    public ServerResponse getFollowingUserIds() {

        UserDto currentUser = userComponent.getUserFromRequest(request);
        List<String> followingUserIds = friendRelationService.findAllFollowing(currentUser.getId()).stream()
                .map(User::getId)
                .collect(toList());

        return ServerResponse.createBySuccess(followingUserIds);
    }

}
