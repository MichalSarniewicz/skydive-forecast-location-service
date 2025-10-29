package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import com.skydiveforecast.infrastructure.adapter.out.persistance.DropzoneRepository;
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
class UpdateDropzoneUseCaseTest {

    @Mock
    private DropzoneRepository dropzoneRepository;

    @Mock
    private DropzoneMapper dropzoneMapper;

    @InjectMocks
    private UpdateDropzoneUseCase updateDropzoneUseCase;

    @Test
    void execute_ShouldUpdateAndReturnDropzone() {
        // Arrange
        Long dropzoneId = 1L;
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        DropzoneEntity existingEntity = DropzoneEntity.builder()
                .id(dropzoneId)
                .name("Old Dropzone")
                .city("Old City")
                .latitude(new BigDecimal("50.00000000"))
                .longitude(new BigDecimal("19.00000000"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneEntity updatedEntity = DropzoneEntity.builder()
                .id(dropzoneId)
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        DropzoneResponse expectedResponse = DropzoneResponse.builder()
                .id(dropzoneId)
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        when(dropzoneRepository.findById(dropzoneId)).thenReturn(Optional.of(existingEntity));
        when(dropzoneRepository.save(any(DropzoneEntity.class))).thenReturn(updatedEntity);
        when(dropzoneMapper.toResponse(updatedEntity)).thenReturn(expectedResponse);

        // Act
        DropzoneResponse result = updateDropzoneUseCase.execute(dropzoneId, request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Dropzone");
        assertThat(result.getCity()).isEqualTo("Updated City");
        assertThat(result.getIsWingsuitFriendly()).isFalse();
        verify(dropzoneRepository).findById(dropzoneId);
        verify(dropzoneRepository).save(any(DropzoneEntity.class));
    }

    @Test
    void execute_ShouldThrowExceptionWhenDropzoneNotFound() {
        // Arrange
        Long dropzoneId = 999L;
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        when(dropzoneRepository.findById(dropzoneId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> updateDropzoneUseCase.execute(dropzoneId, request))
                .isInstanceOf(DropzoneNotFoundException.class)
                .hasMessageContaining("Dropzone with id " + dropzoneId + " not found");
        verify(dropzoneRepository).findById(dropzoneId);
    }
}
