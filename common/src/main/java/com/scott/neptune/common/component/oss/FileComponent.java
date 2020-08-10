package com.scott.neptune.common.component.oss;

import com.scott.neptune.common.property.FileProps;
import com.scott.neptune.common.util.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/29 17:49
 * @Description: NeptuneBlog
 */
@Component
public class FileComponent {

    private static final String SEPARATOR = File.separator;

    private final FileProps fileProps;

    public FileComponent(FileProps fileProps) {
        this.fileProps = fileProps;
    }

    public File createFileByFilename(String filename) {
        File file = new File(fileProps.getTempFolder() + SEPARATOR + filename);
        return file;
    }

    public File createFileByExtensionName(String extensionName) {
        File file = new File(fileProps.getTempFolder() + SEPARATOR + FileUtils.getRandomFileName(extensionName));
        return file;
    }

    public File transferToFile(MultipartFile multipartFile) throws IOException {
        File tempFile = new File(fileProps.getTempFolder() + File.separator + FileUtils.getRandomNameByOriginName(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(tempFile);
        if (tempFile.exists()) {
            return tempFile;
        } else {
            throw new IOException("transfer file exception");
        }
    }
}
