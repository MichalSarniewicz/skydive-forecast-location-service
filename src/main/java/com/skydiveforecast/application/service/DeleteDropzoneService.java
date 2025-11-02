package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.domain.port.in.DeleteDropzoneUseCase;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteDropzoneService implements DeleteDropzoneUseCase {

    private final DropzoneRepositoryPort dropzoneRepositoryPort;

    @Override
    @Transactional
    @CacheEvict(value = "dropzones", allEntries = true)
    public void execute(Long id) {
        if (!dropzoneRepositoryPort.existsById(id)) {
            throw new DropzoneNotFoundException("Dropzone with id " + id + " not found");
        }
        dropzoneRepositoryPort.deleteById(id);
    }
}
