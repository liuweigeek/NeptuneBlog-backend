package com.scott.neptune.fileclient;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.fileclient.config.FeignMultipartSupportConfig;
import com.scott.neptune.fileclient.hystric.FileClientFallbackFactory;
import com.scott.neptune.userclient.dto.UserAvatarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * User服务的远程调用接口
 *
 * @author scott
 */
@FeignClient(name = "file-server", fallbackFactory = FileClientFallbackFactory.class,
        configuration = FeignMultipartSupportConfig.class)
public interface FileClient {

    /**
     * 上传文件
     *
     * @param file      文件
     * @param useTypeId 文件用途标识 [1: default, 2: avatar, 3: user background, 4: post image]
     * @return
     */
    @RequestMapping(value = "/file/fileServer/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ServerResponse<String> upload(@RequestPart("file") MultipartFile file,
                                  @RequestParam(value = "useTypeId", defaultValue = "1") Integer useTypeId);

    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/fileServer/uploadAvatar", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ServerResponse<List<UserAvatarDto>> uploadAvatar(@RequestPart("file") MultipartFile file);

}
