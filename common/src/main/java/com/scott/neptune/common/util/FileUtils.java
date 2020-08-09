package com.scott.neptune.common.util;

import com.scott.neptune.common.enumerate.FileUseTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

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

    /**
     * 生成随机文件名
     *
     * @param extensionName 扩展名
     * @return
     */
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
     * 根据原文件名生成新的随机文件名
     *
     * @param originFileName
     * @return
     */
    public static String getRandomNameByOriginName(String originFileName) {
        String extensionName = "";
        if (StringUtils.contains(originFileName, ".")) {
            extensionName = getExtensionName(originFileName);
        }
        return getRandomFileName(extensionName);
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

    public static String getFolderNameByType(int code) {
        FileUseTypeEnum useTypeEnum = FileUseTypeEnum.getEnum(code);
        if (Objects.isNull(useTypeEnum)) {
            return DEFAULT_DIR;
        }
        return useTypeEnum.getFolder();
    }

    /**
     * 根据文件使用业务类型获取对应文件夹
     *
     * @param fileUseTypeEnum
     * @return
     */
    public static String getFolderNameByType(FileUseTypeEnum fileUseTypeEnum) {
        return fileUseTypeEnum.getFolder();
    }

}
