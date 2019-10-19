package com.scott.neptune.post.remote.hystric;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.remote.server.FileServer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author scott
 */
@Component
public class FileServerHystrix implements FileServer {

    /**
     * 上传文件
     *
     * @param file      文件
     * @param useTypeId 文件用途标识[1: default, 2: avatar, 3: user background, 4: post image]
     * @return
     */
    @Override
    public ServerResponse<String> upload(MultipartFile file, Integer useTypeId) {
        return ServerResponse.createByErrorMessage("上传文件失败，请稍后重试");
    }
}
