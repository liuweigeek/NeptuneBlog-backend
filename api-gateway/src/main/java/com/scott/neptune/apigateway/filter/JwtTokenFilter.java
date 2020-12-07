package com.scott.neptune.apigateway.filter;

import com.scott.neptune.apigateway.util.SecurityUtils;
import com.scott.neptune.authenticationclient.jwt.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: scott
 * @Date: 2020/7/28 15:45
 * @Description:
 */
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String claimsJws = jwtTokenProvider.getClaimsJws(request);
        if (StringUtils.isNotBlank(claimsJws)) {
            try {
                jwtTokenProvider.validateToken(claimsJws);
                Authentication auth = SecurityUtils.getAuthentication(claimsJws);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException | IllegalArgumentException e) {
                log.error("无效的token: {}", claimsJws);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
        filterChain.doFilter(request, response);
    }

}
