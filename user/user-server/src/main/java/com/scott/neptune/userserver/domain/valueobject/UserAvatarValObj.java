package com.scott.neptune.userserver.domain.valueobject;

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
public class UserAvatarValObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "small_avatar_url")
    private String smallAvatarUrl;

    @Column(name = "medium_avatar_url")
    private String mediumAvatarUrl;

    @Column(name = "large_avatar_url")
    private String largeAvatarUrl;

}
