package com.scott.neptune.common.component.oss;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/11/10 22:53
 * @Description: NeptuneBlog
 */
@Component
public class DestroyRunner implements DisposableBean {

    @Value("${neptune.file.tempFolder}")
    private String tempFolder;

    @Override
    public void destroy() throws Exception {
        clearTempFolder();
    }

    private void clearTempFolder() {
        File appTmpDir = new File(tempFolder);
        if (appTmpDir.exists()) {
            appTmpDir.delete();
        }
    }
}
