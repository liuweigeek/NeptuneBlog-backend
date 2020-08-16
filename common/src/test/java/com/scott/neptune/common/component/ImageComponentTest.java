package com.scott.neptune.common.component;

import com.scott.neptune.common.model.ImageSize;
import com.scott.neptune.common.util.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/29 14:19
 * @Description: NeptuneBlog
 */
public class ImageComponentTest {

    private final ImageComponent imageComponent;

    public ImageComponentTest(ImageComponent imageComponent) {
        this.imageComponent = imageComponent;
    }

    @Test
    public void resizeImage() {
        File originalFile = new File("/Users/scott/Pictures/mmexport1561986572192.jpg");
        File targetFile = new File("/Users/scott/Pictures/500px.png");
        boolean resizeSuccess = imageComponent.resizeImage(originalFile, targetFile,
                ImageSize.builder().height(500).width(500).build(), FileUtils.getExtension(targetFile.getName()));

        Assertions.assertTrue(resizeSuccess);
    }
}