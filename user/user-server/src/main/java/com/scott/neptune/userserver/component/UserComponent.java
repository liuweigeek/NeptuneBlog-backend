package com.scott.neptune.userserver.component;

import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.util.HeaderUtils;
import com.scott.neptune.userclient.dto.UserDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 13:58
 * @Description: 用户相关组件
 */
@Component
public class UserComponent {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 通过Request获取当前登录用户
     *
     * @param request HttpServlet
     * @return 当前登录用户
     */
    public UserDto getUserFromRequest(HttpServletRequest request) {

        String token = HeaderUtils.get(request, Constant.Login.CURRENT_USER);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return (UserDto) redisTemplate.opsForValue().get(token);
    }

}
