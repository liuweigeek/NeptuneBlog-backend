package com.scott.neptune.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.response.ResponseCode;
import com.scott.neptune.common.response.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 登录拦截器
 *
 * @author scott
 */
@Slf4j
@Component
public class LoginFilter extends ZuulFilter {

    @Value("${loginFilter.ignoreUris}")
    private String[] ignoreUris;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        return !StringUtils.equalsAny(requestContext.getRequest().getRequestURI(), ignoreUris);
    }

    /**
     * 校验是否包含登录用户信息
     *
     * @return
     */
    @Override
    public Object run() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String token = request.getHeader(Constant.Login.CURRENT_USER);
        if (StringUtils.isBlank(token) ||
                redisTemplate.opsForValue().get(token) == null) {

            requestContext.setSendZuulResponse(false);
            try {
                HttpServletResponse response = requestContext.getResponse();
                ObjectMapper mapper = new ObjectMapper();
                response.setContentType("application/json;charset=utf-8");
                ServerResponse noLoginResponse = ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                        "用户未登录，请先登录后重试");
                response.getWriter().println(mapper.writeValueAsString(noLoginResponse));
            } catch (Exception e) {
                log.error("拦截器异常", e);
            }
        } else {
            redisTemplate.expire(token, 30, TimeUnit.MINUTES);
        }
        return null;
    }

}
