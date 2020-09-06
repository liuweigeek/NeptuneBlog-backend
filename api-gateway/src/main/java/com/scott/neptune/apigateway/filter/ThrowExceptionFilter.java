package com.scott.neptune.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.scott.neptune.common.exception.NeptuneBlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/29 08:17
 * @Description: NeptuneBlog
 */
@Slf4j
@Component
public class ThrowExceptionFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        // only forward to errorPath if it hasn't been forwarded to already
        return RequestContext.getCurrentContext().containsKey("error.status_code");
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Object exception = ctx.get("error.exception");
            if (exception instanceof ZuulException) {
                ctx.remove("error.status_code");
                ZuulException zuulException = (ZuulException) exception;
                log.error("网关异常: ", zuulException);
                throw new NeptuneBlogException("服务不可用，请稍后再试");
            }
        } catch (Exception e) {
            log.error("拦截器异常", e);
        }
        return null;
    }
}
