package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllDropzonesServiceTest {

    @Mock
    private DropzoneRepositoryPort dropzoneRepositoryPort;

    @Mock
    private DropzoneMapper dropzoneMapper;

    @InjectMocks
    private GetAllDropzonesService getAllDropzonesUseCase;

    @Test
    void execute_ShouldReturnAllDropzones() {
        // Arrange
        DropzoneEntity entity1 = DropzoneEntity.builder()
                .id(1L)
                .name("Dropzone 1")
                .city("City 1")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneEntity entity2 = DropzoneEntity.builder()
                .id(2L)
                .name("Dropzone 2")
                .city("City 2")
                .latitude(new BigDecimal("51.12345678"))
                .longitude(new BigDecimal("20.12345678"))
                .isWingsuitFriendly(false)
                .build();

        DropzoneResponse response1 = DropzoneResponse.builder()
                .id(1L)
                .name("Dropzone 1")
                .city("City 1")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneResponse response2 = DropzoneResponse.builder()
                .id(2L)
                .name("Dropzone 2")
                .city("City 2")
                .latitude(new BigDecimal("51.12345678"))
                .longitude(new BigDecimal("20.12345678"))
                .isWingsuitFriendly(false)
                .build();

        when(dropzoneRepositoryPort.findAll()).thenReturn(List.of(entity1, entity2));
        when(dropzoneMapper.toResponse(entity1)).thenReturn(response1);
        when(dropzoneMapper.toResponse(entity2)).thenReturn(response2);

        // Act
        List<DropzoneResponse> result = getAllDropzonesUseCase.execute();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(response1, response2);
        verify(dropzoneRepositoryPort).findAll();
    }

    @Test
    void execute_ShouldReturnEmptyListWhenNoDropzones() {
        // Arrange
        when(dropzoneRepositoryPort.findAll()).thenReturn(List.of());

        // Act
        List<DropzoneResponse> result = getAllDropzonesUseCase.execute();

        // Assert
        assertThat(result).isEmpty();
        verify(dropzoneRepositoryPort).findAll();
    }
}
