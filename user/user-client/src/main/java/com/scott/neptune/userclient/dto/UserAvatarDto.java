package com.scott.neptune.userclient.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/8 21:39
 * @Description: NeptuneBlog
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false, of = {"userId", "sizeType"})
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "userAvatar", description = "用户头像")
public class UserAvatarDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 图片尺寸
     * {@link SizeTypeEnum}
     */
    @ApiModelProperty(value = "图片尺寸")
    private Integer sizeType;

    /**
     * 图片URL
     */
    @ApiModelProperty(value = "图片URL")
    private String url;

    @Getter
    @AllArgsConstructor
    public enum SizeTypeEnum {

        /**
         * small size
         */
        SMALL(1, "small"),

        /**
         * normal size
         */
        NORMAL(2, "normal"),
        /**
         * large size
         */
        LARGE(3, "large");

        private final int code;

        private final String name;

        /**
         * get enum instance from code
         *
         * @param code
         * @return
         */
        public SizeTypeEnum getEnum(int code) {
            for (SizeTypeEnum sizeTypeEnum : SizeTypeEnum.values()) {
                if (sizeTypeEnum.getCode() == code) {
                    return sizeTypeEnum;
                }
            }
            return null;
        }

    }

}
