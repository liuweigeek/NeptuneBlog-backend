package com.scott.neptune.userserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@EqualsAndHashCode(of = {"userId", "sizeType"})
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
     * {@link com.scott.neptune.userclient.dto.UserAvatarDto.SizeTypeEnum}
     */
    private Integer sizeType;

    /**
     * 图片URL
     */
    private String url;

}
