package com.scott.neptune.userserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scott.neptune.userapi.dto.UserDto;
import com.scott.neptune.userserver.entity.FriendRelationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/5 21:53
 * @Description: NeptuneBlog
 */
public interface FriendRelationMapper extends BaseMapper<FriendRelationEntity> {

    /**
     * 判断指定关系是否存在
     *
     * @param friendRelationEntity
     * @return
     */
    boolean exists(@Param("relation") FriendRelationEntity friendRelationEntity);

    /**
     * 删除指定关系
     *
     * @param friendRelationEntity
     * @return
     */
    boolean delete(@Param("relation") FriendRelationEntity friendRelationEntity);

    /**
     * 获取指定关系
     *
     * @param friendRelationEntity
     * @return
     */
    FriendRelationEntity getOne(@Param("relation") FriendRelationEntity friendRelationEntity);

    /**
     * 查找符合条件的关系
     *
     * @param page
     * @param friendRelationEntity
     * @return
     */
    IPage<FriendRelationEntity> findAll(Page page, @Param("relation") FriendRelationEntity friendRelationEntity);

    /**
     * 查看全部关注的用户
     *
     * @param page
     * @param friendRelationEntity
     * @return
     */
    IPage<UserDto> findFollowing(Page page, @Param("relation") FriendRelationEntity friendRelationEntity);

    /**
     * 查看全部关注者
     *
     * @param page
     * @param friendRelationEntity
     * @return
     */
    IPage<UserDto> findFollower(Page page, @Param("relation") FriendRelationEntity friendRelationEntity);

    /**
     * 查看全部关注的用户
     *
     * @param friendRelationEntity
     * @return
     */
    List<UserDto> findAllFollowing(@Param("relation") FriendRelationEntity friendRelationEntity);

    /**
     * 查看全部关注者
     *
     * @param friendRelationEntity
     * @return
     */
    List<UserDto> findAllFollower(@Param("relation") FriendRelationEntity friendRelationEntity);

}
