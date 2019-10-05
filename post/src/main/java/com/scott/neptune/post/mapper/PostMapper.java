package com.scott.neptune.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scott.neptune.postapi.entity.PostEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/5 14:30
 * @Description: NeptuneBlog
 */
public interface PostMapper extends BaseMapper<PostEntity> {

    /**
     * 判断指定条件的推文
     *
     * @param pageable
     * @param postEntity
     * @return
     */
    IPage<PostEntity> findAll(Page<PostEntity> pageable, @Param("post") PostEntity postEntity);

    /**
     * 获取关注用户的推文
     *
     * @param pageable
     * @param postEntity
     * @param fromId
     * @return
     */
    IPage<PostEntity> findAllOfFollowing(Page<PostEntity> pageable, @Param("post") PostEntity postEntity, @Param("fromId") String fromId);

    /**
     * 获取指定用户的推文
     *
     * @param userIdList
     * @param pageable
     * @return
     */
    IPage<PostEntity> findAllInUserIds(Page<PostEntity> pageable, List<String> userIdList);
}
