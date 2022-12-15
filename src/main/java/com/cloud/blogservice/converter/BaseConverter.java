package com.cloud.blogservice.converter;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<D, E>{
    public abstract D fromEntityToDto(E entity);
    public abstract E fromDtoToEntity(D dto);

    public List<D> fromEntitiesToDtos(List<E> entities) {
        if(CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public List<E> fromDtosToEntities(List<D> dtos) {
        if(CollectionUtils.isEmpty(dtos)) {
            return Collections.emptyList();
        }
        return dtos.stream()
                .map(this::fromDtoToEntity)
                .collect(Collectors.toList());
    }

}
