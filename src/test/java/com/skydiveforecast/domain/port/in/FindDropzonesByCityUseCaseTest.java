package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import com.skydiveforecast.infrastructure.adapter.out.persistance.DropzoneRepository;
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
class FindDropzonesByCityUseCaseTest {

    @Mock
    private DropzoneRepository dropzoneRepository;

    @Mock
    private DropzoneMapper dropzoneMapper;

    @InjectMocks
    private FindDropzonesByCityUseCase findDropzonesByCityUseCase;

    @Test
    void execute_ShouldReturnDropzonesForCity() {
        // Arrange
        String city = "Test City";
        DropzoneEntity entity1 = DropzoneEntity.builder()
                .id(1L)
                .name("Dropzone 1")
                .city(city)
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneEntity entity2 = DropzoneEntity.builder()
                .id(2L)
                .name("Dropzone 2")
                .city(city)
                .latitude(new BigDecimal("50.22345678"))
                .longitude(new BigDecimal("19.22345678"))
                .isWingsuitFriendly(false)
                .build();

        DropzoneResponse response1 = DropzoneResponse.builder()
                .id(1L)
                .name("Dropzone 1")
                .city(city)
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneResponse response2 = DropzoneResponse.builder()
                .id(2L)
                .name("Dropzone 2")
                .city(city)
                .latitude(new BigDecimal("50.22345678"))
                .longitude(new BigDecimal("19.22345678"))
                .isWingsuitFriendly(false)
                .build();

        when(dropzoneRepository.findByCity(city)).thenReturn(List.of(entity1, entity2));
        when(dropzoneMapper.toResponse(entity1)).thenReturn(response1);
        when(dropzoneMapper.toResponse(entity2)).thenReturn(response2);

        // Act
        List<DropzoneResponse> result = findDropzonesByCityUseCase.execute(city);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(response1, response2);
        verify(dropzoneRepository).findByCity(city);
    }

    @Test
    void execute_ShouldReturnEmptyListWhenNoCityMatches() {
        // Arrange
        String city = "Nonexistent City";
        when(dropzoneRepository.findByCity(city)).thenReturn(List.of());

        // Act
        List<DropzoneResponse> result = findDropzonesByCityUseCase.execute(city);

        // Assert
        assertThat(result).isEmpty();
        verify(dropzoneRepository).findByCity(city);
    }
}
