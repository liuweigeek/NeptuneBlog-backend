package com.scott.neptune.socialserver.api.web;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.postclient.client.PostClient;
import com.scott.neptune.postclient.dto.PostDto;
import com.scott.neptune.socialserver.entity.LikeEntity;
import com.scott.neptune.socialserver.service.ILikeService;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 推文接口
 *
 * @author scott
 */
@Slf4j
@Api(tags = "点赞接口 - 面向前端")
@RefreshScope
@RestController
@RequestMapping("/like")
public class LikeController extends BaseController {

    @Resource
    private UserClient userClient;
    @Resource
    private PostClient postClient;
    @Resource
    private ILikeService likeService;

    /**
     * 点赞
     *
     * @param postId 指定推文Id
     * @return 点赞结果
     */
    @ApiOperation(value = "点赞")
    @ApiImplicitParam(name = "postId", value = "要点赞的推文ID", required = true, paramType = "body", dataType = "string")
    @PostMapping(value = "/like")
    public ServerResponse like(@RequestBody String postId) {

        ServerResponse<UserDto> loginUserResponse = userClient.getLoginUser();
        if (!loginUserResponse.isSuccess()) {
            return loginUserResponse;
        }
        UserDto loginUser = loginUserResponse.getData();

        // TODO find post by postClient
        PostDto postDto = new PostDto();
        if (Objects.isNull(postDto)) {
            return ServerResponse.createByErrorMessage("推文不存在");
        }

        LikeEntity likeEntity = LikeEntity.builder()
                .userId(loginUser.getId())
                .postId(postId)
                .build();

        if (likeService.save(likeEntity).isSuccess()) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("点赞失败，请稍后再试");
        }
    }

    /**
     * 取消点赞
     *
     * @param postId 指定推文Id
     * @return 取消点赞结果
     */
    @ApiOperation(value = "取消点赞")
    @ApiImplicitParam(name = "postId", value = "要取消点赞的推文ID", required = true, paramType = "path", dataType = "string")
    @DeleteMapping(value = "/cancelLike/{postId}")
    public ServerResponse cancelLike(@PathVariable("postId") String postId) {

        ServerResponse<UserDto> loginUserResponse = userClient.getLoginUser();
        if (!loginUserResponse.isSuccess()) {
            return loginUserResponse;
        }
        UserDto loginUser = loginUserResponse.getData();

        if (likeService.deleteByUserIdAndPostId(loginUser.getId(), postId)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("取消点赞失败，请稍后重试");
        }
    }

}
