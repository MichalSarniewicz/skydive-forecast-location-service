package com.skydiveforecast.infrastructure.adapter.out.persistence.mapper;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.infrastructure.persistance.entity.DropzoneEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DropzoneEntityMapper {

    Dropzone toDomain(DropzoneEntity entity);

    DropzoneEntity toEntity(Dropzone domain);
}
