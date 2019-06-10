package com.scott.neptune.user.component;

import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.dto.UserDto;
import com.scott.neptune.user.util.HeaderUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 用户相关工具类
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

        String token = HeaderUtil.get(request, Constant.Login.CURRENT_USER);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return (UserDto) redisTemplate.opsForValue().get(token);
    }

    public Locale getUserLocale(String userId) {
        return Locale.getDefault();
    }
}
