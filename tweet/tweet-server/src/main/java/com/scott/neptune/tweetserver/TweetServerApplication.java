package com.scott.neptune.tweetserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author scott
 */
@EntityScan(basePackages = "com.scott.neptune")
@ComponentScan(basePackages = "com.scott.neptune")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.scott.neptune")
@EnableHystrix
@EnableCaching
@SpringBootApplication
public class TweetServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetServerApplication.class, args);
    }

}
