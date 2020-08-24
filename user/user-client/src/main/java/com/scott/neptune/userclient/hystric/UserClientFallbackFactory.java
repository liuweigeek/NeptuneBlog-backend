package com.scott.neptune.userclient.hystric;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.userclient.client.UserClient;
import com.scott.neptune.userclient.command.UserSearchRequest;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Component;

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
                throw new NeptuneBlogException("feign [addUser] Exception: ", throwable);
            }

            @Override
            public UserDto getUserById(Long id) {
                log.error("feign [getUserById] Exception: ", throwable);
                throw new NeptuneBlogException("feign [getUserById] Exception: ", throwable);
            }

            @Override
            public UserDto getUserByUsername(String username) {
                log.error("feign [getUserByUsername] Exception: ", throwable);
                throw new NeptuneBlogException("feign [getUserByUsername] Exception: ", throwable);
            }

            @Override
            public List<UserDto> findUsersByIds(String ids) {
                log.error("feign [getUsersByIds] Exception: ", throwable);
                throw new NeptuneBlogException("feign [getUsersByIds] Exception: ", throwable);
            }

            @Override
            public List<UserDto> findUsersByUsernames(String usernames) {
                log.error("feign [getUsersByUsernames] Exception: ", throwable);
                throw new NeptuneBlogException("feign [getUsersByUsernames] Exception: ", throwable);
            }

            @Override
            public List<UserDto> search(UserSearchRequest request) {
                log.error("feign [search] Exception: ", throwable);
                throw new NeptuneBlogException("feign [search] Exception: ", throwable);
            }

            @Override
            public AuthUserDto getUserByUsernameForAuthenticate(String username) {
                log.error("feign [getUserByUsernameForAuthenticate] Exception: ", throwable);
                throw new NeptuneBlogException("feign [getUserByUsernameForAuthenticate] Exception: ", throwable);
            }

            @Override
            public List<Long> findAllFollowingIds(Long id) {
                log.error("feign [findAllFollowingIds] Exception: ", throwable);
                throw new NeptuneBlogException("feign [findAllFollowingIds] Exception: ", throwable);
            }

            @Override
            public List<Long> findAllFollowerIds(Long id) {
                log.error("feign [findAllFollowerIds] Exception: ", throwable);
                throw new NeptuneBlogException("feign [findAllFollowerIds] Exception: ", throwable);
            }
        };
    }
}
