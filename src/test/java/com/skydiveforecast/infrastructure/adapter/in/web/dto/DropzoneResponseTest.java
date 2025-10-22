package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DropzoneResponseTest {

    @Test
    void builder_ShouldCreateResponse() {
        // Arrange & Act
        DropzoneResponse response = DropzoneResponse.builder()
                .id(1L)
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Assert
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Test Dropzone");
        assertThat(response.getCity()).isEqualTo("Test City");
        assertThat(response.getLatitude()).isEqualByComparingTo(new BigDecimal("50.12345678"));
        assertThat(response.getLongitude()).isEqualByComparingTo(new BigDecimal("19.12345678"));
        assertThat(response.getIsWingsuitFriendly()).isTrue();
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyResponse() {
        // Arrange & Act
        DropzoneResponse response = new DropzoneResponse();

        // Assert
        assertThat(response.getId()).isNull();
        assertThat(response.getName()).isNull();
        assertThat(response.getCity()).isNull();
        assertThat(response.getLatitude()).isNull();
        assertThat(response.getLongitude()).isNull();
        assertThat(response.getIsWingsuitFriendly()).isNull();
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Arrange
        DropzoneResponse response = new DropzoneResponse();

        // Act
        response.setId(1L);
        response.setName("Test Dropzone");
        response.setCity("Test City");
        response.setLatitude(new BigDecimal("50.12345678"));
        response.setLongitude(new BigDecimal("19.12345678"));
        response.setIsWingsuitFriendly(true);

        // Assert
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Test Dropzone");
        assertThat(response.getCity()).isEqualTo("Test City");
        assertThat(response.getLatitude()).isEqualByComparingTo(new BigDecimal("50.12345678"));
        assertThat(response.getLongitude()).isEqualByComparingTo(new BigDecimal("19.12345678"));
        assertThat(response.getIsWingsuitFriendly()).isTrue();
    }
}
