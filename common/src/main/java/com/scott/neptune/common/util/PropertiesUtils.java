package com.scott.neptune.common.util;

import com.scott.neptune.common.exception.NeptuneBlogException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/23 09:33
 * @Description:
 */
@Slf4j
public class PropertiesUtils {

    private static final Map<String, Properties> propsMap = new HashMap<>();

    private static Properties getProp(String fileName) {
        if (!propsMap.containsKey(fileName)) {
            try {
                Reader resourceReader = new InputStreamReader(Objects.requireNonNull(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName)),
                        StandardCharsets.UTF_8);
                Properties properties = new Properties();
                properties.load(resourceReader);
                propsMap.put(fileName, properties);
                resourceReader.close();
            } catch (Exception e) {
                log.error("配置文件[" + fileName + "]读取异常", e);
                throw new NeptuneBlogException("配置文件[" + fileName + "]读取异常", e);
            }
        }

        return propsMap.get(fileName);
    }

    /**
     * get properties
     *
     * @param fileName
     * @param key
     * @return
     */
    public static String getProperty(String fileName, String key) {
        String value = getProp(fileName).getProperty(key);
        if (StringUtils.isBlank(value)) {
            return Strings.EMPTY;
        }
        return value.trim();
    }

    /**
     * get properties, if not found, return default value
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
