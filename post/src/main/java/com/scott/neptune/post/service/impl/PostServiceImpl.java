package com.scott.neptune.post.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.entity.PostEntity;
import com.scott.neptune.post.mapper.PostMapper;
import com.scott.neptune.post.remote.client.UserClient;
import com.scott.neptune.post.service.IPostService;
import com.scott.neptune.userapi.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author scott
 */
@Slf4j
@Service
public class PostServiceImpl implements IPostService {

    @Resource
    private PostMapper postMapper;
    @Resource
    private UserClient userClient;

    /**
     * 保存推文
     *
     * @param postEntity 推文
     * @return 保存结果
     */
    @Override
    public ServerResponse<PostEntity> save(PostEntity postEntity, UserDto loginUser) {

        try {
            postEntity.setUserId(loginUser.getId());
            postEntity.setCreateDate(new Date());
            postEntity.setUpdateDate(postEntity.getCreateDate());

            postMapper.insert(postEntity);
            return ServerResponse.createBySuccess(postEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ServerResponse.createByErrorMessage("发送失败");
        }
    }

    /**
     * 删除推文
     *
     * @param postEntity 推文
     * @return 删除结果
     */
    @Override
    public boolean delete(PostEntity postEntity) {

        try {
            postMapper.deleteById(postEntity.getId());
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

    }

    /**
     * 获取指定用户的推文
     *
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public IPage<PostEntity> findByUserId(String userId, int pageNumber, int pageSize) {
        Page<PostEntity> page = new Page<PostEntity>(pageNumber - 1, pageSize);
        return postMapper.findAll(page, PostEntity.builder().userId(userId).build());
    }

    /**
     * 根据用户ID列表获取对应推文
     *
     * @param userIdList
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public IPage<PostEntity> findByUserIdList(List<String> userIdList, int pageNumber, int pageSize) {
        Page<PostEntity> page = new Page<PostEntity>(pageNumber - 1, pageSize);
        return postMapper.findAllInUserIds(page, userIdList);
    }

    /**
     * 获取关注用户的推文
     *
     * @param followerId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public IPage<PostEntity> findByFollowerId(String followerId, int pageNumber, int pageSize) {
        Page<PostEntity> page = new Page<PostEntity>(pageNumber - 1, pageSize);
        return postMapper.findAllOfFollowing(page, null, followerId);
    }

    /**
     * 根据关键字查找推文
     *
     * @param keyword
     * @return
     */
    @Override
    public List<PostEntity> findByKeyword(String keyword) {
        return postMapper.findByKeyword(keyword);
    }

}
