package com.scott.neptune.common.base;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/19 12:07
 * @Description: 基础Controller
 */
public abstract class BaseController {

    @Resource
    protected HttpServletRequest httpServletRequest;
    @Resource
    protected HttpServletResponse httpServletResponse;
    @Resource
    protected HttpSession httpSession;
    /*@Resource
    protected MessageSource messageSource;*/

}