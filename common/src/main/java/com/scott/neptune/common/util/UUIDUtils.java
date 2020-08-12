package com.scott.neptune.common.util;

import java.util.UUID;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/9/30 09:00
 * @Description: NeptuneBlog
 */
public class UUIDUtils {

    /**
     * generate lower case UUID
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * generate upper case UUID
     *
     * @return
     */
    public static String generateUpperCaseUUID() {
        return generateUUID().toUpperCase();
    }
}
