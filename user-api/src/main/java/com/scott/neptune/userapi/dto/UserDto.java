package com.scott.neptune.userapi.dto;

import com.scott.neptune.common.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:39
 * @Description: UserDto对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDto {

    /**
     * Id
     */
    private String id;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 注册时间
     */
    private Date registerDate;

    /**
     * 最近登录时间
     */
    private Date loginDate;

    /**
     * 语言
     */
    private String langKey;

    /**
     * 登录Token信息
     */
    private String token;

}
