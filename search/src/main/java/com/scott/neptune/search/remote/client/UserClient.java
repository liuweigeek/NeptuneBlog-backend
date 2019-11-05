package com.scott.neptune.search.remote.client;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.search.remote.hystric.UserClientFallbackFactory;
import com.scott.neptune.userapi.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * User服务的远程调用接口
 *
 * @author scott
 */
@FeignClient(name = "user", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    /**
     * 获取指定用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @RequestMapping(value = "/userServer/getUserById/{id}", method = RequestMethod.GET)
    ServerResponse<UserDto> getUserById(@PathVariable String id);

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @RequestMapping(value = "/userServer/getLoginUser", method = RequestMethod.GET)
    ServerResponse<UserDto> getLoginUser();

    /**
     * 通过ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户对象列表
     */
    @RequestMapping(value = "/userServer/findAllUserByIdList", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    List<UserDto> findAllUserByIdList(@RequestBody List<String> idList);

    /**
     * 获取全部正在关注的用户Id
     *
     * @return
     */
    @RequestMapping(value = "/friendServer/getFollowingUserIds", method = RequestMethod.GET)
    ServerResponse<List<String>> getFollowingUserIds();

    /**
     * 通过关键字搜索用户
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @RequestMapping(value = "/userServer/findByKeyword/{keyword}", method = RequestMethod.GET)
    ServerResponse<List<UserDto>> findByKeyword(@PathVariable String keyword);
}
