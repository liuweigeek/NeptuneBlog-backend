package com.scott.neptune.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/8 21:39
 * @Description: NeptuneBlog
 */
@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"userId", "size"})
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user_avatar")
public class UserAvatarEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 图片尺寸
     * {@link com.scott.neptune.userapi.dto.UserAvatarDto.SizeTypeEnum}
     */
    private Integer sizeType;

    /**
     * 图片URL
     */
    private String url;

}
