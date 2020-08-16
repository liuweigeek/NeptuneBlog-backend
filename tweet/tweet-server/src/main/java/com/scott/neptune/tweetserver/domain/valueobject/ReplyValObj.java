package com.scott.neptune.tweetserver.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/8 21:39
 * @Description: NeptuneBlog
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReplyValObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "in_reply_to_status_id")
    private Long inReplyToStatusId;

    @Column(name = "in_reply_to_user_id")
    private Long inReplyToUserId;

    @Column(name = "in_reply_to_screen_name")
    private String inReplyToScreenName;

}
