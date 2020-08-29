package com.scott.neptune.authenticationclient.hystric;

import com.scott.neptune.authenticationclient.client.AuthClient;
import com.scott.neptune.common.exception.NeptuneBlogException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author scott
 */
@Slf4j
@Component
public class AuthClientFallbackFactory implements FallbackFactory<AuthClient> {

    @Override
    public AuthClient create(Throwable throwable) {

        return username -> {
            log.error("feign [loadUserByUsername] Exception: ", throwable);
            throw new NeptuneBlogException("feign [getUserById] Exception: ", throwable);
        };
    }
}
