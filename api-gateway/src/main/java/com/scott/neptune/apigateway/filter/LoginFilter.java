package com.scott.neptune.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.userapi.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
@Component
public class LoginFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Value("${loginFilter.ignoreUris}")
    private String[] ignoreUris;

    @Resource
    private UserUtils userUtils;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private MessageSource messageSource;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
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
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

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
                ServerResponse noLoginResponse = ServerResponse.createByErrorMessage("用户未登录，请先登录后重试");
                response.getWriter().println(mapper.writeValueAsString(noLoginResponse));
            } catch (Exception e) {
                logger.error("拦截器异常");
            }
        }
        return null;
    }
}
