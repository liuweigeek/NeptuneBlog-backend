package com.scott.neptune.common.component;

import com.scott.neptune.common.model.ImageSize;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 14:13
 * @Description: NeptuneBlog
 */
@Slf4j
@Component
public class ImageComponent {

    public boolean resizeImage(File originalImage, File toFile, ImageSize size, String extension) {

        try {
            BufferedImage image = ImageIO.read(originalImage);
            Thumbnails.of(originalImage)
                    .sourceRegion(Positions.CENTER, image.getWidth(), image.getHeight())
                    .size(size.getWidth(), size.getHeight())
                    .outputQuality(1.0f)
                    .outputFormat(extension)
                    .toFile(toFile);
            return true;
        } catch (Exception e) {
            log.error("resize image exception: ", e);
            return false;
        }
    }

    public boolean resizeImageToSquare(File originalImage, File toFile, ImageSize size, String extension) {

        try {
            BufferedImage image = ImageIO.read(originalImage);
            int sideLength = Math.min(image.getHeight(), image.getWidth());
            Thumbnails.of(originalImage)
                    .sourceRegion(Positions.CENTER, sideLength, sideLength)
                    .size(size.getWidth(), size.getHeight())
                    .outputQuality(1.0f)
                    .outputFormat(extension)
                    .toFile(toFile);
            return true;
        } catch (Exception e) {
            log.error("resize image exception: ", e);
            return false;
        }
    }
}
