package com.scott.neptune.common.listener;

import com.scott.neptune.common.property.FileProperties;
import com.scott.neptune.common.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/11/10 22:53
 * @Description: NeptuneBlog
 */
@RequiredArgsConstructor
@ConditionalOnProperty(name = "neptune.file.tempFolder")
@Component
public class DestroyRunner implements ApplicationListener<ContextClosedEvent> {

    private final FileProperties fileProperties;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        clearTempFolder();
    }

    private void clearTempFolder() {
        File appTmpDir = new File(fileProperties.getTempFolder());
        FileUtils.deleteFile(appTmpDir);
    }
}
