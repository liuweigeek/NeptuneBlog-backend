package com.scott.neptune.file.service.impl;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.file.component.MinioComponent;
import com.scott.neptune.file.enumerate.FileUseTypeEnum;
import com.scott.neptune.file.property.MinioProperties;
import com.scott.neptune.file.service.IFileService;
import com.scott.neptune.file.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/4 18:30
 * @Description: NeptuneBlog
 */
@Slf4j
@Service
public class FileServiceImpl implements IFileService {

    @Resource
    private MinioProperties minioProperties;
    @Resource
    private MinioComponent minioComponent;

    /**
     * 上传文件
     *
     * @param fileUseTypeEnum
     * @param file
     * @param useRandomName
     * @return
     */
    @Override
    public ServerResponse saveFile(FileUseTypeEnum fileUseTypeEnum, MultipartFile file, boolean useRandomName) {
        fileUseTypeEnum = FileUseTypeEnum.getDefaultIfNull(fileUseTypeEnum);
        String fileName = useRandomName ?
                FileUtils.getRandomNameByOriginName(file.getOriginalFilename())
                : file.getOriginalFilename();
        return minioComponent.saveMultipartFile(minioProperties.getBucket(),
                fileUseTypeEnum.getFolder(),
                file, fileName);
    }

    /**
     * 上传文件
     *
     * @param fileUseTypeEnum
     * @param fileList
     * @param useRandomName
     * @return
     */
    @Override
    public ServerResponse saveBatchFile(FileUseTypeEnum fileUseTypeEnum, List<MultipartFile> fileList, boolean useRandomName) {
        fileUseTypeEnum = FileUseTypeEnum.getDefaultIfNull(fileUseTypeEnum);
        List<String> filenameList;
        if (useRandomName) {
            filenameList = fileList.stream()
                    .map(file -> FileUtils.getRandomNameByOriginName(file.getOriginalFilename()))
                    .collect(Collectors.toList());
        } else {
            filenameList = fileList.stream()
                    .map(MultipartFile::getOriginalFilename)
                    .collect(Collectors.toList());
        }
        return minioComponent.saveMultipartFiles(minioProperties.getBucket(),
                fileUseTypeEnum.getFolder(), fileList, filenameList);
    }

    /**
     * 删除文件
     *
     * @param objectName 文件对象名称(路径加文件名)
     * @return
     */
    @Override
    public ServerResponse deleteFile(String objectName) {
        return minioComponent.deleteFile(minioProperties.getBucket(), objectName);
    }

    /**
     * 删除文件
     *
     * @param folder   所在文件夹
     * @param fileName 文件名
     * @return
     */
    @Override
    public ServerResponse deleteFile(String folder, String fileName) {
        return minioComponent.deleteFile(minioProperties.getBucket(), folder, fileName);
    }
}
