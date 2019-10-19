package com.scott.neptune.file.api.server;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.file.enumerate.FileUseTypeEnum;
import com.scott.neptune.file.service.IFileService;
import com.scott.neptune.userapi.entity.UserAvatarEntity;
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
public class FileServerController {

    @Resource
    private IFileService fileService;

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
        return fileService.saveFile(fileUseTypeEnum, file, true);
    }

    /**
     * 头像
     *
     * @param file
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
    public ServerResponse<List<UserAvatarEntity>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        //TODO
        return ServerResponse.createBySuccess();
    }
}
