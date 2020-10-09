package com.scott.neptune.authenticationclient.resolver;

import com.scott.neptune.authenticationclient.jwt.JwtTokenProvider;
import com.scott.neptune.authenticationclient.property.JwtProperties;
import com.scott.neptune.common.util.SpringContextUtils;
import com.scott.neptune.userclient.dto.AuthUserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
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
        String claimsJws = jwtTokenProvider.getClaimsJws(bearerToken);
        if (StringUtils.isNotBlank(claimsJws)) {
            return jwtTokenProvider.getAutoUser(claimsJws);
        }
        return new AuthUserDto();
    }
}
