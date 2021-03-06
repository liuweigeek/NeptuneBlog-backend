package com.scott.neptune.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/10/17 20:07
 * @Description: 图像尺寸配置
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageSize {

    /**
     * 图像高度
     */
    private int height;

    /**
     * 图像宽度
     */
    private int width;

}
