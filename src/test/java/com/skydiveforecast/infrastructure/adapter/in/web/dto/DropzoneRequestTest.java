package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DropzoneRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void builder_ShouldCreateValidRequest() {
        // Arrange & Act
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Assert
        assertThat(request.getName()).isEqualTo("Test Dropzone");
        assertThat(request.getCity()).isEqualTo("Test City");
        assertThat(request.getLatitude()).isEqualByComparingTo(new BigDecimal("50.12345678"));
        assertThat(request.getLongitude()).isEqualByComparingTo(new BigDecimal("19.12345678"));
        assertThat(request.getIsWingsuitFriendly()).isTrue();
    }

    @Test
    void validation_ShouldFailWhenNameIsBlank() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Act
        Set<ConstraintViolation<DropzoneRequest>> violations = validator.validate(request);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void validation_ShouldFailWhenCityIsBlank() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Act
        Set<ConstraintViolation<DropzoneRequest>> violations = validator.validate(request);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("city"));
    }

    @Test
    void validation_ShouldFailWhenLatitudeIsNull() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(null)
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Act
        Set<ConstraintViolation<DropzoneRequest>> violations = validator.validate(request);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("latitude"));
    }

    @Test
    void validation_ShouldFailWhenLatitudeExceedsMax() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("91.0"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Act
        Set<ConstraintViolation<DropzoneRequest>> violations = validator.validate(request);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("latitude"));
    }

    @Test
    void validation_ShouldFailWhenLatitudeBelowMin() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("-91.0"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Act
        Set<ConstraintViolation<DropzoneRequest>> violations = validator.validate(request);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("latitude"));
    }

    @Test
    void validation_ShouldFailWhenLongitudeExceedsMax() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("181.0"))
                .isWingsuitFriendly(true)
                .build();

        // Act
        Set<ConstraintViolation<DropzoneRequest>> violations = validator.validate(request);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("longitude"));
    }

    @Test
    void validation_ShouldFailWhenLongitudeBelowMin() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("-181.0"))
                .isWingsuitFriendly(true)
                .build();

        // Act
        Set<ConstraintViolation<DropzoneRequest>> violations = validator.validate(request);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("longitude"));
    }

    @Test
    void validation_ShouldFailWhenIsWingsuitFriendlyIsNull() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(null)
                .build();

        // Act
        Set<ConstraintViolation<DropzoneRequest>> violations = validator.validate(request);

        // Assert
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("isWingsuitFriendly"));
    }

    @Test
    void validation_ShouldPassWithValidData() {
        // Arrange
        DropzoneRequest request = DropzoneRequest.builder()
                .name("Test Dropzone")
                .city("Test City")
                .latitude(new BigDecimal("50.12345678"))
                .longitude(new BigDecimal("19.12345678"))
                .isWingsuitFriendly(true)
                .build();

        // Act
        Set<ConstraintViolation<DropzoneRequest>> violations = validator.validate(request);

        // Assert
        assertThat(violations).isEmpty();
    }
}
