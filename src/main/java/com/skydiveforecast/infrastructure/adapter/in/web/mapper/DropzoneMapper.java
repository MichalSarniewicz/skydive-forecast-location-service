package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DropzoneMapper {

    @Mapping(target = "id", ignore = true)
    Dropzone toDomain(DropzoneRequest request);

    DropzoneResponse toResponse(Dropzone dropzone);
}