package com.scott.neptune.common.listener;

import com.scott.neptune.common.property.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/17 22:10
 * @Description: NeptuneBlog
 */
@RequiredArgsConstructor
@ConditionalOnProperty(name = "neptune.file.tempFolder")
@Component
public class StartupRunner implements ApplicationListener<ApplicationStartedEvent> {

    private final FileProperties fileProperties;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
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
