package com.skydiveforecast.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DropzoneNotFoundExceptionTest {

    @Test
    void constructor_ShouldCreateExceptionWithMessage() {
        // Arrange
        String errorMessage = "Dropzone not found with id: 1";

        // Act
        DropzoneNotFoundException exception = new DropzoneNotFoundException(errorMessage);

        // Assert
        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }
}
