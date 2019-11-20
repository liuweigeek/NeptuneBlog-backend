package com.scott.neptune.common.mapping;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:wliu@fleetup.com">scott</a>
 * @Date: 2019/10/15 09:42
 * @Description: NeptuneBlog
 */
public abstract class BaseModelMapping<E, D> {

    /**
     * convert entity to dto
     *
     * @param entity
     * @return
     */
    public abstract D convertToDto(E entity);

    /**
     * convert entity list to dto list
     *
     * @param entityList
     * @return
     */
    public List<D> convertToDtoList(List<E> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
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
    public abstract E convertToEntity(D dto);

    /**
     * convert dto list to entity list
     *
     * @param dtoList
     * @return
     */
    public List<E> convertToEntityList(List<D> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.emptyList();
        }
        return dtoList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
