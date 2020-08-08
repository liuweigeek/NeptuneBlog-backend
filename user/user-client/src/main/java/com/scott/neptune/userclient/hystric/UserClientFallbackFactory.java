package com.scott.neptune.userclient.hystric;

import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
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
            public UserDto findUserById(Long id) {
                log.error("feign [findUserById] Exception: ", throwable);
                return null;
            }

            @Override
            public UserDto findUserByUsername(String username) {
                log.error("feign [findUserByUsername] Exception: ", throwable);
                return null;
            }

            @Override
            public AuthUserDto findUserByUsernameForAuthenticate(String username) {
                log.error("feign [findUserByUsernameForAuthenticate] Exception: ", throwable);
                return null;
            }

            @Override
            public UserDto findUserByEmail(String email) {
                log.error("feign [findUserByEmail] Exception: ", throwable);
                return null;
            }

            @Override
            public List<UserDto> findAllUserByIdList(List<Long> idList) {
                log.error("feign [findAllUserByIdList] Exception: ", throwable);
                return Collections.emptyList();
            }

            @Override
            public UserDto getLoginUser() {
                log.error("feign [getLoginUser] Exception: ", throwable);
                return null;
            }

            @Override
            public List<UserDto> search(String keyword) {
                log.error("feign [search] Exception: ", throwable);
                return Collections.emptyList();
            }
        };
    }
}
