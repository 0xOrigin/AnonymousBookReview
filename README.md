# Anonymous Book Review

A book review web application built with Spring Boot and PostgreSQL.

## Features

- RESTful API endpoints
- Database versioning with Liquibase
- Containerized with Docker
- PgAdmin for database management

## Prerequisites

- Java 25
- Docker and Docker Compose

## Getting Started

### 1. Environment Setup

Create a `.env` file in the project root with the following variables:

```env
# Database Configuration
POSTGRES_DB=anonymous_book_review
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_secure_password
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
```

### 2. Start Services

Before running the application, ensure Docker is running, then start the required services:

```bash
docker-compose up -d
```

This will start:
- PostgreSQL 18.0 on port 5432
- PgAdmin on port 5050 (optional)

### 3. Build and Run the Application

```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## Configuration

### Application Properties

The main configuration can be found in `src/main/resources/application.properties`. Key configurations include:

- Database connection settings
- JPA/Hibernate properties
- Liquibase changelog location
- Pagination defaults

### Seed Data

To enable initial data population, set the following property in `application.properties`:

```properties
app.seed.run=true
```

## API Documentation

API documentation is available at:
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Postman collection in `postman-collection` directory

## License

This project is licensed under the terms of the Apache License 2.0 license. See the [LICENSE](LICENSE) file for details.
