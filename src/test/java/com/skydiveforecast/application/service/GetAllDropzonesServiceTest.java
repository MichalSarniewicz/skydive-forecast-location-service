package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.model.Dropzone;
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

    @InjectMocks
    private GetAllDropzonesService getAllDropzonesUseCase;

    @Test
    void execute_ShouldReturnAllDropzones() {
        // Arrange
        Dropzone dropzone1 = Dropzone.builder()
                .id(1L)
                .name("Dropzone 1")
                .city("City 1")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        Dropzone dropzone2 = Dropzone.builder()
                .id(2L)
                .name("Dropzone 2")
                .city("City 2")
                .latitude(new BigDecimal("51.12345678"))
                .longitude(new BigDecimal("20.12345678"))
                .isWingsuitFriendly(false)
                .build();

        when(dropzoneRepositoryPort.findAll()).thenReturn(List.of(dropzone1, dropzone2));

        // Act
        List<Dropzone> result = getAllDropzonesUseCase.execute();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(dropzone1, dropzone2);
        verify(dropzoneRepositoryPort).findAll();
    }

    @Test
    void execute_ShouldReturnEmptyListWhenNoDropzones() {
        // Arrange
        when(dropzoneRepositoryPort.findAll()).thenReturn(List.of());

        // Act
        List<Dropzone> result = getAllDropzonesUseCase.execute();

        // Assert
        assertThat(result).isEmpty();
        verify(dropzoneRepositoryPort).findAll();
    }
}
