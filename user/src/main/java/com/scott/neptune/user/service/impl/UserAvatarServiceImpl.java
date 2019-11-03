package com.scott.neptune.user.service.impl;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.user.entity.UserAvatarEntity;
import com.scott.neptune.user.mapper.UserAvatarMapper;
import com.scott.neptune.user.service.IUserAvatarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/11/3 02:13
 * @Description: NeptuneBlog
 */
@Slf4j
@Transactional
@Service
public class UserAvatarServiceImpl implements IUserAvatarService {

    @Resource
    private UserAvatarMapper userAvatarMapper;

    @Override
    public ServerResponse<UserAvatarEntity> save(UserAvatarEntity avatarEntity) {
        try {
            userAvatarMapper.insert(avatarEntity);
            return ServerResponse.createBySuccess(avatarEntity);
        } catch (Exception e) {
            log.error("save user avatar failed: ", e);
            return ServerResponse.createByErrorMessage("保存头像失败");
        }
    }

    @Override
    public ServerResponse<List<UserAvatarEntity>> saveList(List<UserAvatarEntity> avatarEntityList) {
        try {
            userAvatarMapper.insertBatch(avatarEntityList);
            return ServerResponse.createBySuccess(avatarEntityList);
        } catch (Exception e) {
            log.error("save user avatar failed: ", e);
            return ServerResponse.createByErrorMessage("保存头像失败");
        }
    }

    @Override
    public boolean delete(UserAvatarEntity userAvatarEntity) {
        return userAvatarMapper.delete(userAvatarEntity);
    }
}
