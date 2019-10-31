package com.scott.neptune.file.component;

import com.scott.neptune.file.model.ImageSize;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 14:19
 * @Description: NeptuneBlog
 */
public class ImageComponentTest {

    @Resource
    private ImageComponent imageComponent;

    @Test
    public void resizeImage() {
        imageComponent = new ImageComponent();
        File originalFile = new File("/Users/scott/Pictures/mmexport1561986572192.jpg");
        File targetFile = new File("/Users/scott/Pictures/500px.png");
        boolean resizeSuccess = imageComponent.resizeImage(originalFile, targetFile,
                ImageSize.builder().height(500).width(500).build());

        Assert.assertTrue(resizeSuccess);
    }
}