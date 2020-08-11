package com.scott.neptune.common.listener;

import com.scott.neptune.common.property.FileProperties;
import org.springframework.beans.factory.DisposableBean;
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

    private final FileProperties fileProperties;

    public DestroyRunner(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    @Override
    public void destroy() throws Exception {
        clearTempFolder();
    }

    private void clearTempFolder() {
        File appTmpDir = new File(fileProperties.getTempFolder());
        if (appTmpDir.exists()) {
            appTmpDir.delete();
        }
    }
}
