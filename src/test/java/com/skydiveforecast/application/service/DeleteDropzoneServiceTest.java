package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteDropzoneServiceTest {

    @Mock
    private DropzoneRepositoryPort dropzoneRepositoryPort;

    @InjectMocks
    private DeleteDropzoneService deleteDropzoneUseCase;

    @Test
    void execute_ShouldDeleteDropzoneWhenExists() {
        // Arrange
        Long dropzoneId = 1L;
        when(dropzoneRepositoryPort.existsById(dropzoneId)).thenReturn(true);

        // Act
        deleteDropzoneUseCase.execute(dropzoneId);

        // Assert
        verify(dropzoneRepositoryPort).existsById(dropzoneId);
        verify(dropzoneRepositoryPort).deleteById(dropzoneId);
    }

    @Test
    void execute_ShouldThrowExceptionWhenDropzoneNotFound() {
        // Arrange
        Long dropzoneId = 999L;
        when(dropzoneRepositoryPort.existsById(dropzoneId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> deleteDropzoneUseCase.execute(dropzoneId))
                .isInstanceOf(DropzoneNotFoundException.class)
                .hasMessageContaining("Dropzone with id " + dropzoneId + " not found");
        verify(dropzoneRepositoryPort).existsById(dropzoneId);
    }
}
