package com.scott.neptune.post.mapping;

import com.scott.neptune.common.mapping.BaseModelMapping;
import com.scott.neptune.post.entity.PostEntity;
import com.scott.neptune.postapi.dto.PostDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * convert entity list to dto list
     *
     * @param entityList
     * @return
     */
    @Override
    public List<PostDto> convertToDtoList(List<PostEntity> entityList) {
        return entityList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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

    /**
     * convert dto list to entity list
     *
     * @param dtoList
     * @return
     */
    @Override
    public List<PostEntity> convertToEntityList(List<PostDto> dtoList) {
        return dtoList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
