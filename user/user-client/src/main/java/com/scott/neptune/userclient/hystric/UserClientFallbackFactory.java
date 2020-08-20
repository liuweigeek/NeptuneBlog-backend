package com.scott.neptune.userclient.hystric;

import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.command.UserSearchRequest;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/**
 * @author scott
 */
@Slf4j
@EnableHystrix
@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {

        return new UserClient() {
            @Override
            public UserDto addUser(UserDto userDto) {
                log.error("feign [addUser] Exception: ", throwable);
                return null;
            }

            @Override
            public UserDto show(Long id, String screenName) {
                log.error("feign [show] Exception: ", throwable);
                return null;
            }

            @Override
            public Collection<UserDto> lookup(String userIds, String screenNames) {
                log.error("feign [lookup] Exception: ", throwable);
                return null;
            }

            @Override
            public Collection<UserDto> search(UserSearchRequest request) {
                log.error("feign [search] Exception: ", throwable);
                return Collections.emptyList();
            }

            @Override
            public AuthUserDto getUserByScreenNameForAuthenticate(String screenName) {
                log.error("feign [getUserByScreenNameForAuthenticate] Exception: ", throwable);
                return null;
            }
        };
    }
}
