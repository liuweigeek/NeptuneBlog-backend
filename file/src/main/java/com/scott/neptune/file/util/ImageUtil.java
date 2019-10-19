package com.scott.neptune.file.util;

import com.scott.neptune.file.enumerate.FileTypeEnum;
import com.scott.neptune.file.model.ImageSize;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/17 20:06
 * @Description: NeptuneBlog
 */
public class ImageUtil {

    /**
     * 转换图片尺寸
     *
     * @param imageFile
     * @param imageSize
     * @return
     */
    public static File resize(File imageFile, ImageSize imageSize) {
        //TODO
        return imageFile;
    }

    /**
     * @param imageFile
     * @param fileTypeEnum
     * @return
     * @throws Exception
     */
    public static File transformat(File imageFile, FileTypeEnum fileTypeEnum) throws Exception {

        if (!fileTypeEnum.isImageFile()) {
            throw new Exception("is not a image file");
        }
        //TODO
        return imageFile;
    }
}
