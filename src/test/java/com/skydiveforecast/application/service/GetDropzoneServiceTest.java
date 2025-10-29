package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDropzoneServiceTest {

    @Mock
    private DropzoneRepositoryPort dropzoneRepositoryPort;

    @Mock
    private DropzoneMapper dropzoneMapper;

    @InjectMocks
    private GetDropzoneService getDropzoneUseCase;

    @Test
    void execute_ShouldReturnDropzoneWhenExists() {
        // Arrange
        Long dropzoneId = 1L;
        DropzoneEntity entity = DropzoneEntity.builder()
                .id(dropzoneId)
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneResponse expectedResponse = DropzoneResponse.builder()
                .id(dropzoneId)
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        when(dropzoneRepositoryPort.findById(dropzoneId)).thenReturn(Optional.of(entity));
        when(dropzoneMapper.toResponse(entity)).thenReturn(expectedResponse);

        // Act
        DropzoneResponse result = getDropzoneUseCase.execute(dropzoneId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(dropzoneId);
        assertThat(result.getName()).isEqualTo("Test Dropzone");
        verify(dropzoneRepositoryPort).findById(dropzoneId);
        verify(dropzoneMapper).toResponse(entity);
    }

    @Test
    void execute_ShouldThrowExceptionWhenDropzoneNotFound() {
        // Arrange
        Long dropzoneId = 999L;
        when(dropzoneRepositoryPort.findById(dropzoneId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> getDropzoneUseCase.execute(dropzoneId))
                .isInstanceOf(DropzoneNotFoundException.class)
                .hasMessageContaining("Dropzone with id " + dropzoneId + " not found");
        verify(dropzoneRepositoryPort).findById(dropzoneId);
    }
}
