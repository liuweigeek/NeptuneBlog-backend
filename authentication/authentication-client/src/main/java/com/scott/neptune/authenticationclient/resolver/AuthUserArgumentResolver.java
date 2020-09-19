package com.scott.neptune.authenticationclient.resolver;

import com.scott.neptune.authenticationclient.jwt.JwtTokenProvider;
import com.scott.neptune.authenticationclient.property.JwtProperties;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.common.util.SpringContextUtils;
import com.scott.neptune.userclient.dto.AuthUserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/16 14:19
 * @Description:
 */
@Slf4j
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProperties jwtProperties = SpringContextUtils.getBean(JwtProperties.class);
    private final JwtTokenProvider jwtTokenProvider = SpringContextUtils.getBean(JwtTokenProvider.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(AuthUserDto.class);
    }

    @Override
    public AuthUserDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                       NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String bearerToken = webRequest.getHeader(jwtProperties.getHeader());
        if (StringUtils.isBlank(bearerToken) || !StringUtils.startsWith(bearerToken, jwtProperties.getHeaderPrefix() + " ")) {
            throw new RestException("无权访问，请检查登录状态", HttpStatus.UNAUTHORIZED);
        }
        String claimsJws = bearerToken.substring(jwtProperties.getHeaderPrefix().length() + 1);
        return jwtTokenProvider.getAutoUser(claimsJws);
    }
}
