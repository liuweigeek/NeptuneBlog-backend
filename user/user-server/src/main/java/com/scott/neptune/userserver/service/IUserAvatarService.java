package com.scott.neptune.userserver.service;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.userserver.entity.UserAvatarEntity;

import java.util.List;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/23 09:40
 * @Description: IUserService
 */
public interface IUserAvatarService {

    ServerResponse<UserAvatarEntity> save(UserAvatarEntity avatarEntity);

    ServerResponse<List<UserAvatarEntity>> saveList(List<UserAvatarEntity> avatarEntityList);

    boolean delete(UserAvatarEntity userAvatarEntity);

}
