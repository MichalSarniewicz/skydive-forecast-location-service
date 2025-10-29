package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.DropzoneEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DropzoneRepositoryAdapterTest {

    @Mock
    private DropzoneJpaRepository jpaRepository;

    @InjectMocks
    private DropzoneRepositoryAdapter adapter;

    @Test
    void save_ShouldDelegateToJpaRepository() {
        DropzoneEntity entity = DropzoneEntity.builder()
                .name("Test")
                .city("City")
                .latitude(new BigDecimal("50.0"))
                .longitude(new BigDecimal("20.0"))
                .isWingsuitFriendly(true)
                .build();

        when(jpaRepository.save(entity)).thenReturn(entity);

        DropzoneEntity result = adapter.save(entity);

        assertThat(result).isEqualTo(entity);
        verify(jpaRepository).save(entity);
    }

    @Test
    void findById_ShouldDelegateToJpaRepository() {
        Long id = 1L;
        DropzoneEntity entity = DropzoneEntity.builder().id(id).build();
        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<DropzoneEntity> result = adapter.findById(id);

        assertThat(result).isPresent().contains(entity);
        verify(jpaRepository).findById(id);
    }

    @Test
    void findAll_ShouldDelegateToJpaRepository() {
        List<DropzoneEntity> entities = List.of(DropzoneEntity.builder().build());
        when(jpaRepository.findAll()).thenReturn(entities);

        List<DropzoneEntity> result = adapter.findAll();

        assertThat(result).isEqualTo(entities);
        verify(jpaRepository).findAll();
    }

    @Test
    void findByCity_ShouldDelegateToJpaRepository() {
        String city = "TestCity";
        List<DropzoneEntity> entities = List.of(DropzoneEntity.builder().city(city).build());
        when(jpaRepository.findByCity(city)).thenReturn(entities);

        List<DropzoneEntity> result = adapter.findByCity(city);

        assertThat(result).isEqualTo(entities);
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
