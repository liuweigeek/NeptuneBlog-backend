package com.scott.neptune.file.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/17 22:10
 * @Description: NeptuneBlog
 */
@Configuration
public class InitializerConfig implements ApplicationRunner {

    private final String SYS_TPM_DIR = System.getProperty("java.io.tmpdir");
    private final String SEPARATOR = File.separator;
    private final String APP_TEMP_FOLDER = "neptune_files";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initTempFolder();
    }

    /**
     * init temp folders on disk
     */
    private void initTempFolder() {
        File appTmpDir = new File(SYS_TPM_DIR + SEPARATOR + APP_TEMP_FOLDER);
        if (!appTmpDir.exists()) {
            appTmpDir.mkdirs();
        }
    }
}
