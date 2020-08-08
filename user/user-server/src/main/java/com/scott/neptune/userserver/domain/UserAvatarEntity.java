package com.scott.neptune.userserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/8 21:39
 * @Description: NeptuneBlog
 */
@Data
@Builder
@EqualsAndHashCode(of = {"userId", "sizeType"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user_avatar")
public class UserAvatarEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 图片尺寸
     * {@link com.scott.neptune.userclient.dto.UserAvatarDto.SizeTypeEnum}
     */
    @Column(name = "size_type")
    private Integer sizeType;

    /**
     * 图片URL
     */
    @Column(name = "url")
    private String url;

}
