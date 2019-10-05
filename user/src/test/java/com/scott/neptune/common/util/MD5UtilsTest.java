package com.scott.neptune.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:23
 * @Description: NeptuneBlog
 */
@Slf4j
public class MD5UtilsTest {

    @Test
    public void MD5EncodeUtf8() {
        String encodePass = MD5Utils.MD5EncodeUtf8("12345678");
        log.info(encodePass);
        assert true;
    }
}