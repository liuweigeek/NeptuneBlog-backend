package com.scott.neptune.post.controller;

import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.entity.Post;
import com.scott.neptune.post.feignclient.UserClient;
import com.scott.neptune.post.service.IPostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

/**
 * 推文接口
 */
@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private UserClient userClient;
    @Resource
    private IPostService postService;
    @Resource
    private MessageSource messageSource;

    @PostMapping(value = "/sendPost")
    public ServerResponse sendPost(@Valid @RequestBody Post post, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMsgList = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(toList());

            return ServerResponse.createByErrorMessage(errorMsgList.toString());
        }

        ServerResponse<UserDto> loginUserResponse = userClient.getLoginUser();
        if (!loginUserResponse.isSuccess()) {
            return loginUserResponse;
        }

        UserDto loginUser = loginUserResponse.getData();
        post.setUserId(loginUser.getId());
        post.setCreateDate(new Date());
        post.setUpdateDate(post.getCreateDate());

        return postService.save(post);
    }

    @PostMapping(value = "/getFollowingPosts")
    public ServerResponse<Page<Post>> getFollowingPosts(@RequestBody Post post) {

        ServerResponse<List<String>> followingUserResponse = userClient.getFollowingUserIds();
        if (!followingUserResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage(followingUserResponse.getMsg());
        }
        List<String> followingUserIds = followingUserResponse.getData();
        if (CollectionUtils.isEmpty(followingUserIds)) {
            return ServerResponse.createByErrorMessage(messageSource.getMessage("msg.noFollowingUser", null, Locale.getDefault()));
        }

        return ServerResponse.createBySuccess(postService.findByUserIdList(followingUserIds, post.getPageNumber(), post.getPageSize()));
    }
}
