package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.port.in.GetAllDropzonesUseCase;
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
public class GetAllDropzonesService implements GetAllDropzonesUseCase {

    private final DropzoneRepositoryPort dropzoneRepositoryPort;
    private final DropzoneMapper dropzoneMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "dropzones", key = "'all'")
    public List<DropzoneResponse> execute() {
        return dropzoneRepositoryPort.findAll()
                .stream()
                .map(dropzoneMapper::toResponse)
                .collect(Collectors.toList());
    }
}
