package com.scott.neptune.post.service.impl;

import com.scott.neptune.common.response.ServerResponse;
import com.scott.neptune.post.entity.Post;
import com.scott.neptune.post.feignclient.UserClient;
import com.scott.neptune.post.repository.PostRepository;
import com.scott.neptune.post.service.IPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements IPostService {

    @Resource
    private PostRepository postRepository;
    @Resource
    private UserClient userClient;

    /**
     * 保存推文
     *
     * @param post 推文
     * @return 保存结果
     */
    @Override
    public ServerResponse save(Post post) {

        try {
            postRepository.save(post);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

    /**
     * 删除推文
     *
     * @param post 推文
     * @return 删除结果
     */
    @Override
    public boolean delete(Post post) {

        try {
            postRepository.delete(post);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

    }

    @Override
    public Page<Post> findByUserId(String userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createDate"));
        return postRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public Page<Post> findByUserIdList(List<String> userIdList, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createDate"));
        return postRepository.findAllByUserIdIn(userIdList, pageable);
    }

}
