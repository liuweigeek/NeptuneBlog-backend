package com.scott.neptune.tweetserver.api.web;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2020/8/9 18:19
 * @Description:
 */
@Slf4j
@Api(tags = "推文接口 - 面向前端")
@RestController
@RequestMapping("/statuses")
public class StatusController {
}
