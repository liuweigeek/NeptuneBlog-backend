package com.scott.neptune.userserver.api.web;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.component.UserComponent;
import com.scott.neptune.userserver.convertor.FriendshipConvertor;
import com.scott.neptune.userserver.service.IFriendshipService;
import com.scott.neptune.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("friendship")
public class FollowersController extends BaseController {

    @Resource
    private UserComponent userComponent;
    @Resource
    private IUserService userService;
    @Resource
    private IFriendshipService friendshipService;
    @Resource
    private FriendshipConvertor friendshipConvertor;

    /**
     * 关注指定用户
     *
     * @param userId 指定用户Id
     * @return 关注结果
     */
    @ApiOperation(value = "关注指定用户")
    @ApiImplicitParam(value = "要关注的用户ID", paramType = "body", required = true)
    @PostMapping(path = "follow")
    public ResponseEntity<FriendshipDto> follow(@RequestParam Long userId) {

        UserDto loginUser = userComponent.getUserFromRequest(request);

        UserDto targetUserDto = userService.findUserById(userId, loginUser.getId());
        if (targetUserDto == null) {
            throw new RestException("用户不存在", HttpStatus.NOT_FOUND);
        }

        //TODO get follow from device
        FriendshipDto friendshipDto = FriendshipDto.builder()
                .sourceId(loginUser.getId())
                .targetId(targetUserDto.getId())
                .followFrom("web")
                .build();
        return ResponseEntity.ok(friendshipService.save(friendshipDto));
    }

    /**
     * 取消关注指定用户
     *
     * @param userId 指定用户
     * @return 取消关注结果
     */
    @ApiOperation(value = "取消关注指定用户")
    @ApiImplicitParam(name = "userId", value = "要取消关注的用户ID", paramType = "path", required = true)
    @DeleteMapping(path = "cancelFollow/{userId}")
    public ResponseEntity<Void> cancelFollow(@PathVariable("userId") Long userId) {

        UserDto loginUser = userComponent.getUserFromRequest(request);
        friendshipService.deleteBySourceIdAndTargetId(loginUser.getId(), userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取关注列表
     *
     * @return 关注列表
     */
    @ApiOperation(value = "获取关注列表")
    @GetMapping(value = "findFollowing")
    public ResponseEntity<Page<FriendshipDto>> findAllFollowing(UserDto userDto) {

        Long fromUserId;
        if (Objects.isNull(userDto) || StringUtils.isBlank(userDto.getUsername())) {
            UserDto loginUser = userComponent.getUserFromRequest(request);
            fromUserId = loginUser.getId();
        } else {
            UserDto fromUser = userService.findUserByUsername(userDto.getUsername(), null);
            if (Objects.isNull(fromUser)) {
                throw new RestException("指定用户不存在", HttpStatus.NOT_FOUND);
            }
            fromUserId = fromUser.getId();
        }
        //TODO parameters for pageable
        return ResponseEntity.ok(friendshipService.findFriends(fromUserId, 0, 0));
    }

    /**
     * 获取关注者列表
     *
     * @return 关注者列表
     */
    @ApiOperation(value = "获取关注者列表")
    @GetMapping(value = "findFollower")
    public ResponseEntity<Page<FriendshipDto>> findAllFollower(UserDto userDto) {
        Long toUserId;
        if (Objects.isNull(userDto) || StringUtils.isBlank(userDto.getUsername())) {
            UserDto loginUser = userComponent.getUserFromRequest(request);
            toUserId = loginUser.getId();
        } else {
            UserDto toUser = userService.findUserByUsername(userDto.getUsername(), null);
            if (Objects.isNull(toUser)) {
                throw new RestException("指定用户不存在", HttpStatus.NOT_FOUND);
            }
            toUserId = toUser.getId();
        }
        return ResponseEntity.ok(friendshipService.findFollowers(toUserId, 0, 0));
    }
}
