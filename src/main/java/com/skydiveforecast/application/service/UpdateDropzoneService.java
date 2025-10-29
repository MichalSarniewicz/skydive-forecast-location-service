package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.domain.port.in.UpdateDropzoneUseCase;
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
public class UpdateDropzoneService implements UpdateDropzoneUseCase {

    private final DropzoneRepositoryPort dropzoneRepositoryPort;
    private final DropzoneMapper dropzoneMapper;

    @Transactional
    @CacheEvict(value = "dropzones", allEntries = true)
    public DropzoneResponse execute(Long id, DropzoneRequest request) {
        DropzoneEntity dropzone = dropzoneRepositoryPort.findById(id)
                .orElseThrow(() -> new DropzoneNotFoundException("Dropzone with id " + id + " not found"));

        dropzone.setName(request.getName());
        dropzone.setCity(request.getCity());
        dropzone.setLatitude(request.getLatitude());
        dropzone.setLongitude(request.getLongitude());
        dropzone.setIsWingsuitFriendly(request.getIsWingsuitFriendly());
        
        DropzoneEntity updatedDropzone = dropzoneRepositoryPort.save(dropzone);
        return dropzoneMapper.toResponse(updatedDropzone);
    }
}
