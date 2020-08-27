package com.scott.neptune.apigateway.util;

import com.scott.neptune.authenticationclient.client.AuthClient;
import com.scott.neptune.authenticationclient.jwt.JwtTokenProvider;
import com.scott.neptune.common.util.SpringContextUtils;
import com.scott.neptune.userclient.dto.AuthUserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/27 22:19
 * @Description:
 */
public class SecurityUtils {

    private static final AuthClient AUTH_CLIENT = SpringContextUtils.getBean(AuthClient.class);
    private static final JwtTokenProvider JWT_TOKEN_PROVIDER = SpringContextUtils.getBean(JwtTokenProvider.class);

    public static Authentication getAuthentication(String token) {
        AuthUserDto authUser = AUTH_CLIENT.loadUserByUsername(JWT_TOKEN_PROVIDER.getUsername(token));
        return new UsernamePasswordAuthenticationToken(authUser, "",
                AuthorityUtils.createAuthorityList(authUser.getAuthorities()));
    }
}
