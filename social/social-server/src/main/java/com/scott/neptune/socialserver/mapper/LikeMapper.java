package com.scott.neptune.socialserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scott.neptune.socialserver.entity.LikeEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/5 21:53
 * @Description: NeptuneBlog
 */
public interface LikeMapper extends BaseMapper<LikeEntity> {

    /**
     * 判断指定关系是否存在
     *
     * @param likeEntity
     * @return
     */
    boolean exists(@Param("like") LikeEntity likeEntity);

    /**
     * 删除指定关系
     *
     * @param likeEntity
     * @return
     */
    boolean delete(@Param("like") LikeEntity likeEntity);

    /**
     * 获取指定关系
     *
     * @param likeEntity
     * @return
     */
    LikeEntity getOne(@Param("like") LikeEntity likeEntity);

    /**
     * 查找符合条件的关系
     *
     * @param page
     * @param likeEntity
     * @return
     */
    IPage<LikeEntity> findAll(Page page, @Param("like") LikeEntity likeEntity);
}
