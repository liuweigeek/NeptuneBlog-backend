package com.scott.neptune.tweetclient.hystric;

import com.scott.neptune.tweetclient.client.TweetClient;
import com.scott.neptune.tweetclient.dto.TweetDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author scott
 */
@Slf4j
@EnableHystrix
@Component
public class TweetClientFallbackFactory implements FallbackFactory<TweetClient> {

    @Override
    public TweetClient create(Throwable throwable) {
        return new TweetClient() {

            /**
             * 通过关键字搜索推文
             *
             * @param keyword 关键字
             * @return 用户列表
             */
            @Override
            public List<TweetDto> search(String keyword) {
                log.error("feign [search] Exception: ", throwable);
                return Collections.emptyList();
            }
        };
    }
}
