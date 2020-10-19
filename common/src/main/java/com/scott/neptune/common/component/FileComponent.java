package com.scott.neptune.common.component;

import com.scott.neptune.common.component.oss.MinioComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/29 17:49
 * @Description: NeptuneBlog
 */
@RequiredArgsConstructor
@ConditionalOnBean(name = "minioComponent")
@Component
public class FileComponent {

    private final MinioComponent minioComponent;

    /**
     * 上传文件
     *
     * @param folder
     * @param file
     * @param filename
     * @return
     */
    public String saveFile(String folder, File file, String filename) {
        return minioComponent.saveObject(folder + "/" + filename, file);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param filename
     * @return
     */
    public String saveFile(File file, String filename) {
        return minioComponent.saveObject(filename, file);
    }

    /**
     * 批量上传文件
     *
     * @param folder
     * @param fileList
     * @param filenames
     * @return
     */
    public List<String> saveFiles(String folder, List<File> fileList, List<String> filenames) {
        List<String> fileFullPaths = filenames.stream()
                .map(filename -> folder + "/" + filename)
                .collect(Collectors.toList());
        return minioComponent.saveObjects(fileFullPaths, fileList);
    }

    /**
     * 批量上传文件
     *
     * @param fileList
     * @param filenames
     * @return
     */
    public List<String> saveFiles(List<File> fileList, List<String> filenames) {

        return minioComponent.saveObjects(filenames, fileList);
    }

    /**
     * 上传文件
     *
     * @param folder
     * @param file
     * @param filename
     * @return
     */
    public String saveMultipartFile(String folder, MultipartFile file, String filename) {
        return minioComponent.saveMultipartObject(folder + "/" + filename, file);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param filename
     * @return
     */
    public String saveMultipartFile(MultipartFile file, String filename) {
        return minioComponent.saveMultipartObject(filename, file);
    }

    /**
     * 批量上传文件
     *
     * @param folder
     * @param fileList
     * @param filenames
     * @return
     */
    public List<String> saveMultipartFiles(String folder, List<MultipartFile> fileList, List<String> filenames) {
        List<String> fileFullPaths = filenames.stream()
                .map(filename -> folder + "/" + filename)
                .collect(Collectors.toList());
        return minioComponent.saveMultipartObjects(fileFullPaths, fileList);
    }

    /**
     * 批量上传文件
     *
     * @param fileList
     * @param filenames
     * @return
     */
    public List<String> saveMultipartFiles(List<MultipartFile> fileList, List<String> filenames) {
        return minioComponent.saveMultipartObjects(filenames, fileList);
    }

    /**
     * 删除文件
     *
     * @param folder
     * @param filename
     * @return
     */
    public boolean deleteFile(String folder, String filename) {
        return minioComponent.deleteObject(folder + "/" + filename);
    }

    /**
     * 批量删除文件
     *
     * @param folder
     * @param filenames
     * @return
     */
    public boolean deleteFiles(String folder, List<String> filenames) {
        List<String> fileFullPaths = filenames.stream()
                .map(filename -> folder + "/" + filename)
                .collect(Collectors.toList());
        return minioComponent.deleteObjects(fileFullPaths);
    }
}
