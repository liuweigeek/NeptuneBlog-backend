package com.scott.neptune.common.interceptor;

import com.scott.neptune.common.constant.Constant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/10/31 08:25
 * @Description: Feign统一Token拦截器
 */
@Slf4j
@Component
public class FeignTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = getHttpServletRequest();
        if (Objects.isNull(request)) {
            log.warn("cannot get request from RequestContextHolder");
            return;
        }
        String currentUserId = request.getHeader(Constant.Login.CURRENT_USER);
        //将获取Token对应的值往下一个服务传递
        requestTemplate.header(Constant.Login.CURRENT_USER, currentUserId);
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }
}
