package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.domain.port.in.UpdateDropzoneUseCase;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateDropzoneService implements UpdateDropzoneUseCase {

    private final DropzoneRepositoryPort dropzoneRepositoryPort;

    @Override
    @Transactional
    @CacheEvict(value = "dropzones", allEntries = true)
    public Dropzone execute(Long id, Dropzone dropzone) {
        dropzoneRepositoryPort.findById(id)
                .orElseThrow(() -> new DropzoneNotFoundException("Dropzone with id " + id + " not found"));

        Dropzone updated = Dropzone.builder()
                .id(id)
                .name(dropzone.getName())
                .city(dropzone.getCity())
                .latitude(dropzone.getLatitude())
                .longitude(dropzone.getLongitude())
                .isWingsuitFriendly(dropzone.getIsWingsuitFriendly())
                .build();

        return dropzoneRepositoryPort.save(updated);
    }
}
