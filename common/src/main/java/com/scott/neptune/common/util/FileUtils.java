package com.scott.neptune.common.util;

import com.google.common.io.Files;
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
    private static final String FILE_EXTENSION_DOT = ".";

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
     * 生成随机文件名
     *
     * @param extension 扩展名
     * @return
     */
    public static String getRandomFileNameByExtension(String extension) {
        if (StringUtils.isBlank(extension)) {
            extension = "";
        }
        if (!extension.startsWith(FILE_EXTENSION_DOT)) {
            extension = FILE_EXTENSION_DOT + extension;
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
    public static String getRandomFileNameByExtension(String extension, String prefix) {
        return prefix + getRandomFileNameByExtension(extension);
    }

    /**
     * 根据原文件名生成新的随机文件名
     *
     * @param originFileName
     * @return
     */
    public static String getRandomNameByOriginName(String originFileName) {
        String extension = getExtension(originFileName);
        return getRandomFileNameByExtension(extension);
    }

    /**
     * 根据原文件名生成新的随机文件名
     *
     * @param originFileName
     * @param prefix
     * @return
     */
    public static String getRandomNameByOriginName(String originFileName, String prefix) {
        String extension = getExtension(originFileName);
        return getRandomFileNameByExtension(extension, prefix);
    }

    /**
     * create a file on disk by specific filename
     *
     * @param folder
     * @param filename
     * @return
     */
    public static File createFileByFilename(String folder, String filename) {
        return new File(folder + PATH_SEPARATOR + filename);
    }

    /**
     * create a file on disk by specific extension
     *
     * @param folder
     * @param extension
     * @return
     */
    public static File createFileByExtension(String folder, String extension) {
        return new File(folder + PATH_SEPARATOR + FileUtils.getRandomFileNameByExtension(extension));
    }

    /**
     * transfer multipart file on disk
     *
     * @param folder
     * @param multipartFile
     * @return
     */
    public static File transferToFile(String folder, MultipartFile multipartFile) {
        return transferToFile(folder, multipartFile, multipartFile.getOriginalFilename());
    }

    /**
     * transfer multipart file on disk
     *
     * @param folder
     * @param multipartFile
     * @param filename
     * @return
     */
    public static File transferToFile(String folder, MultipartFile multipartFile, String filename) {
        File tempFile = new File(folder + File.separator + filename);
        try {
            multipartFile.transferTo(tempFile);
            return tempFile;
        } catch (Exception e) {
            throw new NeptuneBlogException("transfer file exception: ", e);
        }
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            file.exists();
        }
    }

}
