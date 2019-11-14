package com.scott.neptune.mailserver.service;

import com.scott.neptune.common.response.ServerResponse;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/25 14:01
 * @Description: NeptuneBlog
 */
public interface IEmailService {

    ServerResponse sendSimpleMessage(String to, String subject, String content);

    ServerResponse sendTemplateMessage(String to, String subject);
}
