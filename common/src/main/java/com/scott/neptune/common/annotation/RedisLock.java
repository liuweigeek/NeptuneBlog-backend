package com.scott.neptune.common.annotation;

import java.lang.annotation.*;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/11/10 17:27
 * @Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisLock {

    /**
     * 持锁时间,单位毫秒
     */
    long keepTimeInMills() default 30000;

}
