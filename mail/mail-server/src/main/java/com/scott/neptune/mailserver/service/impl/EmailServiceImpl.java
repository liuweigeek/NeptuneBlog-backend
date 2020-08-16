package com.scott.neptune.mailserver.service.impl;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.mailserver.service.IEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/25 14:02
 * @Description: NeptuneBlog
 */
@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;

    @Value("${spring.mail.username}")
    private String fromAddress;

    public EmailServiceImpl(JavaMailSender mailSender, Configuration freemarkerConfig) {
        this.mailSender = mailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public ServerResponse sendSimpleMessage(String to, String subject, String content) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            log.info("send email success, to: {} subject: {}", to, subject);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.info("send email failed, to: {}, subject: {}, error info: ", to, subject, e);
            return ServerResponse.createByErrorMessage("发送失败");
        }
    }

    @Override
    public ServerResponse sendTemplateMessage(String to, String subject) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);

            Map<String, Object> model = new HashMap<>();
            model.put("username", "Scott");

            Template template = freemarkerConfig.getTemplate("welcome.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(html, true);
            mailSender.send(message);

            log.info("send email success, to: {} subject: {}", to, subject);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.info("send email failed, to: {}, subject: {}, error info: ", to, subject, e);
            return ServerResponse.createByErrorMessage("发送失败");
        }
    }
}
