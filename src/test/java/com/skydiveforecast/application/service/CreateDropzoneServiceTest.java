package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.domain.port.out.DropzoneRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateDropzoneServiceTest {

    @Mock
    private DropzoneRepositoryPort dropzoneRepositoryPort;

    @InjectMocks
    private CreateDropzoneService createDropzoneService;

    @Test
    void execute_ShouldCreateAndReturnDropzone() {
        // Arrange
        Dropzone dropzone = Dropzone.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        Dropzone savedDropzone = Dropzone.builder()
                .id(1L)
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        when(dropzoneRepositoryPort.save(dropzone)).thenReturn(savedDropzone);

        // Act
        Dropzone result = createDropzoneService.execute(dropzone);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Dropzone");
        assertThat(result.getCity()).isEqualTo("Test City");
        verify(dropzoneRepositoryPort).save(dropzone);
    }

    @Test
    void execute_ShouldHandleNonWingsuitFriendlyDropzone() {
        // Arrange
        Dropzone dropzone = Dropzone.builder()
                .name("Non-Wingsuit Dropzone")
                .city("City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        Dropzone savedDropzone = Dropzone.builder()
                .id(2L)
                .name("Non-Wingsuit Dropzone")
                .city("City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        when(dropzoneRepositoryPort.save(any(Dropzone.class))).thenReturn(savedDropzone);

        // Act
        Dropzone result = createDropzoneService.execute(dropzone);

        // Assert
        assertThat(result.getIsWingsuitFriendly()).isFalse();
        verify(dropzoneRepositoryPort).save(any(Dropzone.class));
    }
}
