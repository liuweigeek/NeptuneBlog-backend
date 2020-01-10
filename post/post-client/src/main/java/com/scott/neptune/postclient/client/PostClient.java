package com.scott.neptune.postclient.client;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.postclient.dto.PostDto;
import com.scott.neptune.postclient.hystric.PostClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Post服务的远程调用接口
 *
 * @author scott
 */
@FeignClient(name = "post-server", fallbackFactory = PostClientFallbackFactory.class)
public interface PostClient {

    /**
     * 通过关键字搜索推文
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @RequestMapping(value = "/postServer/findByKeyword/{keyword}", method = RequestMethod.GET)
    ServerResponse<List<PostDto>> findByKeyword(@PathVariable String keyword);

}
