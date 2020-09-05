package com.scott.neptune.mailserver.service.impl;

import com.scott.neptune.mailserver.MailServerApplicationTests;
import com.scott.neptune.mailserver.service.IEmailService;
import org.junit.jupiter.api.Test;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
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

        emailService.sendSimpleMessage("xxxxxxxx@outlook.com",
                "Hello Scott",
                "Hello Scott, This email is sent from Spring Boot");
    }

    @Test
    public void sendTemplateMessage() {
        emailService.sendTemplateMessage("xxxxxxxx@outlook.com",
                "Hello Scott");
    }
}