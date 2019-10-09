package com.scott.neptune.userapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;

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
@ApiModel(value = "userAvatar", description = "用户头像")
public class UserAvatarEntity {

    private String userId;

    private Integer size;

    private String url;

    @Getter
    @AllArgsConstructor
    public enum SizeEnum {

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

        private int code;

        private String name;

        /**
         * get enum instance from code
         *
         * @param code
         * @return
         */
        public SizeEnum getEnum(int code) {
            for (SizeEnum sizeEnum : SizeEnum.values()) {
                if (sizeEnum.getCode() == code) {
                    return sizeEnum;
                }
            }
            return null;
        }

    }
}
