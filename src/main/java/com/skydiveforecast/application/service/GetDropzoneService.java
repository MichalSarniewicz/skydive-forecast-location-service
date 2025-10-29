package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.domain.port.in.GetDropzoneUseCase;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetDropzoneService implements GetDropzoneUseCase {

    private final DropzoneRepositoryPort dropzoneRepositoryPort;
    private final DropzoneMapper dropzoneMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "dropzones", key = "#id")
    public DropzoneResponse execute(Long id) {
        DropzoneEntity dropzone = dropzoneRepositoryPort.findById(id)
                .orElseThrow(() -> new DropzoneNotFoundException("Dropzone with id " + id + " not found"));
        return dropzoneMapper.toResponse(dropzone);
    }
}
