package com.scott.neptune.userclient.client;

import com.scott.neptune.common.config.FeignConfig;
import com.scott.neptune.userclient.dto.AuthUserDto;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userclient.hystric.UserClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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
@FeignClient(name = "user-server", configuration = FeignConfig.class, fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    /**
     * 新增用户
     *
     * @param userDto 用户对象
     * @return 保存结果
     */
    @RequestMapping(method = RequestMethod.POST)
    UserDto addUser(UserDto userDto);

    /**
     * 根据ID获取指定用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @RequestMapping(value = "server/user/{id}", method = RequestMethod.GET)
    UserDto findUserById(@PathVariable Long id);

    /**
     * 根据用户名获取指定用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @RequestMapping(value = "server/user/username/{username}", method = RequestMethod.GET)
    UserDto findUserByUsername(@PathVariable String username);

    @RequestMapping(value = "authenticate/{username}", method = RequestMethod.GET)
    AuthUserDto findUserByUsernameForAuthenticate(@PathVariable String username);

    /**
     * 根据邮箱获取指定用户
     *
     * @param email 邮箱
     * @return 用户对象
     */
    @RequestMapping(value = "server/user/email/{email}", method = RequestMethod.GET)
    UserDto findUserByEmail(@PathVariable String email);

    /**
     * 通过ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户对象列表
     */
    //TODO change the http method
    @RequestMapping(value = "server/user/findAllUserByIdList", method = RequestMethod.POST)
    List<UserDto> findAllUserByIdList(@RequestBody List<Long> idList);

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @GetMapping(value = "server/user/loginUser")
    UserDto getLoginUser();

    /**
     * 通过关键字搜索用户
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @RequestMapping(value = "server/user/search/{keyword}", method = RequestMethod.GET)
    List<UserDto> search(@PathVariable String keyword);
}
