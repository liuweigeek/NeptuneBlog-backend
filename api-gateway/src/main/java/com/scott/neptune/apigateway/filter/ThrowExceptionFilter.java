package com.scott.neptune.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;
import com.scott.neptune.common.response.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
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

            ObjectMapper mapper = new ObjectMapper();
            ServerResponse<Void> errorResponse = ServerResponse.createByErrorMessage("服务不可用，请稍后再试");
            HttpServletResponse response = requestContext.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().println(mapper.writeValueAsString(errorResponse));
        } catch (Exception e) {
            log.error("拦截器异常", e);
        }
        return null;
    }
}
