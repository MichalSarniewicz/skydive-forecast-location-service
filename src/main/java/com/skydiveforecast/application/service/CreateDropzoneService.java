package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.domain.port.in.CreateDropzoneUseCase;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateDropzoneService implements CreateDropzoneUseCase {

    private final DropzoneRepositoryPort dropzoneRepositoryPort;

    @Override
    @Transactional
    @CacheEvict(value = "dropzones", allEntries = true)
    public Dropzone execute(Dropzone dropzone) {
        return dropzoneRepositoryPort.save(dropzone);
    }
}
