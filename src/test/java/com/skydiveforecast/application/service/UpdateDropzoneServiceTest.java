package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.domain.model.Dropzone;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateDropzoneServiceTest {

    @Mock
    private DropzoneRepositoryPort dropzoneRepositoryPort;

    @InjectMocks
    private UpdateDropzoneService updateDropzoneUseCase;

    @Test
    void execute_ShouldUpdateAndReturnDropzone() {
        // Arrange
        Long dropzoneId = 1L;
        Dropzone updateData = Dropzone.builder()
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        Dropzone existingDropzone = Dropzone.builder()
                .id(dropzoneId)
                .name("Old Dropzone")
                .city("Old City")
                .latitude(new BigDecimal("50.00000000"))
                .longitude(new BigDecimal("19.00000000"))
                .isWingsuitFriendly(true)
                .build();

        Dropzone updatedDropzone = Dropzone.builder()
                .id(dropzoneId)
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        when(dropzoneRepositoryPort.findById(dropzoneId)).thenReturn(Optional.of(existingDropzone));
        when(dropzoneRepositoryPort.save(any(Dropzone.class))).thenReturn(updatedDropzone);

        // Act
        Dropzone result = updateDropzoneUseCase.execute(dropzoneId, updateData);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Dropzone");
        assertThat(result.getCity()).isEqualTo("Updated City");
        assertThat(result.getIsWingsuitFriendly()).isFalse();
        verify(dropzoneRepositoryPort).findById(dropzoneId);
        verify(dropzoneRepositoryPort).save(any(Dropzone.class));
    }

    @Test
    void execute_ShouldThrowExceptionWhenDropzoneNotFound() {
        // Arrange
        Long dropzoneId = 999L;
        Dropzone updateData = Dropzone.builder()
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        when(dropzoneRepositoryPort.findById(dropzoneId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> updateDropzoneUseCase.execute(dropzoneId, updateData))
                .isInstanceOf(DropzoneNotFoundException.class)
                .hasMessageContaining("Dropzone with id " + dropzoneId + " not found");
        verify(dropzoneRepositoryPort).findById(dropzoneId);
    }
}
