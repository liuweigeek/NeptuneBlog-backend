package com.scott.neptune.post.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.entity.PostEntity;
import com.scott.neptune.post.mapper.PostMapper;
import com.scott.neptune.post.remote.server.UserServer;
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
    private UserServer userServer;

    /**
     * 保存推文
     *
     * @param postEntity 推文
     * @return 保存结果
     */
    @Override
    public ServerResponse save(PostEntity postEntity, UserDto loginUser) {

        try {
            postEntity.setUserId(loginUser.getId());
            postEntity.setCreateDate(new Date());
            postEntity.setUpdateDate(postEntity.getCreateDate());

            postMapper.insert(postEntity);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ServerResponse.createByErrorMessage(e.getMessage());
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

    @Override
    public IPage<PostEntity> findByUserId(String userId, int pageNumber, int pageSize) {
        Page<PostEntity> page = new Page<PostEntity>(pageNumber - 1, pageSize)
                .addOrder(OrderItem.desc("create_date"));
        return postMapper.findAll(page, PostEntity.builder().userId(userId).build());
    }

    @Override
    public IPage<PostEntity> findByUserIdList(List<String> userIdList, int pageNumber, int pageSize) {
        Page<PostEntity> page = new Page<PostEntity>(pageNumber - 1, pageSize)
                .addOrder(OrderItem.desc("create_date"));
        return postMapper.findAllInUserIds(page, userIdList);
    }

    @Override
    public IPage<PostEntity> findByFollowerId(String followerId, int pageNumber, int pageSize) {
        Page<PostEntity> page = new Page<PostEntity>(pageNumber - 1, pageSize)
                .addOrder(OrderItem.desc("create_date"));
        return postMapper.findAllOfFollowing(page, null, followerId);
    }

}
