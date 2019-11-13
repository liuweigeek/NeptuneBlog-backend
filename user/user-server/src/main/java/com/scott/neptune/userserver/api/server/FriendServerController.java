package com.scott.neptune.userserver.api.server;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.mapping.BaseModelMapping;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.userapi.dto.UserDto;
import com.scott.neptune.userserver.component.UserComponent;
import com.scott.neptune.userserver.entity.FriendRelationEntity;
import com.scott.neptune.userserver.service.IFriendRelationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping("/friendServer")
public class FriendServerController extends BaseController {

    @Resource
    private UserComponent userComponent;
    @Resource
    private BaseModelMapping userModelMapping;
    @Resource
    private IFriendRelationService friendRelationService;

    /**
     * 获取全部已关注用户
     *
     * @return 用户对象
     */
    @GetMapping(value = "/getFollowingUsers")
    public ServerResponse getFollowingUsers(FriendRelationEntity friendRelationEntity) {

        UserDto currentUser = userComponent.getUserFromRequest(request);
        List<UserDto> followingUsers = friendRelationService.findAllFollowing(currentUser.getId());

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
                .map(UserDto::getId)
                .collect(toList());

        return ServerResponse.createBySuccess(followingUserIds);
    }

}
