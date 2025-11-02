package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.domain.port.in.FindDropzonesByCityUseCase;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindDropzonesByCityService implements FindDropzonesByCityUseCase {

    private final DropzoneRepositoryPort dropzoneRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "dropzones", key = "#city")
    public List<Dropzone> execute(String city) {
        return dropzoneRepositoryPort.findByCity(city);
    }
}
