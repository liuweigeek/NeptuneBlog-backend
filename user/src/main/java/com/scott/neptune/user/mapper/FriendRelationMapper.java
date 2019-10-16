package com.scott.neptune.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scott.neptune.userapi.dto.UserDto;
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
     * @param page
     * @param friendRelation
     * @return
     */
    IPage<FriendRelation> findAll(Page page, @Param("relation") FriendRelation friendRelation);

    /**
     * 查看全部关注的用户
     *
     * @param page
     * @param friendRelation
     * @return
     */
    IPage<UserDto> findFollowing(Page page, @Param("relation") FriendRelation friendRelation);

    /**
     * 查看全部粉丝
     *
     * @param page
     * @param friendRelation
     * @return
     */
    IPage<UserDto> findFollower(Page page, @Param("relation") FriendRelation friendRelation);

    /**
     * 查看全部关注的用户
     *
     * @param friendRelation
     * @return
     */
    List<UserDto> findAllFollowing(@Param("relation") FriendRelation friendRelation);

    /**
     * 查看全部粉丝
     *
     * @param friendRelation
     * @return
     */
    List<UserDto> findAllFollower(@Param("relation") FriendRelation friendRelation);

}
