package com.scott.neptune.common.util;

import com.scott.neptune.common.exception.NeptuneBlogException;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/25 21:19
 * @Description:
 */
public class AssertUtils {

    /**
     * 断言不为Null
     *
     * @param obj
     * @param errorMsg
     */
    public static void assertNotNull(Object obj, String errorMsg) {
        if (obj == null) {
            throw new NeptuneBlogException(errorMsg);
        }
    }
}
