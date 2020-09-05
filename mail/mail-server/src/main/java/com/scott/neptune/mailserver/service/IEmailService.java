package com.scott.neptune.mailserver.service;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/25 14:01
 * @Description: NeptuneBlog
 */
public interface IEmailService {

    void sendSimpleMessage(String to, String subject, String content);

    void sendTemplateMessage(String to, String subject);
}
