package com.scott.neptune.postserver.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.postclient.dto.PostDto;
import com.scott.neptune.postserver.entity.PostEntity;
import com.scott.neptune.postserver.mapping.PostModelMapping;
import com.scott.neptune.postserver.service.IPostService;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userclient.UserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
@Api(tags = "推文接口 - 面向前端")
@RefreshScope
@RestController
@RequestMapping("/post")
public class PostController extends BaseController {

    @Resource
    private UserClient userClient;
    @Resource
    private IPostService postService;
    @Resource
    private PostModelMapping postModelMapping;

    /**
     * 发送推文
     *
     * @param postDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "发送推文")
    @PostMapping(value = "/sendPost")
    public ServerResponse sendPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult) {

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
        PostEntity postEntity = postModelMapping.convertToEntity(postDto);
        ServerResponse<PostEntity> sendResponse = postService.save(postEntity, loginUser);
        if (sendResponse.isSuccess()) {
            sendResponse.getData().setAuthor(loginUser);
            return ServerResponse.createBySuccess(postModelMapping.convertToDto(sendResponse.getData()));
        } else {
            return sendResponse;
        }
    }

    /**
     * 获取关注用户的推文
     *
     * @param postDto
     * @return
     */
    @ApiOperation(value = "获取关注用户的推文")
    @GetMapping(value = "/getFollowingPosts")
    public ServerResponse<IPage<PostDto>> getFollowingPosts(PostDto postDto) {

        if (Objects.isNull(postDto)) {
            postDto = new PostDto();
        }

        ServerResponse<UserDto> loginUserResponse = userClient.getLoginUser();
        if (!loginUserResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage(loginUserResponse.getMsg());
        }

        IPage<PostDto> postList = postService.findByFollowerId(loginUserResponse.getData().getId(), postDto.getCurrent(), postDto.getSize())
                .convert(postModelMapping::convertToDto);
        return ServerResponse.createBySuccess(postList);
    }

    /**
     * 获取指定用户的推文
     *
     * @param postDto
     * @return
     */
    @ApiOperation(value = "获取指定用户的推文")
    @Cacheable(value = Constant.CacheKey.POST, key = "#postDto.getAuthorId()+'.'+#postDto.getCurrent()+'.'+#postDto.getSize()")
    @GetMapping(value = "/getPostsByUserId")
    public ServerResponse<IPage<PostDto>> getPostsByUserId(PostDto postDto) {

        if (Objects.isNull(postDto) || StringUtils.isBlank(postDto.getAuthorId())) {
            return ServerResponse.createByErrorMessage("请指定用户");
        }

        IPage<PostDto> postList = postService.findByUserId(postDto.getAuthorId(), postDto.getCurrent(), postDto.getSize())
                .convert(postModelMapping::convertToDto);
        return ServerResponse.createBySuccess(postList);
    }

    /**
     * 获取指定用户的推文
     *
     * @param userDto
     * @return
     */
    @ApiOperation(value = "获取指定用户的推文")
    @Cacheable(value = Constant.CacheKey.POST, key = "#userDto.getUsername()+'.'+#userDto.getCurrent()+'.'+#userDto.getSize()")
    @GetMapping(value = "/getPostsByUsername")
    public ServerResponse<IPage<PostDto>> getPostsByUsername(UserDto userDto) {

        if (Objects.isNull(userDto) || StringUtils.isBlank(userDto.getUsername())) {
            return ServerResponse.createByErrorMessage("请指定用户");
        }

        IPage<PostDto> postList = postService.findByUsername(userDto.getUsername(), userDto.getCurrent(), userDto.getSize())
                .convert(postModelMapping::convertToDto);
        return ServerResponse.createBySuccess(postList);
    }

}
