package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.out.persistence.mapper.DropzoneEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DropzoneRepositoryAdapter implements DropzoneRepositoryPort {

    private final DropzoneJpaRepository jpaRepository;
    private final DropzoneEntityMapper entityMapper;

    @Override
    public Dropzone save(Dropzone dropzone) {
        return entityMapper.toDomain(jpaRepository.save(entityMapper.toEntity(dropzone)));
    }

    @Override
    public Optional<Dropzone> findById(Long id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<Dropzone> findAll() {
        return jpaRepository.findAll().stream().map(entityMapper::toDomain).toList();
    }

    @Override
    public List<Dropzone> findByCity(String city) {
        return jpaRepository.findByCity(city).stream().map(entityMapper::toDomain).toList();
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
