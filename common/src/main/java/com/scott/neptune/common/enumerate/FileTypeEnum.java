package com.scott.neptune.common.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/17 20:12
 * @Description: NeptuneBlog
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    /**
     * DOC
     */
    DOC("doc", new String[]{"d0cf11"}, "doc", Category.OFFICE),
    /**
     * DOCX
     */
    DOCX("docx", new String[]{"504b03"}, "docx", Category.OFFICE),
    /**
     * XLS
     */
    XLS("xls", new String[]{"d0cf11"}, "xls", Category.OFFICE),
    /**
     * XLSX
     */
    XLSX("xlsx", new String[]{"504b03"}, "xlsx", Category.OFFICE),
    /**
     * PPT
     */
    PPT("ppt", new String[]{"d0cf11"}, "ppt", Category.OFFICE),
    /**
     * PPTX
     */
    PPTX("pptx", new String[]{"504b03"}, "pptx", Category.OFFICE),
    /**
     * PDF
     */
    PDF("pdf", new String[]{"255044"}, "pdf", Category.OFFICE),
    /**
     * TXT
     */
    TXT("txt", new String[]{""}, "txt", Category.OFFICE),
    /**
     * JPG
     */
    JPG("jpg", new String[]{"ffd8ff"}, "jpg", Category.IMAGE),
    /**
     * JPEG
     */
    JPEG("jpeg", new String[]{"ffd8ff"}, "jpeg", Category.IMAGE),
    /**
     * PNG
     */
    PNG("png", new String[]{"89504e"}, "png", Category.IMAGE),
    /**
     * GIF
     */
    GIF("gif", new String[]{"474946"}, "gif", Category.IMAGE),
    /**
     * BMP
     */
    BMP("bmp", new String[]{"424d22", "424d82", "424d8e"}, "bmp", Category.IMAGE),
    /**
     * TIF
     */
    TIF("tif", new String[]{"49492a"}, "tif", Category.IMAGE),
    /**
     * WEBP
     */
    WEBP("webp", new String[]{"524946"}, "webp", Category.IMAGE),
    /**
     * MP4
     */
    MP4("mp4", new String[]{"000000"}, "mp4", Category.MEDIA),
    /**
     * MP3
     */
    MP3("mp3", new String[]{}, "mp3", Category.MEDIA);

    private final String type;
    private final String[] headCode;
    private final String extensionName;
    private final String category;

    public static FileTypeEnum getEnum(String type) {
        return Arrays.stream(FileTypeEnum.values())
                .filter(fileType -> StringUtils.equals(fileType.getType(), type))
                .findFirst()
                .orElse(null);
    }

    public boolean isOfficeFile() {
        return StringUtils.equals(Category.OFFICE, this.category);
    }

    public boolean isImageFile() {
        return StringUtils.equals(Category.IMAGE, this.category);
    }

    /**
     * file category
     */
    interface Category {
        String OFFICE = "office";
        String IMAGE = "image";
        String MEDIA = "media";
    }
}
