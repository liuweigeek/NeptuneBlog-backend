package com.scott.neptune.userclient.hystric;

import com.google.common.collect.Lists;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.userapi.dto.UserDto;
import com.scott.neptune.userclient.UserClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author scott
 */
@Slf4j
@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {

            /**
             * 获取指定用户
             *
             * @param id 用户ID
             * @return 用户对象
             */
            @Override
            public ServerResponse<UserDto> getUserById(String id) {
                log.error("feign fallback Exception: ", throwable);
                return ServerResponse.createByErrorMessage("用户服务异常，请稍后重试");
            }

            /**
             * 获取当前登录用户
             *
             * @return 当前登录用户
             */
            @Override
            public ServerResponse<UserDto> getLoginUser() {
                log.error("feign fallback Exception: ", throwable);
                return ServerResponse.createByErrorMessage("用户服务异常，请稍后重试");
            }

            /**
             * 通过ID列表获取全部用户
             *
             * @param idList 用户ID列表
             * @return 用户对象列表
             */
            @Override
            public List<UserDto> findAllUserByIdList(List<String> idList) {
                log.error("feign fallback Exception: ", throwable);
                return Lists.newArrayListWithCapacity(0);
            }

            @Override
            public ServerResponse<List<String>> getFollowingUserIds() {
                log.error("feign fallback Exception: ", throwable);
                return ServerResponse.createByErrorMessage("获取关注用户异常，请稍后重试");
            }

            /**
             * 通过关键字搜索用户
             *
             * @param keyword 关键字
             * @return 用户列表
             */
            @Override
            public ServerResponse<List<UserDto>> findByKeyword(String keyword) {
                log.error("feign fallback Exception: ", throwable);
                return ServerResponse.createByErrorMessage("搜索用户异常，请稍后重试");
            }
        };
    }
}
