package com.scott.neptune.common.mapping;

import java.util.List;

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
    public abstract List<D> convertToDtoList(List<E> entityList);

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
    public abstract List<E> convertToEntityList(List<D> dtoList);
}
