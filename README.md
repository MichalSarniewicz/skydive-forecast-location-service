# Skydive Forecast - Location Service

[![CI Pipeline](https://github.com/MichalSarniewicz/skydive-forecast-location-service/actions/workflows/ci.yml/badge.svg)](https://github.com/MichalSarniewicz/skydive-forecast-location-service/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/MichalSarniewicz/skydive-forecast-location-service/branch/master/graph/badge.svg)](https://codecov.io/gh/MichalSarniewicz/skydive-forecast-location-service)
[![Java](https://img.shields.io/badge/Java-21-green?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue?logo=docker)](https://www.docker.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)


Microservice for managing dropzone locations in the Skydive Forecast system, built with Spring Boot and hexagonal architecture.

## Overview

This service is responsible for managing skydiving dropzone locations, including their geographical coordinates, city information, and wingsuit compatibility. It provides RESTful APIs for CRUD operations and location-based queries.

## Architecture

The service follows **Hexagonal Architecture** (Ports and Adapters) pattern:

- **Domain Layer**: Core business logic and entities
  - `DropzoneEntity`: Domain model for dropzone locations
  - `DropzoneNotFoundException`: Custom exception for missing dropzones
  - **Ports**: Use case interfaces defining business operations
    - `CreateDropzoneUseCase`
    - `GetDropzoneUseCase`
    - `GetAllDropzonesUseCase`
    - `UpdateDropzoneUseCase`
    - `DeleteDropzoneUseCase`
    - `FindDropzonesByCityUseCase`

- **Infrastructure Layer**: Technical implementations
  - **Adapters**: REST controllers, DTOs, and mappers
  - **Security**: JWT authentication and permission-based authorization
  - **Configuration**: Exception handling, OpenAPI documentation
  - **AOP**: Permission security aspect

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.5.6
- **Spring Security**: JWT-based authentication
- **Spring Data JPA**: Database access with Hibernate
- **PostgreSQL**: Primary database
- **Redis**: Caching layer
- **Liquibase**: Database migration management
- **MapStruct**: DTO mapping
- **SpringDoc OpenAPI**: API documentation (Swagger)
- **Lombok**: Boilerplate code reduction
- **JUnit 5 & Mockito**: Unit testing
- **Testcontainers**: Integration testing
- **Monitoring**: Actuator, Prometheus, Grafana, Loki, Zipkin
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.x
- PostgreSQL 12+
- Redis 6+

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd skydive-forecast-location-service
```

2. Configure the database and Redis in `application.yaml` or use environment-specific profiles

3. Build the project:
```bash
./mvnw clean install
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

The service will start on port **8083** by default.

## Configuration

The application supports multiple profiles for different environments:

- `dev` (default): Development environment
- `test`: Testing environment
- `prod`: Production environment

## API Endpoints

### Dropzone Management

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/locations/dropzones` | Create a new dropzone | Yes |
| GET | `/api/locations/dropzones/{id}` | Get dropzone by ID | Yes |
| GET | `/api/locations/dropzones` | Get all dropzones | Yes |
| PUT | `/api/locations/dropzones/{id}` | Update dropzone | Yes |
| DELETE | `/api/locations/dropzones/{id}` | Delete dropzone | Yes |
| GET | `/api/locations/dropzones/city/{city}` | Find dropzones by city | Yes |

### API Documentation

- **Swagger UI**: `http://localhost:8083/swagger-ui.html`
- **OpenAPI Docs**: `http://localhost:8083/v3/api-docs/locations`

## Security

The service uses JWT Bearer token authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

### Permission-Based Authorization

The service implements fine-grained permission control using AOP:
- `@PermissionSecurity` annotation on methods
- Permission validation through JWT token claims
- Access denied exceptions for unauthorized requests

## Database Schema

### Dropzones Table

```sql
CREATE TABLE skydive_forecast_location.dropzones (
    id                   BIGSERIAL PRIMARY KEY,
    name                 VARCHAR(255) UNIQUE NOT NULL,
    city                 VARCHAR(255) NOT NULL,
    latitude             DECIMAL(10, 8) NOT NULL,
    longitude            DECIMAL(11, 8) NOT NULL,
    is_wingsuit_friendly BOOLEAN NOT NULL DEFAULT false
);
```

**Indexes:**
- `idx_dropzone_city`: On city column for fast city-based queries
- `idx_dropzone_coordinates`: On latitude and longitude for geospatial queries


## Development

### Building

```bash
./mvnw clean package
```

### Running Tests

```bash
./mvnw test
```

### Running with Docker

```bash
docker-compose up -d
```

### Running the JAR

After building, you can run the application using:

```bash
java -jar target/skydive-forecast-location-service-1.0.0-SNAPSHOT.jar
```

## Testing

The project includes comprehensive unit tests following the AAA (Arrange-Act-Assert) pattern:

- **Controller Tests**: REST endpoint testing with MockMvc
- **Service Tests**: Business logic validation
- **Security Tests**: Authentication and authorization testing
- **DTO Tests**: Validation and mapping tests
- **Exception Handler Tests**: Error handling verification

Run tests with coverage:
```bash
./mvnw clean test jacoco:report
```

## Integration with Skydive Forecast Ecosystem

This service is part of the Skydive Forecast microservices architecture:

- **Gateway**: Routes requests from `http://localhost:8080/api/locations/**` to this service
- **Users Service** (Port 8081): Provides authentication and user management
- **Analyses Service** (Port 8082): Consumes location data for forecast analysis

## Error Handling

The service provides comprehensive error handling with standardized error responses:

- `400 Bad Request`: Validation errors, malformed requests
- `401 Unauthorized`: Authentication failures
- `403 Forbidden`: Permission denied
- `404 Not Found`: Resource not found
- `409 Conflict`: Data integrity violations
- `500 Internal Server Error`: Unexpected errors

## Monitoring

The service includes comprehensive monitoring capabilities:

### Metrics (Prometheus)

- **Endpoint**: `http://localhost:8083/actuator/prometheus`
- **Metrics**: JVM, HTTP requests, database connections, Kafka consumers, Redis cache

### Health Checks

- **Endpoint**: `http://localhost:8083/actuator/health`

### Logs (Loki)

Application logs are automatically sent to Loki for centralized log aggregation.

### Distributed Tracing (Zipkin)

- **Endpoint**: `http://localhost:9411`
- **Traces**: Request flows across services with timing information
- **Sampling**: 100% of requests traced (configurable)

### Grafana Dashboards

Access Grafana at `http://localhost:3000` (admin/admin)

Recommended dashboard: Import ID **11378** (JVM Micrometer)

## License

This project is part of the Skydive Forecast system.

## Contact

For questions or support, please contact the development team.
