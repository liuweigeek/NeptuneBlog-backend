package com.scott.neptune.user.api.web;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.component.UserComponent;
import com.scott.neptune.user.entity.FriendRelationEntity;
import com.scott.neptune.user.entity.UserEntity;
import com.scott.neptune.user.mapping.FriendRelationModelMapping;
import com.scott.neptune.user.service.IFriendRelationService;
import com.scott.neptune.user.service.IUserService;
import com.scott.neptune.userapi.dto.FriendRelationDto;
import com.scott.neptune.userapi.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/24 17:33
 * @Description: 好友关系接口
 */
@Api(tags = "好友关系接口 - 面向前端")
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/friend")
public class FriendController extends BaseController {

    @Resource
    private UserComponent userComponent;
    @Resource
    private IUserService userService;
    @Resource
    private IFriendRelationService friendRelationService;
    @Resource
    private FriendRelationModelMapping friendRelationModelMapping;

    /**
     * 关注指定用户
     *
     * @param userId 指定用户Id
     * @return 关注结果
     */
    @ApiOperation(value = "关注指定用户")
    @ApiImplicitParam(name = "userId", value = "要关注的用户ID", required = true, paramType = "form", dataType = "string")
    @PostMapping(value = "/follow")
    public ServerResponse follow(@RequestBody String userId) {

        UserDto loginUser = userComponent.getUserFromRequest(request);

        UserEntity targetUserEntity = userService.getUserById(userId);
        if (Objects.isNull(targetUserEntity)) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        //TODO get follow from device
        FriendRelationEntity friendRelationEntity = FriendRelationEntity.builder()
                .fromId(loginUser.getId())
                .toId(targetUserEntity.getId())
                .followFrom("web")
                .build();

        if (friendRelationService.save(friendRelationEntity).isSuccess()) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("关注失败，请稍后再试");
        }
    }

    /**
     * 取消关注指定用户
     *
     * @param userId 指定用户
     * @return 取消关注结果
     */
    @ApiOperation(value = "取消关注指定用户")
    @ApiImplicitParam(name = "userId", value = "要取消关注的用户ID", required = true, paramType = "form", dataType = "string")
    @DeleteMapping(value = "/cancelFollow/{userId}")
    public ServerResponse cancelFollow(@PathVariable("userId") String userId) {

        UserDto loginUser = userComponent.getUserFromRequest(request);

        if (friendRelationService.deleteByFromIdAndToId(loginUser.getId(), userId)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("取消关注失败，请稍后重试");
        }
    }

    /**
     * 获取关注列表
     *
     * @return 关注列表
     */
    @ApiOperation(value = "查看正在关注的用户")
    @GetMapping(value = "/findFollowing")
    public ServerResponse findAllFollowing(FriendRelationDto friendRelationDto) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        return ServerResponse.createBySuccess(friendRelationService.findFollowing(loginUser.getId(),
                friendRelationDto.getCurrent(), friendRelationDto.getSize()));
    }

    /**
     * 获取粉丝列表
     *
     * @return 粉丝列表
     */
    @ApiOperation(value = "查看关注我的人")
    @GetMapping(value = "/findFollower")
    public ServerResponse findAllFollower(FriendRelationDto friendRelationDto) {
        UserDto loginUser = userComponent.getUserFromRequest(request);
        return ServerResponse.createBySuccess(friendRelationService.findFollower(loginUser.getId(),
                friendRelationDto.getCurrent(), friendRelationDto.getSize()));
    }
}
