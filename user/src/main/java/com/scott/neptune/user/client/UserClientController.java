package com.scott.neptune.user.client;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.common.util.LocaleUtil;
import com.scott.neptune.user.component.UserComponent;
import com.scott.neptune.user.entity.UserEntity;
import com.scott.neptune.user.service.IUserService;
import com.scott.neptune.user.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 为其他服务提供的用户接口
 */
@Slf4j
@RestController
@RequestMapping("/userClient")
public class UserClientController extends BaseController {

    @Resource
    private IUserService userService;
    @Resource
    private UserComponent userComponent;

    /**
     * 获取指定用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @GetMapping(value = "/getUserById/{id}")
    public ServerResponse<UserDto> getUserById(@PathVariable String id) {
        UserEntity userEntity = userService.getUserById(id);

        if (userEntity == null) {
            return ServerResponse.createByErrorMessage(messageSource.getMessage("error.userNotFound", null,
                    LocaleUtil.getLocaleFromUser(null)));
        }
        return ServerResponse.createBySuccess(UserUtil.convertToDto(userEntity));
    }

    /**
     * 通过ID列表获取全部用户
     *
     * @param idList 用户ID列表
     * @return 用户对象列表
     */
    @GetMapping(value = "/findAllUserByIdList")
    public List<UserDto> findAllUserByIdList(List<String> idList) {
        return userService.findAllUserByIdList(idList)
                .stream()
                .map(UserUtil::convertToDto)
                .collect(toList());
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @GetMapping(value = "/getLoginUser")
    public ServerResponse<UserDto> getLoginUser() {
        UserDto userDto = userComponent.getUserFromRequest(request);
        return ServerResponse.createBySuccess(userDto);
    }
}
