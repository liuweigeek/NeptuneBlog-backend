package com.scott.neptune.userclient.client;

import com.scott.neptune.common.config.FeignConfig;
import com.scott.neptune.userclient.command.UserSearchRequest;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userclient.hystric.UserClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * User服务的远程调用接口
 *
 * @author scott
 */
@FeignClient(name = "user-server", configuration = FeignConfig.class, fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    /**
     * 新增用户
     *
     * @param userDto 用户对象
     * @return 保存结果
     */
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    UserDto addUser(UserDto userDto);

    /**
     * 根据ID获取指定用户
     *
     * @param id         用户ID
     * @param screenName 用户名
     * @return
     */
    @RequestMapping(path = "/users/show", method = RequestMethod.GET)
    UserDto show(Long id, String screenName);

    /**
     * 获取全部用户列表
     *
     * @param userIds     用户ID列表
     * @param screenNames 用户名列表
     * @return
     */
    @RequestMapping(path = "/users/lookup", method = RequestMethod.GET)
    Collection<UserDto> lookup(String userIds, String screenNames);

    /**
     * 通过关键字搜索用户
     *
     * @param request 关键字
     * @return 用户列表
     */
    @RequestMapping(path = "/users/search", method = RequestMethod.GET)
    Collection<UserDto> search(UserSearchRequest request);

    /**
     * 根据用户名获取指定用户,用于授权
     *
     * @param screenName 用户名
     * @return
     */
    @RequestMapping(path = "/users/authenticate/{screenName}", method = RequestMethod.GET)
    AuthUserDto getUserByScreenNameForAuthenticate(@PathVariable String screenName);
}
