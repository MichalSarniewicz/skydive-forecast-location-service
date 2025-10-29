package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.domain.model.DropzoneEntity;

import java.util.List;
import java.util.Optional;

public interface DropzoneRepositoryPort {

    DropzoneEntity save(DropzoneEntity dropzone);

    Optional<DropzoneEntity> findById(Long id);

    List<DropzoneEntity> findAll();

    List<DropzoneEntity> findByCity(String city);

    boolean existsById(Long id);

    void deleteById(Long id);
}
