package com.scott.neptune.user.controller;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.component.UserComponent;
import com.scott.neptune.user.entity.FriendRelation;
import com.scott.neptune.user.entity.User;
import com.scott.neptune.user.service.IFriendRelationService;
import com.scott.neptune.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 好友关系接口
 */
@Slf4j
@RestController
@RequestMapping("/friend")
public class FriendController extends BaseController {

    @Resource
    private UserComponent userComponent;
    @Resource
    private IUserService userService;
    @Resource
    private IFriendRelationService friendRelationService;

    /**
     * 关注指定用户
     *
     * @param userId 指定用户Id
     * @return 关注结果
     */
    @PostMapping(value = "/follow")
    public ServerResponse follow(String userId) {

        UserDto loginUser = userComponent.getUserFromRequest(request);

        User targetUser = userService.getUserById(userId);
        if (targetUser == null) {
            return ServerResponse.createByErrorMessage(messageSource.getMessage("error.userNotFound", null, userComponent.getUserLocale(loginUser.getId())));
        }

        //TODO get follow from device
        FriendRelation friendRelation = FriendRelation.builder()
                .authorId(loginUser.getId())
                .targetId(targetUser.getId())
                .followFrom("web")
                .build();

        if (friendRelationService.save(friendRelation).isSuccess()) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage(messageSource.getMessage("error.followFailed", null, userComponent.getUserLocale(loginUser.getId())));
        }
    }

    /**
     * 取消关注指定用户
     *
     * @param userId 指定用户
     * @return 取消关注结果
     */
    @DeleteMapping(value = "/cancelFollow/{userId}")
    public ServerResponse cancelFollow(@PathVariable("userId") String userId) {

        UserDto loginUser = userComponent.getUserFromRequest(request);
        User targetUser = userService.getUserById(userId);
        if (targetUser == null) {
            return ServerResponse.createByErrorMessage(messageSource.getMessage("error.userNotFound", null, userComponent.getUserLocale(loginUser.getId())));
        }
        FriendRelation friendRelation = friendRelationService.getRelation(loginUser.getId(), targetUser.getId());
        if (friendRelationService.delete(friendRelation)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage(messageSource.getMessage("error.cancelFollowFailed", null, userComponent.getUserLocale(loginUser.getId())));
        }
    }

    /**
     * 获取关注列表
     *
     * @return 关注列表
     */
    @GetMapping(value = "/findAllFollowing")
    public ServerResponse findAllFollowing() {

        UserDto loginUser = userComponent.getUserFromRequest(request);
        return ServerResponse.createBySuccess(friendRelationService.findAllFollowing(loginUser.getId()));
    }

    /**
     * 获取粉丝列表
     *
     * @return 粉丝列表
     */
    @GetMapping(value = "/findAllFollower")
    public ServerResponse findAllFollower() {

        UserDto loginUser = userComponent.getUserFromRequest(request);
        return ServerResponse.createBySuccess(friendRelationService.findAllFollower(loginUser.getId()));
    }
}
