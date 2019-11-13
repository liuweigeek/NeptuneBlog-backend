package com.scott.neptune.fileserver.api.server;

import com.scott.neptune.common.controller.BaseController;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.fileserver.component.FileComponent;
import com.scott.neptune.fileserver.enumerate.FileUseTypeEnum;
import com.scott.neptune.fileserver.service.IAvatarService;
import com.scott.neptune.fileserver.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ServerResponse<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam(defaultValue = "1") Integer useTypeId) {

        FileUseTypeEnum fileUseTypeEnum = FileUseTypeEnum.getEnum(useTypeId);
        return fileService.saveMultipartFile(fileUseTypeEnum, file, true);
    }

    /**
     * 上传头像
     *
     * @return
     */
    @ApiOperation(value = "上传头像")
    @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form", dataTypeClass = MultipartFile.class)
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public ServerResponse uploadAvatar(@RequestParam("file") MultipartFile file) {

        if (Objects.isNull(file)) {
            return ServerResponse.createByErrorMessage("文件不可为空");
        }
        File tempFile = null;
        try {
            tempFile = fileComponent.transferToFile(file);
            return avatarService.generateAvatar(tempFile);
        } catch (IOException e) {
            return ServerResponse.createByErrorMessage("头像上传失败");
        } finally {
            if (!Objects.isNull(tempFile) && tempFile.exists()) {
                tempFile.delete();
            }
        }

    }
}
