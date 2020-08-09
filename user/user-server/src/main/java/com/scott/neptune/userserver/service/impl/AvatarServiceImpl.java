package com.scott.neptune.userserver.service.impl;

import com.google.common.collect.Lists;
import com.scott.neptune.common.component.oss.FileComponent;
import com.scott.neptune.common.component.oss.ImageComponent;
import com.scott.neptune.common.enumerate.FileUseTypeEnum;
import com.scott.neptune.common.model.ImageSize;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.common.service.IFileService;
import com.scott.neptune.common.util.VerifyFileTypeUtil;
import com.scott.neptune.userclient.dto.UserAvatarDto;
import com.scott.neptune.userserver.property.AvatarProperties;
import com.scott.neptune.userserver.service.IAvatarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Objects;

import static com.scott.neptune.common.response.ServerResponse.createByErrorMessage;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 15:21
 * @Description: NeptuneBlog
 */
@Service
public class AvatarServiceImpl implements IAvatarService {

    @Resource
    private AvatarProperties avatarProps;
    @Resource
    private ImageComponent imageComponent;
    @Resource
    private FileComponent fileComponent;
    @Resource
    private IFileService fileService;

    /**
     * generateAvatar
     *
     * @param imageFile
     * @return
     */
    @Override
    public ServerResponse<List<UserAvatarDto>> generateAvatar(File imageFile) {

        if (!VerifyFileTypeUtil.isImageFile(imageFile)) {
            return createByErrorMessage("请传入支持的图片文件");
        }

        List<UserAvatarDto> avatarDtoList = Lists.newArrayListWithExpectedSize(avatarProps.getSizes().size());

        for (AvatarProperties.AvatarValueObject avatarProp : avatarProps.getSizes()) {
            File targetFile = fileComponent.createFileByExtensionName(avatarProps.getExtensionName());
            boolean resizeSuccess = imageComponent.resizeImageToSquare(imageFile,
                    ImageSize.builder().width(avatarProp.getWidth()).height(avatarProp.getHeight()).build(),
                    targetFile, avatarProps.getExtensionName());

            if (!resizeSuccess) {
                return createByErrorMessage("生成头像缩略图失败");
            }
            ServerResponse<String> uploadRes = fileService.saveFile(FileUseTypeEnum.AVATAR, targetFile, false);
            if (!Objects.isNull(targetFile) && targetFile.exists()) {
                targetFile.delete();
            }
            if (!uploadRes.isSuccess()) {
                return ServerResponse.createByErrorMessage("上传头像缩略图失败");
            }
            avatarDtoList.add(new UserAvatarDto(null, avatarProp.getSizeType(), uploadRes.getData()));
        }
        return ServerResponse.createBySuccess(avatarDtoList);
    }
}
