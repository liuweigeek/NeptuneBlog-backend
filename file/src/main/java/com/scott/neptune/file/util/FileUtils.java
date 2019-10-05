package com.scott.neptune.file.util;

import com.scott.neptune.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/4 16:38
 * @Description: NeptuneBlog
 */
public class FileUtils {

    private static final String DEFAULT_DIR = "default";
    private static final String PATH_SEPARATOR = "/";

    /**
     * 根据文件夹和文件名获取相对路径
     *
     * @param folder
     * @param fileName
     * @return
     */
    public static String getPathByFolderAndName(String folder, String fileName) {

        if (StringUtils.isBlank(folder)) {
            return fileName;
        }
        return formatFolder(folder) + fileName;
    }

    /**
     * 处理文件夹名
     *
     * @param folder 文件夹
     * @return
     */
    private static String formatFolder(String folder) {
        if (StringUtils.isBlank(folder) || StringUtils.equals(folder, PATH_SEPARATOR)) {
            return DEFAULT_DIR;
        }
        if (StringUtils.startsWith(folder, PATH_SEPARATOR)) {
            folder = folder.substring(1);
        }
        if (!StringUtils.endsWith(folder, PATH_SEPARATOR)) {
            folder = folder + PATH_SEPARATOR;
        }
        return folder;
    }

    public static String getRandomFileName(String extensionName) {
        if (StringUtils.isBlank(extensionName)) {
            extensionName = "";
        } else {
            if (!extensionName.startsWith(".")) {
                extensionName = "." + extensionName;
            }
        }
        return UUIDUtils.generateUUID() + extensionName;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExtensionName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
