package com.scott.neptune.fileclient.hystric;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.fileclient.FileClient;
import com.scott.neptune.userapi.dto.UserAvatarDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author scott
 */
@Slf4j
@Component
public class FileClientFallbackFactory implements FallbackFactory<FileClient> {

    @Override
    public FileClient create(Throwable throwable) {
        return new FileClient() {
            /**
             * 上传文件
             *
             * @param file      文件
             * @param useTypeId 文件用途标识 [1: default, 2: avatar, 3: user background, 4: post image]
             * @return
             */
            @Override
            public ServerResponse<String> upload(MultipartFile file, Integer useTypeId) {
                log.error("feign fallback Exception: ", throwable);
                return ServerResponse.createByErrorMessage("上传文件失败，请稍后重试");
            }

            /**
             * 上传头像
             *
             * @param file
             * @return
             */
            @Override
            public ServerResponse<List<UserAvatarDto>> uploadAvatar(MultipartFile file) {
                log.error("feign fallback Exception: ", throwable);
                return ServerResponse.createByErrorMessage("上传头像失败，请稍后重试");
            }
        };
    }
}
