package com.scott.neptune.common.listener;

import com.scott.neptune.common.property.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/17 22:10
 * @Description: NeptuneBlog
 */
@RequiredArgsConstructor
//@ConditionalOnProperty(name = "neptune.file.tempFolder")
//@Component
public class StartupRunner implements ApplicationRunner {

    private final FileProperties fileProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initTempFolder();
    }

    /**
     * init temp folders on disk
     */
    private void initTempFolder() {
        File appTmpDir = new File(fileProperties.getTempFolder());
        if (!appTmpDir.exists()) {
            appTmpDir.mkdirs();
        }
    }
}
