package com.scott.neptune.authenticationclient.client;

import com.scott.neptune.common.config.FeignConfig;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.hystric.UserClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User服务的远程调用接口
 *
 * @author scott
 */
@FeignClient(name = "authentication-server", configuration = FeignConfig.class, fallbackFactory = UserClientFallbackFactory.class)
public interface AuthClient {

    @RequestMapping(path = "/username/{username}", method = RequestMethod.GET)
    AuthUserDto loadUserByUsername(@PathVariable("username") String username);
}