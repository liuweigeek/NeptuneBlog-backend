package com.scott.neptune.socialserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2020/1/5 13:46
 * @Description:
 */
@EnableSwagger2
@MapperScan("com.scott.neptune.socialserver.mapper")
@EntityScan(basePackages = "com.scott.neptune")
@ComponentScan(basePackages = "com.scott.neptune")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.scott.neptune")
@EnableHystrix
@EnableCaching
@SpringBootApplication
public class SocialServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialServerApplication.class, args);
    }

}
