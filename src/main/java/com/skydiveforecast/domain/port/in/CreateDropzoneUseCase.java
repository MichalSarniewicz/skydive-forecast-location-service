package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.out.persistance.DropzoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateDropzoneUseCase {

    private final DropzoneRepository dropzoneRepository;
    private final DropzoneMapper dropzoneMapper;

    @Transactional
    @CacheEvict(value = "dropzones", allEntries = true)
    public DropzoneResponse execute(DropzoneRequest request) {
        DropzoneEntity dropzone = dropzoneMapper.toEntity(request);
        DropzoneEntity savedDropzone = dropzoneRepository.save(dropzone);
        return dropzoneMapper.toResponse(savedDropzone);
    }
}