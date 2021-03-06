package com.scott.neptune.authenticationserver;

import com.scott.neptune.common.constant.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = Constant.BASE_PACKAGE)
@EnableFeignClients(basePackages = Constant.BASE_PACKAGE)
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class
})
public class AuthenticationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServerApplication.class, args);
    }

}
