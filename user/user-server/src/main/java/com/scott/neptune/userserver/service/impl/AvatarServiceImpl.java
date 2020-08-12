package com.scott.neptune.userserver.service.impl;

import com.google.common.collect.Lists;
import com.scott.neptune.common.base.BaseStorageInfo;
import com.scott.neptune.common.component.FileComponent;
import com.scott.neptune.common.component.ImageComponent;
import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.model.ImageSize;
import com.scott.neptune.common.property.FileProperties;
import com.scott.neptune.common.util.FileUtils;
import com.scott.neptune.common.util.VerifyFileTypeUtils;
import com.scott.neptune.userclient.dto.UserAvatarDto;
import com.scott.neptune.userserver.model.UserAvatarStorageInfo;
import com.scott.neptune.userserver.property.AvatarProperties;
import com.scott.neptune.userserver.service.IAvatarService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/10/29 15:21
 * @Description: NeptuneBlog
 */
@Service
public class AvatarServiceImpl implements IAvatarService {

    private final FileComponent fileComponent;
    private final ImageComponent imageComponent;
    private final FileProperties fileProperties;
    private final AvatarProperties avatarProperties;

    public AvatarServiceImpl(FileComponent fileComponent,
                             ImageComponent imageComponent,
                             FileProperties fileProperties,
                             AvatarProperties avatarProperties) {

        this.fileComponent = fileComponent;
        this.imageComponent = imageComponent;
        this.fileProperties = fileProperties;
        this.avatarProperties = avatarProperties;
    }

    @Override
    public List<UserAvatarDto> generateAvatar(Long userId, MultipartFile imageFile) {
        File avatarFile = FileUtils.transferToFile(fileProperties.getTempFolder(), imageFile);
        return generateAvatar(userId, avatarFile);
    }

    /**
     * generateAvatar
     *
     * @param imageFile
     * @return
     */
    //TODO optimize
    @Override
    public List<UserAvatarDto> generateAvatar(Long userId, File imageFile) {

        if (!VerifyFileTypeUtils.isImageFile(imageFile)) {
            throw new NeptuneBlogException("图片格式不支持");
        }

        List<UserAvatarDto> avatarDtoList = Lists.newArrayListWithExpectedSize(avatarProperties.getSizes().size());

        for (AvatarProperties.AvatarSizeValueObject sizeValueObject : avatarProperties.getSizes()) {
            File targetFile = FileUtils.createFileByExtension(fileProperties.getTempFolder(), avatarProperties.getExtension());
            boolean resizeSuccess = imageComponent.resizeImageToSquare(imageFile, targetFile,
                    ImageSize.builder().width(sizeValueObject.getWidth()).height(sizeValueObject.getHeight()).build(),
                    avatarProperties.getExtension());
            if (!resizeSuccess) {
                throw new NeptuneBlogException("生成头像缩略图失败");
            }
            BaseStorageInfo avatarStorageInfo = new UserAvatarStorageInfo(userId, sizeValueObject.getSizeName());
            String avatarUrl = fileComponent.saveFile(avatarStorageInfo, targetFile, targetFile.getName());
            if (targetFile.exists()) {
                targetFile.delete();
            }
            avatarDtoList.add(new UserAvatarDto(userId, sizeValueObject.getSizeType(), avatarUrl));
        }
        return avatarDtoList;
    }
}
