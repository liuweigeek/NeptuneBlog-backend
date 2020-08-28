package com.scott.neptune.common.listener;

import com.scott.neptune.common.property.FileProperties;
import com.scott.neptune.common.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/11/10 22:53
 * @Description: NeptuneBlog
 */
@RequiredArgsConstructor
//@ConditionalOnProperty(name = "neptune.file.tempFolder")
//@Component
public class DestroyRunner implements DisposableBean {

    private final FileProperties fileProperties;

    @Override
    public void destroy() {
        clearTempFolder();
    }

    private void clearTempFolder() {
        File appTmpDir = new File(fileProperties.getTempFolder());
        FileUtils.deleteFile(appTmpDir);
    }
}
