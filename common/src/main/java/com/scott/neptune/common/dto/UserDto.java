package com.scott.neptune.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends PageDto {

    private String id;

    private String username;

    private String realName;

    private String password;

    private Integer age;

    private Integer sex;

    private Date registerDate;

    private Date loginDate;

    private String token;

}
