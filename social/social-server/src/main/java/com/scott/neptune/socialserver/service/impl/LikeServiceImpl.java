package com.scott.neptune.socialserver.service.impl;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.socialserver.entity.LikeEntity;
import com.scott.neptune.socialserver.mapper.LikeMapper;
import com.scott.neptune.socialserver.service.ILikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2020/1/5 14:27
 * @Description:
 */
@Slf4j
@Transactional
@Service
public class LikeServiceImpl implements ILikeService {

    @Resource
    private LikeMapper likeMapper;

    /**
     * 保存点赞
     *
     * @param likeEntity 点赞
     * @return 保存结果
     */
    @Override
    public ServerResponse save(LikeEntity likeEntity) {

        if (likeMapper.exists(likeEntity)) {
            return ServerResponse.createBySuccess();
        }
        try {
            likeEntity.setCreateDate(new Date());
            likeMapper.insert(likeEntity);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ServerResponse.createByErrorMessage("点赞失败");
        }

    }

    /**
     * 获取点赞关系
     *
     * @param userId
     * @param postId
     * @return
     */
    @Override
    public LikeEntity getLikeByUserIdAndPostId(String userId, String postId) {
        return likeMapper.getOne(LikeEntity.builder().userId(userId).postId(postId).build());
    }

    /**
     * 删除点赞
     *
     * @param likeEntity 点赞关系
     * @return 删除结果
     */
    @Override
    public boolean delete(LikeEntity likeEntity) {

        try {
            likeMapper.delete(likeEntity);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

    }

    /**
     * 根据用户ID和推文ID删除点赞
     *
     * @param userId 用户ID
     * @param postId 推文ID
     * @return
     */
    @Override
    public boolean deleteByUserIdAndPostId(String userId, String postId) {
        try {
            likeMapper.delete(LikeEntity.builder().userId(userId).postId(postId).build());
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
