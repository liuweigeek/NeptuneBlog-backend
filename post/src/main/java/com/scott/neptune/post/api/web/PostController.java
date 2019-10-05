package com.scott.neptune.post.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scott.neptune.common.dto.Pageable;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.remote.client.UserServer;
import com.scott.neptune.post.service.IPostService;
import com.scott.neptune.postapi.entity.PostEntity;
import com.scott.neptune.userapi.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 推文接口
 *
 * @author scott
 */
@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private UserServer userServer;
    @Resource
    private IPostService postService;

    @PostMapping(value = "/sendPost")
    public ServerResponse sendPost(@Valid @RequestBody PostEntity postEntity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMsgList = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(toList());

            return ServerResponse.createByErrorMessage(errorMsgList.toString());
        }

        ServerResponse<UserDto> loginUserResponse = userServer.getLoginUser();
        if (!loginUserResponse.isSuccess()) {
            return loginUserResponse;
        }

        UserDto loginUser = loginUserResponse.getData();
        return postService.save(postEntity, loginUser);
    }

    @PostMapping(value = "/getFollowingPosts")
    public ServerResponse<IPage<PostEntity>> getFollowingPosts(Pageable pageable, PostEntity postEntity) {

        ServerResponse<UserDto> loginUserResponse = userServer.getLoginUser();
        if (!loginUserResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage(loginUserResponse.getMsg());
        }

        ServerResponse<List<String>> followingUserResponse = userServer.getFollowingUserIds();
        if (!followingUserResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage(followingUserResponse.getMsg());
        }
        List<String> followingUserIds = followingUserResponse.getData();
        if (CollectionUtils.isEmpty(followingUserIds)) {
            return ServerResponse.createByErrorMessage("未关注任何用户");
        }

        return ServerResponse.createBySuccess(postService.findByUserIdList(followingUserIds, pageable.getPageNumber(), pageable.getPageSize()));
    }
}
