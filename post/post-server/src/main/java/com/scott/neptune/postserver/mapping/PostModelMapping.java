package com.scott.neptune.postserver.mapping;

import com.scott.neptune.common.mapping.BaseModelMapping;
import com.scott.neptune.postclient.dto.PostDto;
import com.scott.neptune.postserver.entity.PostEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
@Component
public class PostModelMapping extends BaseModelMapping<PostEntity, PostDto> {

    /**
     * convert entity to dto
     *
     * @param entity
     * @return
     */
    @Override
    public PostDto convertToDto(PostEntity entity) {
        PostDto dto = new PostDto();
        BeanUtils.copyProperties(entity, dto);
        if (!Objects.isNull(entity.getAuthor())) {
            dto.setAuthorId(entity.getAuthor().getId());
            dto.setAuthorSex(entity.getAuthor().getSex());
            dto.setAuthorUsername(entity.getAuthor().getUsername());
            dto.setAuthorNickname(entity.getAuthor().getNickname());
            dto.setAuthorAvatar(entity.getAuthor().getSmallAvatar());
        }
        return dto;
    }

    /**
     * convert dto to entity
     *
     * @param dto
     * @return
     */
    @Override
    public PostEntity convertToEntity(PostDto dto) {
        PostEntity entity = new PostEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
