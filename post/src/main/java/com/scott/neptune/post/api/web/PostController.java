package com.scott.neptune.post.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.remote.server.UserServer;
import com.scott.neptune.post.service.IPostService;
import com.scott.neptune.postapi.dto.PostDto;
import com.scott.neptune.postapi.entity.PostEntity;
import com.scott.neptune.postapi.mapping.PostModelMapping;
import com.scott.neptune.userapi.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
    @Resource
    private PostModelMapping postModelMapping;

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

    @GetMapping(value = "/getFollowingPosts")
    public ServerResponse<IPage<PostDto>> getFollowingPosts(PostEntity postEntity) {

        if (Objects.isNull(postEntity)) {
            postEntity = new PostEntity();
        }

        ServerResponse<UserDto> loginUserResponse = userServer.getLoginUser();
        if (!loginUserResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage(loginUserResponse.getMsg());
        }

        IPage<PostDto> postList = postService.findByFollowerId(loginUserResponse.getData().getId(), postEntity.getCurrent(), postEntity.getSize())
                .convert(postModelMapping::convertToDto);
        return ServerResponse.createBySuccess(postList);
    }
}
