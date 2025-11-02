package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DropzoneControllerTest {

    @Mock
    private CreateDropzoneUseCase createDropzoneUseCase;

    @Mock
    private GetDropzoneUseCase getDropzoneUseCase;

    @Mock
    private GetAllDropzonesUseCase getAllDropzonesUseCase;

    @Mock
    private UpdateDropzoneUseCase updateDropzoneUseCase;

    @Mock
    private DeleteDropzoneUseCase deleteDropzoneUseCase;

    @Mock
    private FindDropzonesByCityUseCase findDropzonesByCityUseCase;

    @Mock
    private DropzoneMapper mapper;

    @InjectMocks
    private DropzoneController dropzoneController;

    @Test
    void createDropzone_ShouldReturnCreatedResponse() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        Dropzone domain = Dropzone.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        Dropzone savedDomain = Dropzone.builder()
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

        when(mapper.toDomain(request)).thenReturn(domain);
        when(createDropzoneUseCase.execute(domain)).thenReturn(savedDomain);
        when(mapper.toResponse(savedDomain)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<DropzoneResponse> response = dropzoneController.createDropzone(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(createDropzoneUseCase).execute(domain);
    }

    @Test
    void getDropzone_ShouldReturnDropzone() {
        // Arrange
        Long dropzoneId = 1L;
        Dropzone domain = Dropzone.builder()
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

        when(getDropzoneUseCase.execute(dropzoneId)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<DropzoneResponse> response = dropzoneController.getDropzone(dropzoneId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(getDropzoneUseCase).execute(dropzoneId);
    }

    @Test
    void getAllDropzones_ShouldReturnAllDropzones() {
        // Arrange
        Dropzone domain1 = Dropzone.builder()
                .id(1L)
                .name("Dropzone 1")
                .city("City 1")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        Dropzone domain2 = Dropzone.builder()
                .id(2L)
                .name("Dropzone 2")
                .city("City 2")
                .latitude(new BigDecimal("51.12345678"))
                .longitude(new BigDecimal("20.12345678"))
                .isWingsuitFriendly(false)
                .build();

        DropzoneResponse response1 = DropzoneResponse.builder()
                .id(1L)
                .name("Dropzone 1")
                .city("City 1")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        DropzoneResponse response2 = DropzoneResponse.builder()
                .id(2L)
                .name("Dropzone 2")
                .city("City 2")
                .latitude(new BigDecimal("51.12345678"))
                .longitude(new BigDecimal("20.12345678"))
                .isWingsuitFriendly(false)
                .build();

        when(getAllDropzonesUseCase.execute()).thenReturn(List.of(domain1, domain2));
        when(mapper.toResponse(domain1)).thenReturn(response1);
        when(mapper.toResponse(domain2)).thenReturn(response2);

        // Act
        ResponseEntity<List<DropzoneResponse>> response = dropzoneController.getAllDropzones();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        verify(getAllDropzonesUseCase).execute();
    }

    @Test
    void updateDropzone_ShouldReturnUpdatedDropzone() {
        // Arrange
        Long dropzoneId = 1L;
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(false)
                .build();

        Dropzone domain = Dropzone.builder()
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(false)
                .build();

        Dropzone updatedDomain = Dropzone.builder()
                .id(dropzoneId)
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(false)
                .build();

        DropzoneResponse expectedResponse = DropzoneResponse.builder()
                .id(dropzoneId)
                .name("Updated Dropzone")
                .city("Updated City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(false)
                .build();

        when(mapper.toDomain(request)).thenReturn(domain);
        when(updateDropzoneUseCase.execute(dropzoneId, domain)).thenReturn(updatedDomain);
        when(mapper.toResponse(updatedDomain)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<DropzoneResponse> response = dropzoneController.updateDropzone(dropzoneId, request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(updateDropzoneUseCase).execute(dropzoneId, domain);
    }

    @Test
    void deleteDropzone_ShouldReturnNoContent() {
        // Arrange
        Long dropzoneId = 1L;
        doNothing().when(deleteDropzoneUseCase).execute(dropzoneId);

        // Act
        ResponseEntity<Void> response = dropzoneController.deleteDropzone(dropzoneId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(deleteDropzoneUseCase).execute(dropzoneId);
    }

    @Test
    void getDropzonesByCity_ShouldReturnDropzonesForCity() {
        // Arrange
        String city = "Test City";
        Dropzone domain1 = Dropzone.builder()
                .id(1L)
                .name("Dropzone 1")
                .city(city)
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        Dropzone domain2 = Dropzone.builder()
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

        when(findDropzonesByCityUseCase.execute(city)).thenReturn(List.of(domain1, domain2));
        when(mapper.toResponse(domain1)).thenReturn(response1);
        when(mapper.toResponse(domain2)).thenReturn(response2);

        // Act
        ResponseEntity<List<DropzoneResponse>> response = dropzoneController.getDropzonesByCity(city);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        verify(findDropzonesByCityUseCase).execute(city);
    }
}
