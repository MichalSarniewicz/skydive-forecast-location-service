package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.domain.port.in.GetAllDropzonesUseCase;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllDropzonesService implements GetAllDropzonesUseCase {

    private final DropzoneRepositoryPort dropzoneRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "dropzones", key = "'all'")
    public List<Dropzone> execute() {
        return dropzoneRepositoryPort.findAll();
    }
}
