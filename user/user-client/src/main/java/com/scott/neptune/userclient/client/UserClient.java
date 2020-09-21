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
import org.springframework.web.bind.annotation.RequestParam;

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
     * 获取指定用户信息
     *
     * @param id 用户ID
     * @return
     */
    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    UserDto getUserById(@PathVariable("id") Long id);

    /**
     * 获取指定用户信息
     *
     * @param username 用户名
     * @return
     */
    @RequestMapping(path = "/users/username/{username}", method = RequestMethod.GET)
    UserDto getUserByUsername(@PathVariable("username") String username);

    /**
     * 获取用户列表
     *
     * @param ids 用户ID列表
     * @return
     */
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    Collection<UserDto> findUsersByIds(String ids);

    /**
     * 获取用户列表
     *
     * @param usernames 用户名列表
     * @return
     */
    @RequestMapping(path = "/users/username", method = RequestMethod.GET)
    Collection<UserDto> findUsersByUsernames(String usernames);

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
     * @param username 用户名
     * @return
     */
    @RequestMapping(path = "/users/authenticate/{username}", method = RequestMethod.GET)
    AuthUserDto getUserByUsernameForAuthenticate(@PathVariable("username") String username);

    /**
     * 获取全部已关注用户ID列表
     *
     * @param id 指定用户视角
     * @return
     */
    @RequestMapping(path = "/following/ids/all", method = RequestMethod.GET)
    Collection<Long> findAllFollowingIds(@RequestParam("userId") Long id);

    /**
     * 获取全部关注者用户ID列表
     *
     * @param id 指定用户视角
     * @return
     */
    @RequestMapping(path = "/followers/ids/all", method = RequestMethod.GET)
    Collection<Long> findAllFollowerIds(@RequestParam("userId") Long id);
}
