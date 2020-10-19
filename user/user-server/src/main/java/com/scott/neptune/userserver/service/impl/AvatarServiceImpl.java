package com.scott.neptune.userserver.service.impl;

import com.scott.neptune.common.component.FileComponent;
import com.scott.neptune.common.component.ImageComponent;
import com.scott.neptune.common.exception.NeptuneBlogException;
import com.scott.neptune.common.model.ImageSize;
import com.scott.neptune.common.property.FileProperties;
import com.scott.neptune.common.util.FileUtils;
import com.scott.neptune.common.util.VerifyFileTypeUtils;
import com.scott.neptune.userclient.dto.UserDto;
import com.scott.neptune.userserver.convertor.UserConvertor;
import com.scott.neptune.userserver.domain.entity.UserEntity;
import com.scott.neptune.userserver.domain.valueobject.UserAvatarValObj;
import com.scott.neptune.userserver.model.UserAvatarFactory;
import com.scott.neptune.userserver.property.AvatarProperties;
import com.scott.neptune.userserver.repository.UserRepository;
import com.scott.neptune.userserver.service.IAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/29 15:21
 * @Description: NeptuneBlog
 */
@RequiredArgsConstructor
@Service
public class AvatarServiceImpl implements IAvatarService {

    private final UserRepository userRepository;
    private final UserConvertor userConvertor;
    private final FileComponent fileComponent;
    private final ImageComponent imageComponent;
    private final FileProperties fileProperties;
    private final AvatarProperties avatarProperties;

    @Override
    public UserDto generateAvatar(Long userId, MultipartFile imageFile) {
        File avatarFile = FileUtils.transferToFile(fileProperties.getTempFolder(), imageFile);
        UserDto userDto = generateAvatar(userId, avatarFile);
        FileUtils.deleteFile(avatarFile);
        return userDto;
    }

    /**
     * Generate Avatar
     *
     * @param userId
     * @param imageFile
     * @return
     */
    @Override
    public UserDto generateAvatar(Long userId, File imageFile) {

        if (!VerifyFileTypeUtils.isImageFile(imageFile)) {
            throw new NeptuneBlogException("图片格式不支持");
        }
        UserEntity userEntity = Optional.of(userRepository.getOne(userId))
                .orElseThrow(() -> new NeptuneBlogException("用户不存在"));

        UserAvatarValObj userAvatarValObj = new UserAvatarValObj();

        Stream.of(avatarProperties.getSmall(), avatarProperties.getMedium(), avatarProperties.getLarge())
                .forEach(sizeValObj -> {
                    File targetFile = FileUtils.createFileByExtension(fileProperties.getTempFolder(), avatarProperties.getExtension());
                    boolean resizeSuccess = imageComponent.resizeImageToSquare(imageFile, targetFile,
                            ImageSize.builder().width(sizeValObj.getWidth()).height(sizeValObj.getHeight()).build(),
                            avatarProperties.getExtension());
                    if (!resizeSuccess) {
                        throw new NeptuneBlogException("生成头像缩略图失败");
                    }
                    String avatarUrl = fileComponent.saveFile(UserAvatarFactory.buildFolder(userId, sizeValObj.getName()), targetFile, targetFile.getName());
                    FileUtils.deleteFile(targetFile);
                    setAvatar(userAvatarValObj, sizeValObj, avatarUrl);
                });

        userEntity.setUserAvatarValObj(userAvatarValObj);
        userRepository.save(userEntity);

        return userConvertor.convertToDto(userEntity);
    }

    private void setAvatar(UserAvatarValObj userAvatarValObj, AvatarProperties.AvatarSizeValObj sizeValObj, String url) {
        switch (sizeValObj.getName()) {
            case "small":
                userAvatarValObj.setSmallAvatarUrl(url);
                break;
            case "medium":
                userAvatarValObj.setMediumAvatarUrl(url);
                break;
            case "large":
                userAvatarValObj.setLargeAvatarUrl(url);
                break;
        }
    }

}
