package com.skydiveforecast.domain.model;

import com.skydiveforecast.infrastructure.persistance.entity.DropzoneEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DropzoneEntityTest {

    @Test
    void builder_ShouldCreateDropzoneEntity() {
        // Arrange & Act
        DropzoneEntity dropzone = DropzoneEntity.builder()
                .id(1L)
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Assert
        assertThat(dropzone.getId()).isEqualTo(1L);
        assertThat(dropzone.getName()).isEqualTo("Test Dropzone");
        assertThat(dropzone.getCity()).isEqualTo("Test City");
        assertThat(dropzone.getLatitude()).isEqualByComparingTo(new BigDecimal("50.12345678"));
        assertThat(dropzone.getLongitude()).isEqualByComparingTo(new BigDecimal("19.12345678"));
        assertThat(dropzone.getIsWingsuitFriendly()).isTrue();
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyEntity() {
        // Arrange & Act
        DropzoneEntity dropzone = new DropzoneEntity();

        // Assert
        assertThat(dropzone.getId()).isNull();
        assertThat(dropzone.getName()).isNull();
        assertThat(dropzone.getCity()).isNull();
        assertThat(dropzone.getLatitude()).isNull();
        assertThat(dropzone.getLongitude()).isNull();
    }

    @Test
    void allArgsConstructor_ShouldCreateEntityWithAllFields() {
        // Arrange & Act
        DropzoneEntity dropzone = new DropzoneEntity(
                1L,
                "Test Dropzone",
                "Test City",
                new BigDecimal("50.12345678"),
                new BigDecimal("19.12345678"),
                true
        );

        // Assert
        assertThat(dropzone.getId()).isEqualTo(1L);
        assertThat(dropzone.getName()).isEqualTo("Test Dropzone");
        assertThat(dropzone.getCity()).isEqualTo("Test City");
        assertThat(dropzone.getLatitude()).isEqualByComparingTo(new BigDecimal("50.12345678"));
        assertThat(dropzone.getLongitude()).isEqualByComparingTo(new BigDecimal("19.12345678"));
        assertThat(dropzone.getIsWingsuitFriendly()).isTrue();
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Arrange
        DropzoneEntity dropzone = new DropzoneEntity();

        // Act
        dropzone.setId(1L);
        dropzone.setName("Test Dropzone");
        dropzone.setCity("Test City");
        dropzone.setLatitude(new BigDecimal("50.12345678"));
        dropzone.setLongitude(new BigDecimal("19.12345678"));
        dropzone.setIsWingsuitFriendly(true);

        // Assert
        assertThat(dropzone.getId()).isEqualTo(1L);
        assertThat(dropzone.getName()).isEqualTo("Test Dropzone");
        assertThat(dropzone.getCity()).isEqualTo("Test City");
        assertThat(dropzone.getLatitude()).isEqualByComparingTo(new BigDecimal("50.12345678"));
        assertThat(dropzone.getLongitude()).isEqualByComparingTo(new BigDecimal("19.12345678"));
        assertThat(dropzone.getIsWingsuitFriendly()).isTrue();
    }
}
