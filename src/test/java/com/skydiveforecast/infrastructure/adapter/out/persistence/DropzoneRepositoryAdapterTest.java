package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.infrastructure.persistance.entity.DropzoneEntity;
import com.skydiveforecast.infrastructure.adapter.out.persistence.mapper.DropzoneEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DropzoneRepositoryAdapterTest {

    @Mock
    private DropzoneJpaRepository jpaRepository;

    @Mock
    private DropzoneEntityMapper entityMapper;

    @InjectMocks
    private DropzoneRepositoryAdapter adapter;

    @Test
    void save_ShouldDelegateToJpaRepository() {
        Dropzone domain = Dropzone.builder()
                .name("Test")
                .city("City")
                .latitude(new BigDecimal("50.0"))
                .longitude(new BigDecimal("20.0"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneEntity entity = DropzoneEntity.builder()
                .name("Test")
                .city("City")
                .latitude(new BigDecimal("50.0"))
                .longitude(new BigDecimal("20.0"))
                .isWingsuitFriendly(true)
                .build();

        when(entityMapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(entityMapper.toDomain(entity)).thenReturn(domain);

        Dropzone result = adapter.save(domain);

        assertThat(result).isEqualTo(domain);
        verify(jpaRepository).save(entity);
    }

    @Test
    void findById_ShouldDelegateToJpaRepository() {
        Long id = 1L;
        DropzoneEntity entity = DropzoneEntity.builder().id(id).build();
        Dropzone domain = Dropzone.builder().id(id).build();
        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entityMapper.toDomain(entity)).thenReturn(domain);

        Optional<Dropzone> result = adapter.findById(id);

        assertThat(result).isPresent().contains(domain);
        verify(jpaRepository).findById(id);
    }

    @Test
    void findAll_ShouldDelegateToJpaRepository() {
        DropzoneEntity entity = DropzoneEntity.builder().build();
        Dropzone domain = Dropzone.builder().build();
        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(entityMapper.toDomain(entity)).thenReturn(domain);

        List<Dropzone> result = adapter.findAll();

        assertThat(result).containsExactly(domain);
        verify(jpaRepository).findAll();
    }

    @Test
    void findByCity_ShouldDelegateToJpaRepository() {
        String city = "TestCity";
        DropzoneEntity entity = DropzoneEntity.builder().city(city).build();
        Dropzone domain = Dropzone.builder().city(city).build();
        when(jpaRepository.findByCity(city)).thenReturn(List.of(entity));
        when(entityMapper.toDomain(entity)).thenReturn(domain);

        List<Dropzone> result = adapter.findByCity(city);

        assertThat(result).containsExactly(domain);
        verify(jpaRepository).findByCity(city);
    }

    @Test
    void existsById_ShouldDelegateToJpaRepository() {
        Long id = 1L;
        when(jpaRepository.existsById(id)).thenReturn(true);

        boolean result = adapter.existsById(id);

        assertThat(result).isTrue();
        verify(jpaRepository).existsById(id);
    }

    @Test
    void deleteById_ShouldDelegateToJpaRepository() {
        Long id = 1L;

        adapter.deleteById(id);

        verify(jpaRepository).deleteById(id);
    }
}
