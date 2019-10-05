package com.scott.neptune.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scott.neptune.userapi.entity.FriendRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/5 21:53
 * @Description: NeptuneBlog
 */
public interface FriendRelationMapper extends BaseMapper<FriendRelation> {

    /**
     * 判断指定关系是否存在
     *
     * @param friendRelation
     * @return
     */
    boolean exists(@Param("relation") FriendRelation friendRelation);

    /**
     * 删除指定关系
     *
     * @param friendRelation
     * @return
     */
    boolean delete(@Param("relation") FriendRelation friendRelation);

    /**
     * 获取指定关系
     *
     * @param friendRelation
     * @return
     */
    FriendRelation getOne(@Param("relation") FriendRelation friendRelation);

    /**
     * 查找符合条件的关系
     *
     * @param friendRelation
     * @return
     */
    List<FriendRelation> findAll(@Param("relation") FriendRelation friendRelation);

}
