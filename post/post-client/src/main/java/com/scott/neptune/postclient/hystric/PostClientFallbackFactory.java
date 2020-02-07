package com.scott.neptune.postclient.hystric;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.postclient.dto.PostDto;
import com.scott.neptune.postclient.client.PostClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author scott
 */
@Slf4j
@Component
public class PostClientFallbackFactory implements FallbackFactory<PostClient> {

    @Override
    public PostClient create(Throwable throwable) {
        return new PostClient() {

            /**
             * 通过关键字搜索推文
             *
             * @param keyword 关键字
             * @return 用户列表
             */
            @Override
            public ServerResponse<List<PostDto>> search(String keyword) {
                log.error("feign fallback Exception: ", throwable);
                return ServerResponse.createByErrorMessage("搜索推文异常，请稍后重试");
            }
        };
    }
}
