package com.scott.neptune.search.remote.client;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.postapi.dto.PostDto;
import com.scott.neptune.search.remote.hystric.PostClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Post服务的远程调用接口
 *
 * @author scott
 */
@FeignClient(name = "post", fallback = PostClientHystrix.class)
public interface PostClient {

    /**
     * 通过关键字搜索推文
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @GetMapping(value = "/postServer/findByKeyword/{keyword}")
    public ServerResponse<List<PostDto>> findByKeyword(@PathVariable String keyword);

}
