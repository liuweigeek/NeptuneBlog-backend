package com.scott.neptune.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.scott.neptune.common.model.ApiErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/29 08:17
 * @Description: NeptuneBlog
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ThrowExceptionFilter extends ZuulFilter {

    private static final String ERROR_STATUS_CODE_KEY = "throwable";

    private final ObjectMapper objectMapper;

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_ERROR_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().containsKey(ERROR_STATUS_CODE_KEY);
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Throwable exception = (Exception) ctx.get(ERROR_STATUS_CODE_KEY);
            if (exception instanceof ZuulException) {
                ctx.remove(ERROR_STATUS_CODE_KEY);
                ZuulException zuulException = (ZuulException) exception;
                log.error("网关异常: ", zuulException);

                ApiErrorResponse apiErrorResponse = ApiErrorResponse.createByMessage("服务不可用，请稍后再试");
                HttpServletResponse response = ctx.getResponse();
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(objectMapper.writeValueAsString(apiErrorResponse));
                ctx.setSendZuulResponse(false);
            }
        } catch (Exception e) {
            log.error("拦截器异常", e);
        }
        return null;
    }
}
