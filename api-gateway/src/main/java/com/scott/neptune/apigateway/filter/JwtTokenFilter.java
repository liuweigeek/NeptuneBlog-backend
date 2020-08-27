package com.scott.neptune.apigateway.filter;

import com.scott.neptune.apigateway.util.SecurityUtils;
import com.scott.neptune.authenticationclient.jwt.JwtTokenProvider;
import com.scott.neptune.common.exception.RestException;
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
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = SecurityUtils.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (RestException e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

}
