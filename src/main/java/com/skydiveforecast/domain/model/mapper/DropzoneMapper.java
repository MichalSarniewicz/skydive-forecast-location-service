package com.skydiveforecast.domain.model.mapper;

import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DropzoneMapper {

    DropzoneEntity toEntity(DropzoneRequest request);

    DropzoneResponse toResponse(DropzoneEntity dropzone);
}