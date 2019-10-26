package com.scott.neptune.mail.service.impl;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.mail.MailApplicationTests;
import com.scott.neptune.mail.service.IEmailService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/25 14:12
 * @Description: NeptuneBlog
 */
public class EmailServiceImplTest extends MailApplicationTests {

    @Resource
    private IEmailService emailService;

    @Test
    public void sendSimpleMessage() {

        ServerResponse response = emailService.sendSimpleMessage("xxxxxxxx@outlook.com",
                "Hello Scott",
                "Hello Scott, This email is sent from Spring Boot");
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void sendTemplateMessage() {
        ServerResponse response = emailService.sendTemplateMessage("xxxxxxxx@outlook.com",
                "Hello Scott");
        Assert.assertTrue(response.isSuccess());
    }
}