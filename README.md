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

The service follows **Hexagonal Architecture** (Ports and Adapters) pattern with strict layer separation:

### Domain Layer (Core Business Logic)
- **Models**:
  - `Dropzone`: Pure domain model (immutable, no framework dependencies)
  - `DropzoneEntity`: JPA entity for persistence (infrastructure concern)
- **Exceptions**: `DropzoneNotFoundException`
- **Ports**:
  - **Input Ports** (Use Cases):
    - `CreateDropzoneUseCase`
    - `GetDropzoneUseCase`
    - `GetAllDropzonesUseCase`
    - `UpdateDropzoneUseCase`
    - `DeleteDropzoneUseCase`
    - `FindDropzonesByCityUseCase`
  - **Output Ports**:
    - `DropzoneRepositoryPort`: Repository abstraction

### Application Layer (Use Case Implementations)
- Service implementations that orchestrate domain logic
- Transaction management
- Cache management

### Infrastructure Layer (Technical Implementations)
- **Inbound Adapters**:
  - REST controllers (`DropzoneController`)
  - DTOs (`DropzoneRequest`, `DropzoneResponse`)
  - DTO mappers (MapStruct)
- **Outbound Adapters**:
  - `DropzoneRepositoryAdapter`: Implements `DropzoneRepositoryPort`
  - `DropzoneJpaRepository`: Spring Data JPA repository
  - Entity mappers (MapStruct)
- **Security**:
  - JWT authentication filter
  - Permission-based authorization with AOP
  - `@PermissionSecurity` annotation
- **Configuration**: Exception handling, OpenAPI, caching

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.5.6
- **Spring Cloud Config Client**: Centralized configuration
- **Spring Cloud Consul Discovery**: Service discovery and registration
- **Spring Security**: JWT-based authentication
- **Spring Data JPA**: Database access with Hibernate
- **PostgreSQL**: Primary database
- **Redis**: Caching layer
- **Apache Kafka**: Event streaming
- **Liquibase**: Database migration management
- **MapStruct**: DTO mapping
- **SpringDoc OpenAPI**: API documentation (Swagger)
- **Lombok**: Boilerplate code reduction
- **JUnit 5 & Mockito**: Unit testing
- **Testcontainers**: Integration testing
- **Monitoring**: Actuator, Prometheus, Grafana, Loki, Tempo
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

2. Ensure required services are running:
   - **Consul** (Port 8500) - Service discovery
   - **Config Server** (Port 8888) - Configuration
   - **PostgreSQL** (Port 5432) - Database
   - **Redis** (Port 6379) - Cache
   - **Kafka** (Port 9092) - Messaging

3. Build the project:
```bash
./mvnw clean install
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

The service will:
- Start on port **8083**
- Register itself in Consul
- Load configuration from Config Server

## Configuration

The application supports multiple profiles for different environments:

- `dev` (default): Development environment
- `test`: Testing environment
- `prod`: Production environment

## API Endpoints

### Dropzone Management

| Method | Endpoint                               | Description | Permission Required |
|--------|----------------------------------------|-------------|---------------------|
| POST | `/api/locations/dropzones`          | Create a new dropzone | DROPZONE_CREATE |
| GET | `/api/locations/dropzones/{id}`        | Get dropzone by ID | DROPZONE_VIEW |
| GET | `/api/locations/dropzones`             | Get all dropzones | DROPZONE_VIEW |
| PUT | `/api/locations/dropzones/{id}`        | Update dropzone | DROPZONE_UPDATE |
| DELETE | `/api/locations/dropzones/{id}`        | Delete dropzone | DROPZONE_DELETE |
| GET | `/api/locations/dropzones/city/{city}` | Find dropzones by city | DROPZONE_VIEW |

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
- `@PermissionSecurity` annotation on controller methods
- `PermissionSecurityAspect` intercepts annotated methods
- Permission validation through JWT token claims
- Permissions extracted from `CustomUserPrincipal`
- Access denied exceptions for unauthorized requests

**Required Permissions:**
- `DROPZONE_CREATE`: Create new dropzones
- `DROPZONE_VIEW`: View dropzone information
- `DROPZONE_UPDATE`: Update existing dropzones
- `DROPZONE_DELETE`: Delete dropzones

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

- **Controller Tests**: REST endpoint testing with mocked use cases and mappers
- **Service Tests**: Business logic validation with domain models
- **Repository Adapter Tests**: Persistence layer with entity mapping
- **Mapper Tests**: MapStruct DTO and entity mapping validation
- **Security Tests**: Authentication and authorization testing
- **AOP Tests**: Permission security aspect validation
- **Exception Handler Tests**: Error handling verification

**Test Coverage**: All layers tested in isolation with proper mocking

Run tests with coverage:
```bash
./mvnw clean test jacoco:report
```

## Key Design Decisions

### Separation of Domain and Persistence
- **Domain Model** (`Dropzone`): Pure business object, immutable, framework-agnostic
- **JPA Entity** (`DropzoneEntity`): Persistence model with JPA annotations
- **Mapping**: MapStruct handles conversion between domain and entity

### Dependency Direction
- Domain layer has **zero dependencies** on infrastructure
- Use cases work with domain models only
- Controllers and repositories adapt between DTOs/entities and domain models
- Follows **Dependency Inversion Principle**

### Benefits
- **Testability**: Domain logic tested without database or framework
- **Flexibility**: Easy to change persistence or API without affecting business logic
- **Maintainability**: Clear separation of concerns
- **SOLID Principles**: Strict adherence to clean architecture principles

## Integration with Skydive Forecast Ecosystem

This service is part of the Skydive Forecast microservices architecture:

- **Gateway**: Discovers this service via Consul and routes requests from `http://localhost:8080/api/locations/**`
- **Users Service** (Port 8081): Provides authentication and user management
- **Analyses Service** (Port 8082): Consumes location data for forecast analysis
- **Consul**: Service registry at `http://localhost:8500`
- **Config Server**: Configuration management at `http://localhost:8888`

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

### Distributed Tracing (Tempo)

- **Endpoint**: `http://localhost:4318`
- **Traces**: Request flows across services with timing information
- **Sampling**: 100% of requests traced (configurable)

### Grafana Dashboards

Access Grafana at `http://localhost:3000` (admin/admin)

Recommended dashboard: Import ID **11378** (JVM Micrometer)

## License

This project is part of the Skydive Forecast system.

## Contact

For questions or support, please contact the development team.
