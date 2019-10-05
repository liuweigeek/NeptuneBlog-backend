package com.scott.neptune.user.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import lombok.Getter;
import lombok.Setter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/5 22:53
 * @Description: NeptuneBlog
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.scott.neptune.mapper")
public class MyBatisPlusConfig {

    @Getter
    @Setter
    @Value("${mybatis-plus.pageSize}")
    private Integer pageSize;

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor()
                .setCountSqlParser(new JsqlParserCountOptimize(true))
                .setOverflow(false)
                .setLimit(pageSize);
    }
}