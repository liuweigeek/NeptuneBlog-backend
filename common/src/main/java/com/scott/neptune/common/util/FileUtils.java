package com.scott.neptune.common.util;

import com.google.common.io.Files;
import com.scott.neptune.common.base.BaseStorageInfo;
import com.scott.neptune.common.exception.NeptuneBlogException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/10/4 16:38
 * @Description: NeptuneBlog
 */
public class FileUtils {

    private static final String DEFAULT_DIR = "default";
    private static final String PATH_SEPARATOR = File.separator;

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        return Files.getFileExtension(fileName);
    }

    /**
     * 根据文件使用业务类型获取对应文件夹
     *
     * @param businessTypeEnum
     * @return
     */
    public static String getFolderByType(BaseStorageInfo.BusinessTypeEnum businessTypeEnum) {
        return businessTypeEnum.getFolder();
    }

    /**
     * 根据文件夹和文件名获取相对路径
     *
     * @param folder
     * @param fileName
     * @return
     */
    public static String getObjectKeyByFolderAndName(String folder, String fileName) {

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
     * @param extension 扩展名
     * @return
     */
    public static String getRandomFileName(String extension) {
        if (StringUtils.isBlank(extension)) {
            extension = "";
        }

        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }
        return UUIDUtils.generateUUID() + extension;
    }

    /**
     * 生成随机文件名(包含前缀)
     *
     * @param extension 扩展名
     * @param prefix    前缀
     * @return
     */
    public static String getRandomFileName(String extension, String prefix) {
        if (StringUtils.isBlank(extension)) {
            extension = "";
        }
        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }
        return prefix + UUIDUtils.generateUUID() + extension;
    }

    /**
     * 根据原文件名生成新的随机文件名
     *
     * @param originFileName
     * @return
     */
    public static String getRandomNameByOriginName(String originFileName) {
        String extension = getExtension(originFileName);
        return getRandomFileName(extension);
    }

    /**
     * 根据原文件名生成新的随机文件名
     *
     * @param originFileName
     * @return
     */
    public static String getRandomNameByOriginName(String originFileName, String prefix) {
        String extension = getExtension(originFileName);
        return getRandomFileName(extension, prefix);
    }

    public static File createFileByFilename(String folder, String filename) {
        return new File(folder + PATH_SEPARATOR + filename);
    }

    public static File createFileByExtension(String folder, String extension) {
        return new File(folder + PATH_SEPARATOR + FileUtils.getRandomFileName(extension));
    }

    public static File transferToFile(String folder, MultipartFile multipartFile) {
        return transferToFile(folder, multipartFile, multipartFile.getOriginalFilename());
    }

    public static File transferToFile(String folder, MultipartFile multipartFile, String filename) {
        File tempFile = new File(folder + File.separator + FileUtils.getRandomNameByOriginName(multipartFile.getOriginalFilename()));
        try {
            multipartFile.transferTo(tempFile);
            return tempFile;
        } catch (Exception e) {
            throw new NeptuneBlogException("transfer file exception: ", e);
        }

    }

}
