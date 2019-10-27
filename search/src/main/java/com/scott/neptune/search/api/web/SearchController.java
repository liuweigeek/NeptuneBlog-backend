package com.scott.neptune.search.api.web;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.postapi.dto.PostDto;
import com.scott.neptune.search.remote.client.PostClient;
import com.scott.neptune.search.remote.client.UserClient;
import com.scott.neptune.userapi.dto.UserDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/21 22:44
 * @Description: NeptuneBlog
 */
@Slf4j
@Api(tags = "搜索接口 - 面向前端")
@RestController
@RefreshScope
@RequestMapping("/search")
public class SearchController extends BaseController {

    @Resource
    private UserClient userClient;

    @Resource
    private PostClient postClient;

    @ApiOperation(value = "搜索用户和推文")
    @ApiImplicitParam(name = "keyword", value = "关键字", required = true, dataType = "string", paramType = "path")
    @GetMapping(value = "/{keyword}")
    public ServerResponse<Map<String, ServerResponse>> searchByKeyword(@PathVariable("keyword") String keyword) {

        ServerResponse<List<UserDto>> userResponse = userClient.findByKeyword(keyword);
        ServerResponse<List<PostDto>> postResponse = postClient.findByKeyword(keyword);

        Map<String, ServerResponse> searchResultMap = new HashMap<String, ServerResponse>() {
            {
                put("userRes", userResponse);
                put("postRes", postResponse);
            }
        };
        return ServerResponse.createBySuccess(searchResultMap);
    }
}
