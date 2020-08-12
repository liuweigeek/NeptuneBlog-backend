package com.scott.neptune.mailserver.service.impl;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.mailserver.MailServerApplicationTests;
import com.scott.neptune.mailserver.service.IEmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/10/25 14:12
 * @Description: NeptuneBlog
 */
public class EmailServiceImplTest extends MailServerApplicationTests {

    private final IEmailService emailService;

    public EmailServiceImplTest(IEmailService emailService) {
        this.emailService = emailService;
    }

    @Test
    public void sendSimpleMessage() {

        ServerResponse response = emailService.sendSimpleMessage("xxxxxxxx@outlook.com",
                "Hello Scott",
                "Hello Scott, This email is sent from Spring Boot");
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    public void sendTemplateMessage() {
        ServerResponse response = emailService.sendTemplateMessage("xxxxxxxx@outlook.com",
                "Hello Scott");
        Assertions.assertTrue(response.isSuccess());
    }
}