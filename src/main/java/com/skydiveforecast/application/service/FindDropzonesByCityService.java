package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.port.in.FindDropzonesByCityUseCase;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindDropzonesByCityService implements FindDropzonesByCityUseCase {

    private final DropzoneRepositoryPort dropzoneRepositoryPort;
    private final DropzoneMapper dropzoneMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "dropzones", key = "#city")
    public List<DropzoneResponse> execute(String city) {
        return dropzoneRepositoryPort.findByCity(city)
                .stream()
                .map(dropzoneMapper::toResponse)
                .collect(Collectors.toList());
    }
}
