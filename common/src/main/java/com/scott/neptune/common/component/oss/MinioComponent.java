package com.scott.neptune.common.component.oss;

import com.google.common.collect.Lists;
import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.property.MinioProperties;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.Result;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/9/30 08:50
 * @Description: NeptuneBlog
 */
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "neptune.oss.minio.endpoint")
@Component
public class MinioComponent {

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;

    /**
     * 获取文件对象URL
     *
     * @param objectKey 文件对象名
     * @return URL
     */
    public String getObjectUrl(String objectKey) {
        return getObjectUrl(minioProperties.getBucket(), objectKey);
    }

    /**
     * 获取对象URL
     *
     * @param bucket    容器
     * @param objectKey 文件对象名
     * @return URL
     */
    public String getObjectUrl(String bucket, String objectKey) {
        try {
            return minioClient.getObjectUrl(bucket, objectKey);
        } catch (Exception e) {
            log.error("获取文件对象访问链接异常: ", e);
            throw new NeptuneBlogException("获取文件对象访问链接异常: ", e);
        }
    }

    /**
     * 获取文件对象URL(可访问时间限制)
     *
     * @param objectKey 文件对象名
     * @param expires   可访问时间(秒)
     * @return URL
     */
    public String getObjectUrlWithExpires(String objectKey, int expires) {
        return getObjectUrlWithExpires(minioProperties.getBucket(), objectKey, expires);
    }

