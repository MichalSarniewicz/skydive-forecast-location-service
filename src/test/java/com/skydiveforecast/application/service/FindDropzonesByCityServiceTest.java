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
class FindDropzonesByCityServiceTest {

    @Mock
    private DropzoneRepositoryPort dropzoneRepositoryPort;

    @InjectMocks
    private FindDropzonesByCityService findDropzonesByCityUseCase;

    @Test
    void execute_ShouldReturnDropzonesForCity() {
        // Arrange
        String city = "Test City";
        Dropzone dropzone1 = Dropzone.builder()
                .id(1L)
                .name("Dropzone 1")
                .city(city)
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        Dropzone dropzone2 = Dropzone.builder()
                .id(2L)
                .name("Dropzone 2")
                .city(city)
                .latitude(new BigDecimal("50.22345678"))
                .longitude(new BigDecimal("19.22345678"))
                .isWingsuitFriendly(false)
                .build();

        when(dropzoneRepositoryPort.findByCity(city)).thenReturn(List.of(dropzone1, dropzone2));

        // Act
        List<Dropzone> result = findDropzonesByCityUseCase.execute(city);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(dropzone1, dropzone2);
        verify(dropzoneRepositoryPort).findByCity(city);
    }

    @Test
    void execute_ShouldReturnEmptyListWhenNoCityMatches() {
        // Arrange
        String city = "Nonexistent City";
        when(dropzoneRepositoryPort.findByCity(city)).thenReturn(List.of());

        // Act
        List<Dropzone> result = findDropzonesByCityUseCase.execute(city);

        // Assert
        assertThat(result).isEmpty();
        verify(dropzoneRepositoryPort).findByCity(city);
    }
}
