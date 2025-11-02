package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DropzoneMapperTest {

    private final DropzoneMapper mapper = Mappers.getMapper(DropzoneMapper.class);

    @Test
    void toDomain_ShouldMapRequestToDomain() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Act
        Dropzone domain = mapper.toDomain(request);

        // Assert
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isNull();
        assertThat(domain.getName()).isEqualTo("Test Dropzone");
        assertThat(domain.getCity()).isEqualTo("Test City");
        assertThat(domain.getLatitude()).isEqualTo(new BigDecimal("50.12345678"));
        assertThat(domain.getLongitude()).isEqualTo(new BigDecimal("19.12345678"));
        assertThat(domain.getIsWingsuitFriendly()).isTrue();
    }

    @Test
    void toDomain_ShouldHandleNonWingsuitFriendly() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Non-Wingsuit Dropzone")
                .city("City")
                .latitude(new BigDecimal("51.00000000"))
                .longitude(new BigDecimal("20.00000000"))
                .isWingsuitFriendly(false)
                .build();

        // Act
        Dropzone domain = mapper.toDomain(request);

        // Assert
        assertThat(domain.getIsWingsuitFriendly()).isFalse();
    }

    @Test
    void toResponse_ShouldMapDomainToResponse() {
        // Arrange
        Dropzone domain = Dropzone.builder()
                .id(1L)
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Act
        DropzoneResponse response = mapper.toResponse(domain);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Test Dropzone");
        assertThat(response.getCity()).isEqualTo("Test City");
        assertThat(response.getLatitude()).isEqualTo(new BigDecimal("50.12345678"));
        assertThat(response.getLongitude()).isEqualTo(new BigDecimal("19.12345678"));
        assertThat(response.getIsWingsuitFriendly()).isTrue();
    }

    @Test
    void toResponse_ShouldMapAllFields() {
        // Arrange
        Dropzone domain = Dropzone.builder()
                .id(99L)
                .name("Another Dropzone")
                .city("Another City")
                .latitude(new BigDecimal("52.99999999"))
                .longitude(new BigDecimal("21.99999999"))
                .isWingsuitFriendly(false)
                .build();

        // Act
        DropzoneResponse response = mapper.toResponse(domain);

        // Assert
        assertThat(response.getId()).isEqualTo(99L);
        assertThat(response.getName()).isEqualTo("Another Dropzone");
        assertThat(response.getCity()).isEqualTo("Another City");
        assertThat(response.getLatitude()).isEqualTo(new BigDecimal("52.99999999"));
        assertThat(response.getLongitude()).isEqualTo(new BigDecimal("21.99999999"));
        assertThat(response.getIsWingsuitFriendly()).isFalse();
    }
}
