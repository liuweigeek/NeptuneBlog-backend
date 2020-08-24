package com.scott.neptune.common.base;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/3 21:05
 * @Description:
 */
public abstract class BaseConvertor<ENTITY, DTO> {

    /**
     * convert to DTO
     *
     * @return
     */
    public Function<ENTITY, DTO> convertToDto() {
        return functionConvertToDto();
    }

    /**
     * convert to Entity
     *
     * @return
     */
    public Function<DTO, ENTITY> convertToEntity() {
        return functionConvertToEntity();
    }

    /**
     * convert to DTO
     *
     * @return
     */
    public DTO convertToDto(ENTITY entity) {
        return convertToDto().apply(entity);
    }

    /**
     * convert to Entity
     *
     * @return
     */
    public ENTITY convertToEntity(DTO dto) {
        return convertToEntity().apply(dto);
    }


    /**
     * convert to DTO list
     *
     * @return
     */
    public Function<Collection<ENTITY>, Collection<DTO>> convertToDtoList() {
        return functionConvertToDtoList();
    }

    /**
     * convert to Entity list
     *
     * @return
     */
    public Function<Collection<DTO>, Collection<ENTITY>> convertToEntityList() {
        return functionConvertToEntityList();
    }

    /**
     * convert to DTO list
     *
     * @return
     */
    public Collection<DTO> convertToDtoList(Collection<ENTITY> entity) {
        return convertToDtoList().apply(entity);
    }

    /**
     * convert to Entity list
     *
     * @return
     */
    public Collection<ENTITY> convertToEntityList(Collection<DTO> dto) {
        return convertToEntityList().apply(dto);
    }


    /**
     * get a function implement to convert an entity to a dto
     *
     * @return
     */
    protected abstract Function<ENTITY, DTO> functionConvertToDto();

    /**
     * get a function implement to convert a dto to aentity
     *
     * @return
     */
    protected abstract Function<DTO, ENTITY> functionConvertToEntity();

    /**
     * get a function implement to convert several of enties to dtos
     *
     * @return
     */
    protected Function<Collection<ENTITY>, Collection<DTO>> functionConvertToDtoList() {
        return entities -> entities.stream()
                .map(functionConvertToDto())
                .collect(Collectors.toList());
    }

    /**
     * get a function implement to convert several of dtos to entities
     *
     * @return
     */
    protected Function<Collection<DTO>, Collection<ENTITY>> functionConvertToEntityList() {
        return dtos -> dtos.stream()
                .map(functionConvertToEntity())
                .collect(Collectors.toList());
    }


}
