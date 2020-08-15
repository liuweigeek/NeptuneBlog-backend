package com.scott.neptune.common.config;

import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.common.model.ApiErrorResponse;
import feign.Contract;
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

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2020/8/7 09:40
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class FeignConfig {

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
    public ErrorDecoder apiErrorResponseDecoder(Decoder decoder) {
        return (methodKey, response) -> {
            try {
                ApiErrorResponse apiErrorResponse = (ApiErrorResponse) decoder.decode(response, ApiErrorResponse.class);
                return new RestException(apiErrorResponse.getMessage(), HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                log.error("decoding feign response exception: ", e);
                return new NeptuneBlogException("系统服务异常，请稍后再试", e);
            }
        };
    }
}
