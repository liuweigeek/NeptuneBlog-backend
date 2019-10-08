package com.scott.neptune.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scott.neptune.userapi.entity.UserAvatarEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/5 14:30
 * @Description: NeptuneBlog
 */
public interface UserAvatarMapper extends BaseMapper<UserAvatarEntity> {

    /**
     * 根据用户ID获取全部头像
     *
     * @param userId 用户ID
     * @return 头像列表
     */
    List<UserAvatarEntity> findAllByUserId(@Param("userId") String userId);

    /**
     * 通过UserId删除全部头像
     *
     * @param avatarEntity
     * @return
     */
    boolean delete(@Param("avatar") UserAvatarEntity avatarEntity);
}
