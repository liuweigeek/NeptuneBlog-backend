package com.scott.neptune.fileserver.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/17 20:12
 * @Description: NeptuneBlog
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    /**
     * DOC
     */
    DOC("doc", new String[]{"d0cf11"}, "doc", CATEGORY.OFFICE),
    /**
     * DOCX
     */
    DOCX("docx", new String[]{"504b03"}, "docx", CATEGORY.OFFICE),
    /**
     * XLS
     */
    XLS("xls", new String[]{"d0cf11"}, "xls", CATEGORY.OFFICE),
    /**
     * XLSX
     */
    XLSX("xlsx", new String[]{"504b03"}, "xlsx", CATEGORY.OFFICE),
    /**
     * PPT
     */
    PPT("ppt", new String[]{"d0cf11"}, "ppt", CATEGORY.OFFICE),
    /**
     * PPTX
     */
    PPTX("pptx", new String[]{"504b03"}, "pptx", CATEGORY.OFFICE),
    /**
     * PDF
     */
    PDF("pdf", new String[]{"255044"}, "pdf", CATEGORY.OFFICE),
    /**
     * TXT
     */
    TXT("txt", new String[]{""}, "txt", CATEGORY.OFFICE),
    /**
     * JPG
     */
    JPG("jpg", new String[]{"ffd8ff"}, "jpg", CATEGORY.IMAGE),
    /**
     * JPEG
     */
    JPEG("jpeg", new String[]{"ffd8ff"}, "jpeg", CATEGORY.IMAGE),
    /**
     * PNG
     */
    PNG("png", new String[]{"89504e"}, "png", CATEGORY.IMAGE),
    /**
     * GIF
     */
    GIF("gif", new String[]{"474946"}, "gif", CATEGORY.IMAGE),
    /**
     * BMP
     */
    BMP("bmp", new String[]{"424d22", "424d82", "424d8e"}, "bmp", CATEGORY.IMAGE),
    /**
     * TIF
     */
    TIF("tif", new String[]{"49492a"}, "tif", CATEGORY.IMAGE),
    /**
     * WEBP
     */
    WEBP("webp", new String[]{"524946"}, "webp", CATEGORY.IMAGE),
    /**
     * MP4
     */
    MP4("mp4", new String[]{"000000"}, "mp4", CATEGORY.MEDIA),
    /**
     * MP3
     */
    MP3("mp3", new String[]{}, "mp3", CATEGORY.MEDIA);

    interface CATEGORY {
        String OFFICE = "office";
        String IMAGE = "image";
        String MEDIA = "media";
    }

    private String type;

    private String[] headCode;

    private String extensionName;

    private String category;

    public boolean isOfficeFile() {
        return StringUtils.equals(CATEGORY.OFFICE, this.category);
    }

    public boolean isImageFile() {
        return StringUtils.equals(CATEGORY.IMAGE, this.category);
    }

    public static FileTypeEnum getEnum(String type) {
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (StringUtils.equals(fileTypeEnum.getType(), type)) {
                return fileTypeEnum;
            }
        }
        return null;
    }
}
