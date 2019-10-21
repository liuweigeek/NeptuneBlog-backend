package com.scott.neptune.post.remote.client;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.remote.hystric.FileClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * User服务的远程调用接口
 *
 * @author scott
 */
@FeignClient(name = "file", fallback = FileClientHystrix.class)
public interface FileClient {

    /**
     * 上传文件
     *
     * @param file      文件
     * @param useTypeId 文件用途标识 [1: default, 2: avatar, 3: user background, 4: post image]
     * @return
     */
    @GetMapping(value = "/file/fileServer/upload")
    ServerResponse<String> upload(@RequestParam("file") MultipartFile file,
                                  @RequestParam(defaultValue = "1") Integer useTypeId);

}
