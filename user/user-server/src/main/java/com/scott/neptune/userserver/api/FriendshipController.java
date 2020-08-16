package com.scott.neptune.userserver.api;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.FriendshipDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.service.IFriendshipService;
import com.scott.neptune.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //TODO
    public ResponseEntity<FriendshipDto> lookUp() {
        return ResponseEntity.ok(new FriendshipDto());
    }

    public ResponseEntity<FriendshipDto> show() {
        return ResponseEntity.ok(new FriendshipDto());
    }

    /**
     * 关注指定用户
     *
     * @param userId   指定用户ID
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "关注指定用户")
    @ApiImplicitParam(value = "要关注的用户ID", paramType = "body", required = true)
    @PostMapping("/create")
    public ResponseEntity<FriendshipDto> create(Long userId, AuthUserDto authUser) {

        UserDto targetUserDto = userService.findUserById(userId, authUser.getId());
        if (targetUserDto == null) {
            throw new RestException("用户不存在", HttpStatus.NOT_FOUND);
        }

        FriendshipDto friendshipDto = FriendshipDto.builder()
                .sourceId(authUser.getId())
                .targetId(targetUserDto.getId())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(friendshipService.save(friendshipDto));
    }

    /**
     * 取消关注指定用户
     *
     * @param userId   指定用户ID
     * @param authUser 已登录用户
     * @return
     */
    @ApiOperation(value = "取消关注指定用户")
    @ApiImplicitParam(name = "userId", value = "要取消关注的用户ID", paramType = "path", required = true)
    @DeleteMapping("/destroy")
    public ResponseEntity<Void> destroy(Long userId, AuthUserDto authUser) {
        friendshipService.delete(authUser.getId(), userId);
        return ResponseEntity.noContent().build();
    }
}
