package com.scott.neptune.post.api.server;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.entity.PostEntity;
import com.scott.neptune.post.mapping.PostModelMapping;
import com.scott.neptune.post.service.IPostService;
import com.scott.neptune.postapi.dto.PostDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 14:07
 * @Description: 为其他服务提供的推文接口
 */
@Api(tags = "推文接口 - 面向其他服务")
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/postServer")
public class PostServerController extends BaseController {

    @Resource
    private IPostService postService;
    @Resource
    private PostModelMapping postModelMapping;

    /**
     * 通过关键字搜索推文
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @ApiOperation(value = "通过关键字搜索用户")
    @ApiImplicitParam(name = "keyword", value = "关键字", required = true, paramType = "path", dataType = "String")
    @GetMapping(value = "/findByKeyword/{keyword}")
    public ServerResponse<List<PostDto>> findByKeyword(@PathVariable String keyword) {
        List<PostEntity> postEntityList = postService.findByKeyword(keyword);
        List<PostDto> postDtoList = postModelMapping.convertToDtoList(postEntityList);
        return ServerResponse.createBySuccess(postDtoList);
    }
}
