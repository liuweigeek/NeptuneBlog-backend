package com.scott.neptune.apigateway.config;

import com.scott.neptune.apigateway.filter.JwtTokenFilter;
import com.scott.neptune.apigateway.handler.ApiAccessDeniedHandler;
import com.scott.neptune.apigateway.handler.ApiAuthenticationEntryPoint;
import com.scott.neptune.apigateway.handler.ApiAuthenticationFailureHandler;
import com.scott.neptune.authenticationclient.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;
    private final ApiAuthenticationFailureHandler apiAuthenticationFailureHandler;
    private final ApiAccessDeniedHandler apiAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .logout().disable()
                .formLogin()
                .failureHandler(apiAuthenticationFailureHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(apiAccessDeniedHandler)
                .authenticationEntryPoint(apiAuthenticationEntryPoint)
                .and()
                .addFilterAfter(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/auth-server/auth/signIn", "/auth-server/auth/signUp").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/actuator/**")
                .antMatchers("/health/**")
                .antMatchers("/configuration/**")
                .antMatchers("/webjars/**")
                .antMatchers("/public");
    }
}
