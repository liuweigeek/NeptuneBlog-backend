package com.scott.neptune.userserver.api.web;

import com.scott.neptune.common.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/11/5 23:05
 * @Description: NeptuneBlog
 */
@Slf4j
@RestController
@RefreshScope
public class TestController extends BaseController {

    @Value("${label}")
    private String label;

    @GetMapping("/label")
    public String label() {
        return label;
    }
}
