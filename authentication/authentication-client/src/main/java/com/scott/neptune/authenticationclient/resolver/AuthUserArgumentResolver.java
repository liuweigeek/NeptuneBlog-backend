package com.scott.neptune.authenticationclient.resolver;

import com.scott.neptune.authenticationclient.client.AuthClient;
import com.scott.neptune.authenticationclient.jwt.JwtTokenProvider;
import com.scott.neptune.authenticationclient.properties.JwtProperties;
import com.scott.neptune.common.util.SpringContextUtils;
import com.scott.neptune.userclient.dto.AuthUserDto;
import lombok.extern.slf4j.Slf4j;
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

    private final AuthClient authClient = SpringContextUtils.getBean(AuthClient.class);
    private final JwtProperties jwtProperties = SpringContextUtils.getBean(JwtProperties.class);
    private final JwtTokenProvider jwtTokenProvider = SpringContextUtils.getBean(JwtTokenProvider.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(AuthUserDto.class);
    }

    @Override
    public AuthUserDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                       NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        //return (AuthUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = webRequest.getHeader(jwtProperties.getHeader());
        String username = jwtTokenProvider.getUsername(token);
        //TODO catch
        return authClient.loadUserByUsername(username);
    }
}
