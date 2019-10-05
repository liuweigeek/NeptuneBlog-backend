package com.scott.neptune.postapi.mapping;

import com.scott.neptune.postapi.dto.PostDto;
import com.scott.neptune.postapi.entity.PostEntity;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/9/30 21:53
 * @Description: NeptuneBlog
 */
public class PostModelMapping {

    public static PostDto convertToDto(PostEntity postEntity) {
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(postEntity, postDto);
        if (!Objects.isNull(postEntity.getAuthor())) {
            postDto.setAuthorId(postEntity.getAuthor().getId());
            postDto.setAuthorSex(postEntity.getAuthor().getSex());
            postDto.setAuthorUsername(postEntity.getAuthor().getUsername());
        }
        return postDto;

    }

    public static List<PostDto> convertToDtoList(List<PostEntity> postEntityList) {
        return postEntityList.stream()
                .map(PostModelMapping::convertToDto)
                .collect(Collectors.toList());
    }
}
