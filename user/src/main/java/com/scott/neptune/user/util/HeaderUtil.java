package com.scott.neptune.user.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:14
 * @Description: Header工具类
 */
@Slf4j
public class HeaderUtil {

    /**
     * 获取Header
     *
     * @param request    响应
     * @param headerName header名称
     * @return
     */
    public static String get(HttpServletRequest request, String headerName) {

        return request.getHeader(headerName);
    }

    /**
     * 设置Header
     *
     * @param response    响应
     * @param headerName  header名称
     * @param headerValue header值
     */
    public static void set(HttpServletResponse response, String headerName, String headerValue) {
        response.addHeader(headerName, headerValue);
    }
}
