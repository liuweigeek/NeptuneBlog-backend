package com.scott.neptune.common.component;

import com.scott.neptune.common.base.BaseStorageInfo;
import com.scott.neptune.common.component.oss.MinioComponent;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 17:49
 * @Description: NeptuneBlog
 */
@Component
public class FileComponent {

    private final MinioComponent minioComponent;

    public FileComponent(MinioComponent minioComponent) {
        this.minioComponent = minioComponent;
    }

    /**
     * 上传文件
     *
     * @param storageInfo
     * @param file
     * @param filename
     * @return
     */
    public String saveFile(BaseStorageInfo storageInfo, File file, String filename) {
        return minioComponent.saveObject(storageInfo.getBucket(), storageInfo.getFolder() + "/" + filename, file);
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
     * @param storageInfo
     * @param fileList
     * @param filenames
     * @return
     */
    public List<String> saveFiles(BaseStorageInfo storageInfo, List<File> fileList, List<String> filenames) {
        List<String> fileFullPaths = filenames.stream()
                .map(filename -> storageInfo.getFolder() + "/" + filename)
                .collect(Collectors.toList());
        return minioComponent.saveObjects(storageInfo.getBucket(), fileFullPaths, fileList);
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
     * @param storageInfo
     * @param file
     * @param filename
     * @return
     */
    public String saveMultipartFile(BaseStorageInfo storageInfo, MultipartFile file, String filename) {
        return minioComponent.saveMultipartObject(storageInfo.getBucket(), storageInfo.getFolder() + "/" + filename, file);
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
     * @param storageInfo
     * @param fileList
     * @param filenames
     * @return
     */
    public List<String> saveMultipartFiles(BaseStorageInfo storageInfo, List<MultipartFile> fileList, List<String> filenames) {
        List<String> fileFullPaths = filenames.stream()
                .map(filename -> storageInfo.getFolder() + "/" + filename)
                .collect(Collectors.toList());
        return minioComponent.saveMultipartObjects(storageInfo.getBucket(), fileFullPaths, fileList);
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
     * @param storageInfo
     * @param filename
     * @return
     */
    public boolean deleteFile(BaseStorageInfo storageInfo, String filename) {
        return minioComponent.deleteObject(storageInfo.getBucket(), storageInfo.getFolder() + "/" + filename);
    }

    /**
     * 批量删除文件
     *
     * @param storageInfo
     * @param filenames
     * @return
     */
    public boolean deleteFiles(BaseStorageInfo storageInfo, List<String> filenames) {
        List<String> fileFullPaths = filenames.stream()
                .map(filename -> storageInfo.getFolder() + "/" + filename)
                .collect(Collectors.toList());
        return minioComponent.deleteObjects(storageInfo.getBucket(), fileFullPaths);
    }
}
