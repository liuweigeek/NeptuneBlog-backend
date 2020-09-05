package com.scott.neptune.apigateway.filter;

import com.netflix.zuul.context.RequestContext;
import com.scott.neptune.common.exception.NeptuneBlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/29 08:17
 * @Description: NeptuneBlog
 */
@Slf4j
@Component
public class ThrowExceptionFilter extends SendErrorFilter {

    @Override
    public Object run() {
        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            ExceptionHolder exception = findZuulException(requestContext.getThrowable());
            log.error("网关异常: ", exception.getThrowable());
            throw new NeptuneBlogException("服务不可用，请稍后再试");
        } catch (Exception e) {
            log.error("拦截器异常", e);
        }
        return null;
    }
}
