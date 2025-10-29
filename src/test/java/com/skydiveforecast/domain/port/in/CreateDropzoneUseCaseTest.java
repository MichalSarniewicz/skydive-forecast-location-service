package com.skydiveforecast.domain.port.in;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateDropzoneUseCaseTest {

    @Mock
    private DropzoneRepository dropzoneRepository;

    @Mock
    private DropzoneMapper dropzoneMapper;

    @InjectMocks
    private CreateDropzoneUseCase createDropzoneUseCase;

    @Test
    void execute_ShouldCreateAndReturnDropzone() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneEntity entity = DropzoneEntity.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneEntity savedEntity = DropzoneEntity.builder()
                .id(1L)
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneResponse expectedResponse = DropzoneResponse.builder()
                .id(1L)
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        when(dropzoneMapper.toEntity(request)).thenReturn(entity);
        when(dropzoneRepository.save(entity)).thenReturn(savedEntity);
        when(dropzoneMapper.toResponse(savedEntity)).thenReturn(expectedResponse);

        // Act
        DropzoneResponse result = createDropzoneUseCase.execute(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Dropzone");
        assertThat(result.getCity()).isEqualTo("Test City");
        verify(dropzoneMapper).toEntity(request);
        verify(dropzoneRepository).save(entity);
        verify(dropzoneMapper).toResponse(savedEntity);
    }

    @Test
    void execute_ShouldHandleNonWingsuitFriendlyDropzone() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Non-Wingsuit Dropzone")
                .city("City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        DropzoneEntity entity = DropzoneEntity.builder()
                .name("Non-Wingsuit Dropzone")
                .city("City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        DropzoneEntity savedEntity = DropzoneEntity.builder()
                .id(2L)
                .name("Non-Wingsuit Dropzone")
                .city("City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        DropzoneResponse expectedResponse = DropzoneResponse.builder()
                .id(2L)
                .name("Non-Wingsuit Dropzone")
                .city("City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        when(dropzoneMapper.toEntity(request)).thenReturn(entity);
        when(dropzoneRepository.save(any(DropzoneEntity.class))).thenReturn(savedEntity);
        when(dropzoneMapper.toResponse(savedEntity)).thenReturn(expectedResponse);

        // Act
        DropzoneResponse result = createDropzoneUseCase.execute(request);

        // Assert
        assertThat(result.getIsWingsuitFriendly()).isFalse();
        verify(dropzoneRepository).save(any(DropzoneEntity.class));
    }
}
