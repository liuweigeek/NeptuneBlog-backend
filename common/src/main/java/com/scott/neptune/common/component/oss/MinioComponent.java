package com.scott.neptune.common.component.oss;

import com.google.common.collect.Lists;
import com.scott.neptune.common.property.MinioProperties;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.common.util.FileUtils;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/30 08:50
 * @Description: NeptuneBlog
 */
@Slf4j
@Component
public class MinioComponent {

    @Resource
    private MinioClient minioClient;
    @Resource
    private MinioProperties minioProperties;

    /**
     * 获取文件URL
     *
     * @param bucket   容器
     * @param folder   文件夹
     * @param filename 文件名
     * @return URL
     */
    public String getFileUrl(String bucket, String folder, String filename) {
        try {
            return minioClient.getObjectUrl(StringUtils.isNotBlank(bucket) ? bucket : minioProperties.getBucket(),
                    FileUtils.getPathByFolderAndName(folder, filename));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件URL(可访问时间限制)
     *
     * @param bucket   容器
     * @param folder   文件夹
     * @param filename 文件名
     * @param expires  可访问时间(秒)
     * @return URL
     */
    public String getFileUrl(String bucket, String folder, String filename, Integer expires) {
        try {
            return minioClient.presignedGetObject(StringUtils.isNotBlank(bucket) ? bucket : minioProperties.getBucket(),
                    FileUtils.getPathByFolderAndName(folder, filename), expires);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定文件夹下的全部文件名
     *
     * @param bucket 容器
     * @param bucket 容器
     * @return 文件名列表
     */
    public List<String> getFiles(String bucket) {
        if (StringUtils.isBlank(bucket)) {
            bucket = minioProperties.getBucket();
        }
        try {
            Iterable<Result<Item>> objects = minioClient.listObjects(bucket);
            List<String> filenames = new LinkedList<>();
            for (Result<Item> object : objects) {
                Item item = object.get();
                filenames.add(item.objectName());
            }
            return filenames;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 保存文件
     *
     * @param bucket 容器
     * @param folder 文件夹
     * @param file   文件
     * @return 保存结果
     */
    public ServerResponse<String> saveMultipartFile(String bucket, String folder, MultipartFile file) {
        return saveMultipartFile(bucket, folder, file, file.getOriginalFilename());
    }

    /**
     * 保存文件
     *
     * @param bucket   容器
     * @param folder   文件夹
     * @param file     文件
     * @param filename 保存的文件名
     * @return 保存结果
     */
    public ServerResponse<String> saveMultipartFile(String bucket, String folder, MultipartFile file, String filename) {
        if (StringUtils.isBlank(bucket)) {
            bucket = minioProperties.getBucket();
        }
        try {
            minioClient.putObject(bucket, FileUtils.getPathByFolderAndName(folder, filename),
                    file.getInputStream(), file.getSize(), null, null, file.getContentType());
            String url = this.getFileUrl(bucket, folder, filename);
            return ServerResponse.createBySuccess(url);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("保存失败");
        }
    }

    /**
     * 批量保存文件
     *
     * @param bucket    容器
     * @param folder    文件夹
     * @param files     文件列表
     * @param filenames 保存的文件名列表
     * @return 保存结果
     */
    public ServerResponse<List<String>> saveMultipartFiles(String bucket, String folder, List<MultipartFile> files, List<String> filenames) {
        List<String> urlList = Lists.newArrayListWithExpectedSize(files.size());
        for (int i = 0; i < files.size(); i++) {
            ServerResponse<String> saveResponse = saveMultipartFile(bucket, folder, files.get(i), filenames.get(i));
            if (saveResponse.isFailed()) {
                return ServerResponse.createByErrorMessage("保存失败");
            }
            urlList.add(saveResponse.getData());
        }
        return ServerResponse.createBySuccess(urlList);
    }

    /**
     * 批量保存文件
     *
     * @param bucket 容器
     * @param folder 文件夹
     * @param files  文件列表
     * @return 保存结果
     */
    public ServerResponse<List<String>> saveMultipartFiles(String bucket, String folder, List<MultipartFile> files) {
        List<String> filenames = files.stream()
                .map(MultipartFile::getOriginalFilename)
                .collect(Collectors.toList());
        return this.saveMultipartFiles(bucket, folder, files, filenames);
    }

    /**
     * 保存文件
     *
     * @param bucket 容器
     * @param folder 文件夹
     * @param file   文件
     * @return 保存结果
     */
    public ServerResponse<String> saveFile(String bucket, String folder, File file) {
        return saveFile(bucket, folder, file, file.getName());
    }

    /**
     * 保存文件
     *
     * @param bucket   容器
     * @param folder   文件夹
     * @param file     文件
     * @param filename 保存的文件名
     * @return 保存结果
     */
    public ServerResponse<String> saveFile(String bucket, String folder, File file, String filename) {
        if (file == null || !file.exists()) {
            return ServerResponse.createByErrorMessage("请传入文件");
        }

        try {
            InputStream fileInputStream = new FileInputStream(file);
            minioClient.putObject(StringUtils.isNotBlank(bucket) ? bucket : minioProperties.getBucket(),
                    FileUtils.getPathByFolderAndName(folder, filename), fileInputStream,
                    file.length(), null, null, Files.probeContentType(Paths.get(file.getPath())));
            String url = this.getFileUrl(bucket, folder, filename);
            return ServerResponse.createBySuccess(url);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("保存失败");
        }
    }

    /**
     * 批量保存文件
     *
     * @param bucket    容器
     * @param folder    文件夹
     * @param files     文件列表
     * @param filenames 保存的文件名列表
     * @return 保存结果
     */
    public ServerResponse<List<String>> saveFiles(String bucket, String folder, List<File> files, List<String> filenames) {
        List<String> urlList = Lists.newArrayListWithExpectedSize(files.size());
        for (int i = 0; i < files.size(); i++) {
            ServerResponse<String> saveResponse = saveFile(bucket, folder, files.get(i), filenames.get(i));
            if (saveResponse.isFailed()) {
                return ServerResponse.createByErrorMessage("保存失败");
            }
            urlList.add(saveResponse.getData());
        }
        return ServerResponse.createBySuccess(urlList);
    }

    /**
     * 批量保存文件
     *
     * @param bucket 容器
     * @param folder 文件夹
     * @param files  文件列表
     * @return 保存结果
     */
    public ServerResponse<List<String>> saveFiles(String bucket, String folder, List<File> files) {
        List<String> filenames = files.stream()
                .map(File::getName)
                .collect(Collectors.toList());
        return this.saveFiles(bucket, folder, files, filenames);
    }

    /**
     * 删除文件
     *
     * @param bucket   容器
     * @param folder   所在文件夹
     * @param filename 文件名
     */
    public ServerResponse deleteFile(String bucket, String folder, String filename) {
        try {
            minioClient.removeObject(StringUtils.isNotBlank(bucket) ? bucket : minioProperties.getBucket(),
                    FileUtils.getPathByFolderAndName(folder, filename));
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    public ServerResponse deleteFile(String bucket, String objectName) {
        try {
            minioClient.removeObject(StringUtils.isNotBlank(bucket) ? bucket : minioProperties.getBucket(), objectName);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

}
