package com.scott.neptune.tweetserver;

import com.scott.neptune.common.constant.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author scott
 */
@EntityScan(basePackages = "com.scott.neptune.tweetserver.domain.entity")
@ComponentScan(basePackages = Constant.BASE_PACKAGE)
@EnableFeignClients(basePackages = Constant.BASE_PACKAGE)
@EnableDiscoveryClient
@SpringBootApplication
public class TweetServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetServerApplication.class, args);
    }

}
