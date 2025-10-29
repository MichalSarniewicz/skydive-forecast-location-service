package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DropzoneRepositoryAdapter implements DropzoneRepositoryPort {

    private final DropzoneJpaRepository jpaRepository;

    @Override
    public DropzoneEntity save(DropzoneEntity dropzone) {
        return jpaRepository.save(dropzone);
    }

    @Override
    public Optional<DropzoneEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<DropzoneEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<DropzoneEntity> findByCity(String city) {
        return jpaRepository.findByCity(city);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
