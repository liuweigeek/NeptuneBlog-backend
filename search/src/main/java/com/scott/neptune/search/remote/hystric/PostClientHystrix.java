package com.scott.neptune.search.remote.hystric;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.postapi.dto.PostDto;
import com.scott.neptune.search.remote.client.PostClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author scott
 */
@Component
public class PostClientHystrix implements PostClient {

    /**
     * 通过关键字搜索推文
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @Override
    public ServerResponse<List<PostDto>> findByKeyword(String keyword) {
        return ServerResponse.createByErrorMessage("搜索推文异常，请稍后重试");
    }
}
