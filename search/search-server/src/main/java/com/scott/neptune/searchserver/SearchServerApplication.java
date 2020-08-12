package com.scott.neptune.searchserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/10/21 23:51
 * @Description:
 */
@EntityScan(basePackages = "com.scott.neptune")
@ComponentScan(basePackages = "com.scott.neptune")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.scott.neptune")
@EnableHystrix
@SpringBootApplication
public class SearchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchServerApplication.class, args);
    }

}
