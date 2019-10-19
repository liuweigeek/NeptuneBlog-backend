package com.scott.neptune.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:33
 * @Description:
 */
@Slf4j
public class PropertiesUtils {

    private static Map<String, Properties> propsMap = new HashMap<>();

    private static Properties getProp(String fileName) {
        if (!propsMap.containsKey(fileName)) {
            try {
                Properties properties = new Properties();
                properties.load(new InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName), StandardCharsets.UTF_8));
                propsMap.put(fileName, properties);
            } catch (Exception e) {
                log.error("配置文件[" + fileName + "]读取异常", e);
                return null;
            }
        }

        return propsMap.get(fileName);
    }

    /**
     * get properties
     *
     * @param key
     * @return
     */
    public static String getProperty(String fileName, String key) {
        String value = getProp(fileName).getProperty(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    /**
     * get properties, is not found, return default value
     *
     * @param fileName
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperty(String fileName, String key, String defaultValue) {
        String value = getProp(fileName).getProperty(key);
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }

}
