package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    void builder_ShouldCreateErrorResponse() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(404)
                .error("Not Found")
                .message("Resource not found")
                .build();

        // Assert
        assertThat(response.getTimestamp()).isEqualTo(timestamp);
        assertThat(response.getStatus()).isEqualTo(404);
        assertThat(response.getError()).isEqualTo("Not Found");
        assertThat(response.getMessage()).isEqualTo("Resource not found");
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyResponse() {
        // Arrange & Act
        ErrorResponse response = new ErrorResponse();

        // Assert
        assertThat(response.getTimestamp()).isNull();
        assertThat(response.getStatus()).isZero();
        assertThat(response.getError()).isNull();
        assertThat(response.getMessage()).isNull();
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Arrange
        ErrorResponse response = new ErrorResponse();
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        response.setTimestamp(timestamp);
        response.setStatus(500);
        response.setError("Internal Server Error");
        response.setMessage("An error occurred");

        // Assert
        assertThat(response.getTimestamp()).isEqualTo(timestamp);
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getError()).isEqualTo("Internal Server Error");
        assertThat(response.getMessage()).isEqualTo("An error occurred");
    }
}
