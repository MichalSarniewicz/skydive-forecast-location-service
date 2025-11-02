package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.domain.model.Dropzone;

import java.util.List;
import java.util.Optional;

public interface DropzoneRepositoryPort {

    Dropzone save(Dropzone dropzone);

    Optional<Dropzone> findById(Long id);

    List<Dropzone> findAll();

    List<Dropzone> findByCity(String city);

    boolean existsById(Long id);

    void deleteById(Long id);
}
