package com.scott.neptune.file.api.server;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.file.component.FileComponent;
import com.scott.neptune.file.enumerate.FileUseTypeEnum;
import com.scott.neptune.file.remote.client.UserClient;
import com.scott.neptune.file.service.IAvatarService;
import com.scott.neptune.file.service.IFileService;
import com.scott.neptune.userapi.dto.UserAvatarDto;
import com.scott.neptune.userapi.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/9 18:42
 * @Description: NeptuneBlog
 */
@Api(tags = "文件接口 - 面向其他服务")
@Slf4j
@RestController
@RequestMapping("/fileServer")
public class FileServerController extends BaseController {

    @Resource
    private IFileService fileService;
    @Resource
    private UserClient userClient;
    @Resource
    private FileComponent fileComponent;
    @Resource
    private IAvatarService avatarService;

    /**
     * 上传文件
     *
     * @param file      文件
     * @param useTypeId 文件用途标识 {@link FileUseTypeEnum}
     * @return
     */
    @ApiOperation(value = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form", dataTypeClass = MultipartFile.class),
            @ApiImplicitParam(name = "useTypeId", value = "业务用途ID",
                    example = "1: default, 2: avatar, 3: user background, 4: post image",
                    paramType = "form", dataType = "String")
    })
    @PostMapping(value = "/upload")
    public ServerResponse<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam(defaultValue = "1") Integer useTypeId) {

        FileUseTypeEnum fileUseTypeEnum = FileUseTypeEnum.getEnum(useTypeId);
        return fileService.saveMultipartFile(fileUseTypeEnum, file, true);
    }

    /**
     * 上传头像
     *
     * @param file 头像文件
     * @return
     */
    @ApiOperation(value = "上传头像")
    @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form", dataTypeClass = MultipartFile.class)
    @PostMapping(value = "/uploadAvatar")
    public ServerResponse<List<UserAvatarDto>> uploadAvatar(@RequestParam("file") MultipartFile file) {

        ServerResponse<UserDto> loginUserResponse = userClient.getLoginUser();
        if (!loginUserResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage(loginUserResponse.getMsg());
        }
        UserDto loginUser = loginUserResponse.getData();
        File tempFile;
        try {
            tempFile = fileComponent.transferToFile(file);
        } catch (IOException e) {
            return ServerResponse.createByErrorMessage("头像上传失败");
        }

        ServerResponse generateRes = avatarService.generateAvatar(loginUser, tempFile);
        tempFile.delete();
        return generateRes;
    }
}
