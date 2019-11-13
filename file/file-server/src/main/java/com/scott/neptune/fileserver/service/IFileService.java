package com.scott.neptune.fileserver.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.fileserver.enumerate.FileUseTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/4 18:29
 * @Description: NeptuneBlog
 */
public interface IFileService {

    /**
     * 上传文件
     *
     * @param fileUseTypeEnum
     * @param file
     * @param useRandomName
     * @return
     */
    ServerResponse<String> saveFile(FileUseTypeEnum fileUseTypeEnum, File file, boolean useRandomName);

    /**
     * 上传文件
     *
     * @param fileUseTypeEnum
     * @param file
     * @param useRandomName
     * @return
     */
    ServerResponse<String> saveMultipartFile(FileUseTypeEnum fileUseTypeEnum, MultipartFile file, boolean useRandomName);

    /**
     * 上传文件
     *
     * @param fileUseTypeEnum
     * @param fileList
     * @param useRandomName
     * @return
     */
    ServerResponse<List<String>> saveBatchFile(FileUseTypeEnum fileUseTypeEnum, List<File> fileList, boolean useRandomName);

    /**
     * 上传文件
     *
     * @param fileUseTypeEnum
     * @param fileList
     * @param useRandomName
     * @return
     */
    ServerResponse<List<String>> saveBatchMultipartFile(FileUseTypeEnum fileUseTypeEnum, List<MultipartFile> fileList, boolean useRandomName);

    /**
     * 删除文件
     *
     * @param objectName 文件对象名称(路径加文件名)
     * @return
     */
    ServerResponse deleteFile(String objectName);

    /**
     * 删除文件
     *
     * @param folder   所在文件夹
     * @param fileName 文件名
     * @return
     */
    ServerResponse deleteFile(String folder, String fileName);
}
