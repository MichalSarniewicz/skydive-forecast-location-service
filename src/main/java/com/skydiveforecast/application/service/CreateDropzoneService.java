package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.domain.port.in.CreateDropzoneUseCase;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateDropzoneService implements CreateDropzoneUseCase {

    private final DropzoneRepositoryPort dropzoneRepositoryPort;
    private final DropzoneMapper dropzoneMapper;

    @Transactional
    @CacheEvict(value = "dropzones", allEntries = true)
    public DropzoneResponse execute(DropzoneRequest request) {
        DropzoneEntity dropzone = dropzoneMapper.toEntity(request);
        DropzoneEntity savedDropzone = dropzoneRepositoryPort.save(dropzone);
        return dropzoneMapper.toResponse(savedDropzone);
    }
}
