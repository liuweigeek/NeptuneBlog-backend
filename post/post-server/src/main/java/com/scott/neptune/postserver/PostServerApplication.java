package com.scott.neptune.postserver;

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
 * @author scott
 */
@EnableSwagger2
@MapperScan("com.scott.neptune.postserver.mapper")
@EntityScan(basePackages = "com.scott.neptune")
@ComponentScan(basePackages = "com.scott.neptune")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.scott.neptune")
@EnableHystrix
@EnableCaching
@SpringBootApplication
public class PostServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostServerApplication.class, args);
    }

}
