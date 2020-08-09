package com.scott.neptune.tweetserver.api.server;

import com.scott.neptune.common.base.BaseController;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetserver.service.ITweetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("server/tweets")
public class TweetServerController extends BaseController {

    @Resource
    private ITweetService tweetService;

    /**
     * 通过关键字搜索推文
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @ApiOperation(value = "通过关键字搜索用户")
    @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "path", required = true)
    @GetMapping(value = "/search/{keyword}")
    public ResponseEntity<List<TweetDto>> search(@PathVariable String keyword) {
        List<TweetDto> tweetDtoList = tweetService.findByKeyword(keyword);
        return ResponseEntity.ok(tweetDtoList);
    }
}
