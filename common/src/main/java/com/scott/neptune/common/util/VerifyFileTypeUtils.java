package com.scott.neptune.common.util;

import com.scott.neptune.common.constant.FileTypeConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author scott
 * @Date 18/4/24 8:41
 * @Description 验证文件格式的工具类
 */
public class VerifyFileTypeUtils {

    private static final Logger logger = LoggerFactory.getLogger(VerifyFileTypeUtils.class);

    /**
     * 图片文件头信息与格式字符串
     */
    private final static Map<String, String> IMG_TYPE_MAP = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("ffd8ff", FileTypeConst.JPG);   //JPEG (jpg)
            put("89504e", FileTypeConst.PNG);   //PNG (png)
            put("424d22", FileTypeConst.BMP);   //16色位图(bmp)
            put("424d82", FileTypeConst.BMP);   //24位位图(bmp)
            put("424d8e", FileTypeConst.BMP);   //256色位图(bmp)
            put("49492a", FileTypeConst.TIF);   //TIFF (tif)
            put("474946", FileTypeConst.GIF);   //GIF (gif)
            put("524946", FileTypeConst.WEBP);  //WebP (webp)
        }
    };

    /**
     * Office文件头信息与格式字符串
     */
    private final static Map<String, String> OFFICE_TYPE_MAP = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("d0cf11", FileTypeConst.OFFICE);    //Office 97-2003 (doc,xls,ppt)
            put("504b03", FileTypeConst.OFFICE);    //Office (docx,xlsx,pptx)
        }
    };

    /**
     * Office文件后缀名字符串
     */
    private final static List<String> OFFICE_TYPE_EXTEND_LIST = new ArrayList<String>() {

        private static final long serialVersionUID = 1L;

        {
            add(FileTypeConst.DOC);
            add(FileTypeConst.DOCX);
            add(FileTypeConst.XLS);
            add(FileTypeConst.XLSX);
            add(FileTypeConst.PPT);
            add(FileTypeConst.PPTX);
            add(FileTypeConst.TXT);
        }
    };

    /**
     * 常用文件头信息与格式字符串
     */
    private final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("ffd8ff", FileTypeConst.JPG);       //JPEG (jpg)
            put("89504e", FileTypeConst.PNG);       //PNG (png)
            put("424d22", FileTypeConst.BMP);       //BMP 16色位图 (bmp)
            put("424d82", FileTypeConst.BMP);       //BMP 24位位图 (bmp)
            put("424d8e", FileTypeConst.BMP);       //BMP 256色位图 (bmp)
            put("49492a", FileTypeConst.TIF);       //TIFF (tif)
            put("474946", FileTypeConst.GIF);       //GIF (gif)
            put("524946", FileTypeConst.WEBP);      //WebP (webp)
            put("d0cf11", FileTypeConst.OFFICE);    //Office 97-2003 (doc,xls,ppt)
            put("504b03", FileTypeConst.OFFICE);    //Office (docx,xlsx,pptx)
            put("255044", FileTypeConst.PDF);       //PDF (pdf)
            put("000000", FileTypeConst.MP4);       //MP4 (mp4)
            //put("494433", FileTypeConst.MP3);       //MP3 (mp3)
        }
    };

    /**
     * 文件后缀与文件类型对应信息
     */
    private final static Map<String, String> TYPE_NAME_MAP = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put(FileTypeConst.DOC, "word");
            put(FileTypeConst.DOCX, "word");
            put(FileTypeConst.XLS, "excel");
            put(FileTypeConst.XLSX, "excel");
            put(FileTypeConst.PPT, "ppt");
            put(FileTypeConst.PPTX, "ppt");
            put(FileTypeConst.TXT, "txt");
        }
    };

    /**
     * 判断是否为Office文件
     *
     * @param file Office文件
     * @return 判断结果，是图片文件则返回true，否则返回false
     */
    public static boolean isOfficeFile(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] b = new byte[3];
            fileInputStream.read(b, 0, b.length);

            String fileCode = bytesToHexString(b);
            if (StringUtils.isBlank(fileCode)) {
                return false;
            }
            return OFFICE_TYPE_MAP.containsKey(fileCode.toLowerCase());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (!Objects.isNull(fileInputStream)) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否为Office文件后缀
     *
     * @param fileName Office文件名
     * @return 判断结果，是图片文件则返回true，否则返回false
     */
    public static boolean isOfficeFile(String fileName) {
        String extendName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        return OFFICE_TYPE_EXTEND_LIST.contains(extendName.toLowerCase());
    }

    /**
     * 判断是否为图片文件
     *
     * @param file 图片文件
     * @return 判断结果，是图片文件则返回true，否则返回false
     */
    public static boolean isImageFile(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] b = new byte[3];
            fileInputStream.read(b, 0, b.length);

            String fileCode = bytesToHexString(b);
            if (StringUtils.isBlank(fileCode)) {
                return false;
            }
            return IMG_TYPE_MAP.containsKey(fileCode.toLowerCase());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (!Objects.isNull(fileInputStream)) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否为支持的文件
     *
     * @param file 文件
     * @return 判断结果，是图片文件则返回true，否则返回false
     */
    public static boolean isSupportFile(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] b = new byte[3];
            fileInputStream.read(b, 0, b.length);

            String fileCode = bytesToHexString(b);
            if (StringUtils.isBlank(fileCode)) {
                return false;
            }
            return FILE_TYPE_MAP.containsKey(fileCode.toLowerCase());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (!Objects.isNull(fileInputStream)) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量判断是否为支持的文件
     *
     * @param fileList 文件列表
     * @return 判断结果，全部支持则返回null，否则返回第一个不支持的文件名
     */
    public static String isSupportFile(List<File> fileList) {
        for (File file : fileList) {
            if (!isSupportFile(file)) {
                return file.getName();
            }
        }
        return null;
    }

    /**
     * 判断是否不为图片文件
     *
     * @param file 图片文件
     * @return 判断结果，不是图片文件则返回true，否则返回false
     */
    public static boolean isNotImage(File file) {
        return !isImageFile(file);
    }

    /**
     * 根据指定的图片判断其图片类型
     *
     * @param file 指定验证的图片
     * @return 真实格式类型
     */
    public static String getImageType(File file) {
        FileInputStream fileInputStream = null;
        String realType = "";
        try {
            fileInputStream = new FileInputStream(file);
            byte[] b = new byte[3];
            fileInputStream.read(b, 0, b.length);

            String fileCode = bytesToHexString(b);
            if (StringUtils.isBlank(fileCode)) {
                return "";
            }

            for (String key : IMG_TYPE_MAP.keySet()) {
                if (key.toLowerCase().startsWith(fileCode.toLowerCase())
                        || fileCode.toLowerCase().startsWith(key.toLowerCase())) {

                    realType = IMG_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!Objects.isNull(fileInputStream)) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return realType;
    }

    /**
     * 根据指定的Office文件判断其Office类型
     *
     * @param file 指定验证的Office文件
     * @return 真实格式类型
     */
    public static String getOfficeType(File file) {
        String fileName = file.getName();
        String extensionName = fileName.substring(file.getName().lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        if (extensionName.equals(FileTypeConst.TXT) || isOfficeFile(file)) {
            return extensionName;
        } else {
            logger.error("文件 {} 不是Office文件", file.getPath());
            return "";
        }
    }

    /**
     * 根据指定的文件判断其类型
     *
     * @param file 指定验证的文件
     * @return 真实格式类型
     */
    public static String getFileType(File file) {
        String fileName = file.getName();
        String extensionName = fileName.substring(file.getName().lastIndexOf(".") + 1, fileName.length()).toLowerCase();

        if (extensionName.equals(FileTypeConst.TXT)) {
            return FileTypeConst.TXT;
        }

        FileInputStream fileInputStream = null;
        String realType = "";
        try {
            fileInputStream = new FileInputStream(file);
            byte[] b = new byte[3];
            fileInputStream.read(b, 0, b.length);

            String fileCode = bytesToHexString(b);
            if (StringUtils.isBlank(fileCode)) {
                return "";
            }

            for (String key : FILE_TYPE_MAP.keySet()) {
                if (key.toLowerCase().startsWith(fileCode.toLowerCase())
                        || fileCode.toLowerCase().startsWith(key.toLowerCase())) {
                    realType = FILE_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (FileTypeConst.OFFICE.equals(realType)) {
            realType = extensionName;
        }
        return realType;
    }

    /**
     * 字节转为十六进制字符串
     *
     * @param src 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String getTypeName(String extensionName) {
        return TYPE_NAME_MAP.getOrDefault(extensionName, "");
    }
}