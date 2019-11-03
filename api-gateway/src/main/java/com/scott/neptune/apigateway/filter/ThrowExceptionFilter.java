package com.scott.neptune.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.scott.neptune.common.response.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_ERROR_FILTER_ORDER;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 08:17
 * @Description: NeptuneBlog
 */
@Slf4j
//TODO disabled
//@Component
public class ThrowExceptionFilter extends ZuulFilter {

    private static final String ERROR_STATUS_CODE = "error.status_code";

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_ERROR_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().contains(ERROR_STATUS_CODE);
    }

    @Override
    public Object run() throws ZuulException {

        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            Object exception = requestContext.get("error.exception");

            if (!Objects.isNull(exception) && exception instanceof ZuulException) {
                ZuulException zuulException = (ZuulException) exception;
                log.error("网关异常: " + zuulException.getMessage(), zuulException);

                requestContext.remove(ERROR_STATUS_CODE);
                HttpServletResponse response = requestContext.getResponse();
                ObjectMapper mapper = new ObjectMapper();
                response.setContentType("application/json;charset=utf-8");
                ServerResponse errorResponse = ServerResponse.createByErrorMessage("服务异常，请稍后再试");
                response.getWriter().println(mapper.writeValueAsString(errorResponse));

                requestContext.setResponseStatusCode(200);
            }
        } catch (Exception e) {
            log.error("拦截器异常", e);
        }
        return null;
    }
}
