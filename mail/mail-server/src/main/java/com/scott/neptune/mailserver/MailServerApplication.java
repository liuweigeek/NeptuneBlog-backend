package com.scott.neptune.mailserver;

import com.scott.neptune.common.constant.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/25 13:42
 * @Description:
 */
@ComponentScan(basePackages = Constant.BASE_PACKAGE)
@EnableDiscoveryClient
@SpringBootApplication
public class MailServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailServerApplication.class, args);
    }

}
