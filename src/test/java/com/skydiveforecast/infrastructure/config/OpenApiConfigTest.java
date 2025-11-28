package com.skydiveforecast.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    private final OpenApiConfig openApiConfig = new OpenApiConfig();

    @Test
    void customOpenAPI_ShouldCreateOpenAPIConfiguration() {
        // Arrange & Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getInfo()).isNotNull();
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("Location Service API");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("1.0");
        assertThat(openAPI.getInfo().getDescription()).isEqualTo("API documentation for Location Service");
    }

    @Test
    void customOpenAPI_ShouldConfigureBearerAuthentication() {
        // Arrange & Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assertThat(openAPI.getComponents()).isNotNull();
        assertThat(openAPI.getComponents().getSecuritySchemes()).containsKey("BearerAuth");
        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("BearerAuth");
        assertThat(securityScheme.getType()).isEqualTo(SecurityScheme.Type.HTTP);
        assertThat(securityScheme.getScheme()).isEqualTo("bearer");
        assertThat(securityScheme.getBearerFormat()).isEqualTo("JWT");
    }

    @Test
    void customOpenAPI_ShouldAddSecurityRequirement() {
        // Arrange & Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assertThat(openAPI.getSecurity()).isNotEmpty();
        assertThat(openAPI.getSecurity().get(0).containsKey("BearerAuth")).isTrue();
    }
}
