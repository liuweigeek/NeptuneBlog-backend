package com.scott.neptune.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;
import com.scott.neptune.common.response.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.http.MediaType;
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
public class ThrowExceptionFilter extends SendErrorFilter {

    private final ObjectMapper objectMapper;

    @Override
    public Object run() {

        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            ExceptionHolder exception = findZuulException(requestContext.getThrowable());
            log.error("网关异常: ", exception.getThrowable());

            ServerResponse<Void> errorResponse = ServerResponse.createByErrorMessage("服务不可用，请稍后再试");
            HttpServletResponse response = requestContext.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().println(objectMapper.writeValueAsString(errorResponse));
        } catch (Exception e) {
            log.error("拦截器异常", e);
        }
        return null;
    }
}
