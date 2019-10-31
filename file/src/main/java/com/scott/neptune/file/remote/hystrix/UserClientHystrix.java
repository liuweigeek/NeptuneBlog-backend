package com.scott.neptune.file.remote.hystrix;

import com.google.common.collect.Lists;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.file.remote.client.UserClient;
import com.scott.neptune.userapi.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author scott
 */
@Component
public class UserClientHystrix implements UserClient {

    /**
     * 获取指定用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @Override
    public ServerResponse<UserDto> getUserById(String id) {
        return ServerResponse.createByErrorMessage("用户服务异常，请稍后重试");
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @Override
    public ServerResponse<UserDto> getLoginUser() {
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
        return Lists.newArrayListWithCapacity(0);
    }

    @Override
    public ServerResponse<List<String>> getFollowingUserIds() {
        return ServerResponse.createByErrorMessage("获取关注用户异常，请稍后重试");
    }
}
