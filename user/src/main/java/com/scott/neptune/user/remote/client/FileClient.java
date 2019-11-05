package com.scott.neptune.user.remote.client;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.remote.hystric.FileClientFallbackFactory;
import com.scott.neptune.userapi.dto.UserAvatarDto;
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
@FeignClient(name = "file", fallbackFactory = FileClientFallbackFactory.class)
public interface FileClient {

    /**
     * 上传文件
     *
     * @param file      文件
     * @param useTypeId 文件用途标识 [1: default, 2: avatar, 3: user background, 4: post image]
     * @return
     */
    @RequestMapping(value = "/fileServer/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ServerResponse<String> upload(@RequestPart("file") MultipartFile file,
                                  @RequestParam("useTypeId") Integer useTypeId);

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
