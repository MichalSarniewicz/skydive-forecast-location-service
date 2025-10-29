package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.infrastructure.adapter.out.persistance.DropzoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteDropzoneUseCaseTest {

    @Mock
    private DropzoneRepository dropzoneRepository;

    @InjectMocks
    private DeleteDropzoneUseCase deleteDropzoneUseCase;

    @Test
    void execute_ShouldDeleteDropzoneWhenExists() {
        // Arrange
        Long dropzoneId = 1L;
        when(dropzoneRepository.existsById(dropzoneId)).thenReturn(true);

        // Act
        deleteDropzoneUseCase.execute(dropzoneId);

        // Assert
        verify(dropzoneRepository).existsById(dropzoneId);
        verify(dropzoneRepository).deleteById(dropzoneId);
    }

    @Test
    void execute_ShouldThrowExceptionWhenDropzoneNotFound() {
        // Arrange
        Long dropzoneId = 999L;
        when(dropzoneRepository.existsById(dropzoneId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> deleteDropzoneUseCase.execute(dropzoneId))
                .isInstanceOf(DropzoneNotFoundException.class)
                .hasMessageContaining("Dropzone with id " + dropzoneId + " not found");
        verify(dropzoneRepository).existsById(dropzoneId);
    }
}