    /**
     * 获取文件对象URL(可访问时间限制)
     *
     * @param bucket    容器
     * @param objectKey 文件对象名
     * @param expires   可访问时间(秒)
     * @return URL
     */
    public String getObjectUrlWithExpires(String bucket, String objectKey, int expires) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(objectKey)
                    .expiry(expires)
                    .build());
        } catch (Exception e) {
            log.error("生成文件对象访问链接异常: ", e);
            throw new NeptuneBlogException("生成文件对象访问链接异常: ", e);
        }
    }

    /**
     * 获取默认容器下的全部文件对象名
     *
     * @return 文件对象名列表
     */
    public List<String> getObjects() {
        return getObjects(minioProperties.getBucket());
    }

    /**
     * 获取指定容器下的全部文件对象名
     *
     * @param bucket 容器
     * @return 文件对象名
     */
    public List<String> getObjects(String bucket) {
        try {
            Iterator<Result<Item>> iterator = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(bucket)
                    .build()
            ).iterator();
            List<String> filenames = new ArrayList<>();
            while (iterator.hasNext()) {
                filenames.add(iterator.next().get().objectName());
            }
            return filenames;
        } catch (Exception e) {
            log.error("文件对象存储服务器异常: ", e);
            throw new NeptuneBlogException("文件对象存储服务器异常: ", e);
        }
    }

    /**
     * 保存文件对象
     *
     * @param objectKey 文件对象名
     * @param file      文件对象
     * @return URL
     */
    public String saveObject(String objectKey, File file) {
        return this.saveObject(minioProperties.getBucket(), objectKey, file);
    }

    /**
     * 保存文件对象
     *
     * @param bucket    容器
     * @param objectKey 文件对象名
     * @param file      文件对象
     * @return URL
     */
    public String saveObject(String bucket, String objectKey, File file) {
        if (file == null || !file.exists()) {
            throw new NeptuneBlogException("请传入文件");
        }
        try (InputStream fileInputStream = new FileInputStream(file);) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectKey)
                    .stream(fileInputStream, file.length(), -1)
                    .contentType(Files.probeContentType(Paths.get(file.getPath())))
                    .build());
            return this.getObjectUrl(bucket, objectKey);
        } catch (Exception e) {
            log.error("保存文件对象失败: ", e);
            throw new NeptuneBlogException("保存文件对象失败");
        }
    }

    /**
     * 批量保存文件对象
     *
     * @param objectKeys 文件对象名列表
     * @param files      文件对象列表
     * @return URL列表
     */
    public List<String> saveObjects(List<String> objectKeys, List<File> files) {
        return this.saveObjects(minioProperties.getBucket(), objectKeys, files);
    }

    /**
     * 批量保存文件对象
     *
     * @param bucket     容器
     * @param objectKeys 文件对象名列表
     * @param files      文件对象列表
     * @return URL列表
     */
    public List<String> saveObjects(String bucket, List<String> objectKeys, List<File> files) {
        List<String> urlList = Lists.newArrayListWithExpectedSize(files.size());
        for (int i = 0; i < files.size(); i++) {
            urlList.add(saveObject(bucket, objectKeys.get(i), files.get(i)));
        }
        return urlList;
    }

    /**
     * 保存文件对象
     *
     * @param objectKey 文件对象名
     * @param file      文件对象
     * @return URL
     */
    public String saveMultipartObject(String objectKey, MultipartFile file) {
        return saveMultipartObject(minioProperties.getBucket(), objectKey, file);
    }

    /**
     * 保存文件对象
     *
     * @param bucket    容器
     * @param objectKey 文件对象名
     * @param file      文件对象
     * @return URL
     */
    public String saveMultipartObject(String bucket, String objectKey, MultipartFile file) {

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectKey)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            return this.getObjectUrl(bucket, objectKey);
        } catch (Exception e) {
            log.error("保存文件对象失败: ", e);
            throw new NeptuneBlogException("保存文件对象失败", e);
        }
    }

    /**
     * 批量保存文件对象
     *
     * @param files      文件对象列表
     * @param objectKeys 文件对象名列表
     * @return URL列表
     */
    public List<String> saveMultipartObjects(List<String> objectKeys, List<MultipartFile> files) {
        List<String> urlList = Lists.newArrayListWithExpectedSize(files.size());
        for (int i = 0; i < files.size(); i++) {
            urlList.add(saveMultipartObject(minioProperties.getBucket(), objectKeys.get(i), files.get(i)));
        }
        return urlList;
    }

    /**
     * 批量保存文件对象
     *
     * @param bucket     容器
     * @param objectKeys 文件对象名列表
     * @param files      文件对象列表
     * @return URL列表
     */
    public List<String> saveMultipartObjects(String bucket, List<String> objectKeys, List<MultipartFile> files) {
        List<String> urlList = Lists.newArrayListWithExpectedSize(files.size());
        for (int i = 0; i < files.size(); i++) {
            urlList.add(saveMultipartObject(bucket, objectKeys.get(i), files.get(i)));
        }
        return urlList;
    }

    /**
     * 删除文件对象
     *
     * @param objectKey 文件对象名
     * @return 删除结果
     */
    public boolean deleteObject(String objectKey) {
        return this.deleteObject(minioProperties.getBucket(), objectKey);
    }

    /**
     * 删除文件对象
     *
     * @param bucket    容器
     * @param objectKey 文件对象名
     * @return 删除结果
     */
    public boolean deleteObject(String bucket, String objectKey) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectKey)
                    .build());
            return true;
        } catch (Exception e) {
            log.error("删除对象[" + objectKey + "]失败");
            throw new NeptuneBlogException("删除对象失败");
        }
    }

    /**
     * 批量删除文件对象
     *
     * @param objectKeys 文件对象名列表
     * @return 删除结果
     */
    public boolean deleteObjects(List<String> objectKeys) {
        return this.deleteObjects(minioProperties.getBucket(), objectKeys);
    }

    /**
     * 批量删除文件对象
     *
     * @param bucket     容器
     * @param objectKeys 文件对象名列表
     * @return 删除结果
     */
    public boolean deleteObjects(String bucket, List<String> objectKeys) {
        try {
            minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(bucket)
                    .objects(objectKeys.stream().map(DeleteObject::new).collect(Collectors.toList()))
                    .build());
            return true;
        } catch (Exception e) {
            log.error("删除文件对象" + objectKeys + "失败");
            throw new NeptuneBlogException("删除文件对象失败");
        }
    }

}
