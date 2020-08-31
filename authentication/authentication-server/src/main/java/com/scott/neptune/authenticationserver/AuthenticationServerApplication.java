package com.scott.neptune.authenticationserver;

import com.scott.neptune.common.constant.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = Constant.BASE_PACKAGE)
@EnableFeignClients(basePackages = Constant.BASE_PACKAGE)
@SpringBootApplication
public class AuthenticationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServerApplication.class, args);
    }

}
