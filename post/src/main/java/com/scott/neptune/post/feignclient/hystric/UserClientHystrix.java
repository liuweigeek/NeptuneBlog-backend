package com.scott.neptune.post.feignclient.hystric;

import com.google.common.collect.Lists;
import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.feignclient.UserClient;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

@Component
public class UserClientHystrix implements UserClient {

    @Resource
    private MessageSource messageSource;

    /**
     * 获取指定用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @Override
    public ServerResponse<UserDto> getUserById(String id) {
        return ServerResponse.createByErrorMessage(messageSource.getMessage("exception.userServiceException", null, Locale.getDefault()));
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @Override
    public ServerResponse<UserDto> getLoginUser() {
        return ServerResponse.createByErrorMessage(messageSource.getMessage("exception.userServiceException", null, Locale.getDefault()));
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
        return ServerResponse.createByErrorMessage(messageSource.getMessage("exception.getFollowingUserException", null, Locale.getDefault()));
    }
}
