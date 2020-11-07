package com.scott.neptune.userclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/8 21:39
 * @Description: NeptuneBlog
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false, of = {"userId", "sizeType"})
@AllArgsConstructor
@NoArgsConstructor
public class UserAvatarDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 图片尺寸
     *
     * @see SizeTypeEnum
     */
    private Integer sizeType;

    /**
     * 图片URL
     */
    private String url;

    @Getter
    @AllArgsConstructor
    public enum SizeTypeEnum {

        /**
         * small size
         */
        SMALL(1, "small"),

        /**
         * medimul size
         */
        MEDIUM(2, "medium"),
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
        public static SizeTypeEnum getEnum(int code) {
            return Arrays.stream(SizeTypeEnum.values())
                    .filter(sizeType -> sizeType.getCode() == code)
                    .findFirst()
                    .orElse(null);
        }

    }

}
