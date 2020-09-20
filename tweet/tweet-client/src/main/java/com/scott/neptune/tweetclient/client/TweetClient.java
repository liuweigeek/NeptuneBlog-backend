package com.scott.neptune.tweetclient.client;

import com.scott.neptune.common.config.FeignConfig;
import com.scott.neptune.tweetclient.command.TweetSearchRequest;
import com.scott.neptune.tweetclient.dto.TweetDto;
import com.scott.neptune.tweetclient.hystric.TweetClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * Tweet服务的远程调用接口
 *
 * @author scott
 */
@FeignClient(name = "tweet-server", configuration = FeignConfig.class, fallbackFactory = TweetClientFallbackFactory.class)
public interface TweetClient {

    /**
     * 通过关键字搜索推文
     *
     * @param request 关键字
     * @return Tweet列表
     */
    @RequestMapping(path = "/tweets/search", method = RequestMethod.GET)
    Collection<TweetDto> search(TweetSearchRequest request);

}
