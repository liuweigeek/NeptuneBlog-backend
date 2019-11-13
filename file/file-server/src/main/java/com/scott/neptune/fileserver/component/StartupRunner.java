package com.scott.neptune.fileserver.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/17 22:10
 * @Description: NeptuneBlog
 */
@Component
public class StartupRunner implements ApplicationRunner {

    @Value("${file.tempFolder}")
    private String tempFolder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initTempFolder();
    }

    /**
     * init temp folders on disk
     */
    private void initTempFolder() {
        File appTmpDir = new File(tempFolder);
        if (!appTmpDir.exists()) {
            appTmpDir.mkdirs();
        }
    }
}
