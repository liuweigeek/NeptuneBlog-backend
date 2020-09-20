package com.scott.neptune.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.common.model.ApiErrorResponse;
import feign.Contract;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/7 09:40
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class FeignConfig {

    private final ObjectMapper objectMapper;
    private final ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    public Decoder springDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(messageConverters));
    }

    @Bean
    public Contract feignContract() {
        return new SpringMvcContract();
    }

    @Bean
    public ErrorDecoder apiErrorResponseDecoder() {
        return (methodKey, response) -> {
            try {
                ApiErrorResponse errorResponse = objectMapper.readValue(Util.toString(response.body().asReader(StandardCharsets.UTF_8)), ApiErrorResponse.class);
                log.error("feign exception, status: {}, body: {}", response.status(), errorResponse.getMessage());
                return new RestException(errorResponse.getMessage(), HttpStatus.valueOf(response.status()));
            } catch (Exception e) {
                log.error("decoding feign response exception: ", e);
                return new RestException("系统服务异常，请稍后再试", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
