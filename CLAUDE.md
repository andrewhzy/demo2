# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Boot 3.5.6 application with Elasticsearch integration for managing blocked users. Uses Java 21 and Maven.

## Build & Run Commands

**Build:**
```bash
mvn clean install
```

**Run application:**
```bash
mvn spring-boot:run
```

**Run tests:**
```bash
mvn test
```

**Run single test class:**
```bash
mvn test -Dtest=ClassName
```

**Run single test method:**
```bash
mvn test -Dtest=ClassName#methodName
```

## Architecture

This application follows a **layered architecture** with clear separation of concerns:

- **REST Layer** (`rest/`): Controllers exposing HTTP endpoints
- **Application Layer** (`application/`): Service layer with business logic and DTOs
- **Domain Layer** (`domain/`): Core domain entities (Elasticsearch documents)
- **Infrastructure Layer** (`infrastructure/`): Repositories and data access

**Key architectural points:**

1. **Elasticsearch as primary datastore**: The `BlockedUser` entity is stored in Elasticsearch index `glean-gateway-blocked-users`
2. **Custom SSL configuration**: `ElasticsearchConfig` uses a trust-all SSL context with `NoopHostnameVerifier` - suitable for development/testing environments
3. **Lombok integration**: All classes use Lombok annotations (`@Data`, `@Builder`, etc.) with proper Maven compiler configuration for annotation processing

## Configuration

Elasticsearch connection settings are in `src/main/resources/application.yaml`:
- Application name: `blocked-users-service`
- Default Elasticsearch URI: `https://localhost:9200`
- Requires `spring.elasticsearch.username` and `spring.elasticsearch.password` properties

## API Endpoints

**Block a user:**
```
POST /api/blocked-users
Content-Type: application/json

{
  "userId": "string",
  "blockedBy": "string",
  "blockReason": "string"
}
```

**Unblock a user:**
```
DELETE /api/blocked-users/{id}
```

## Dependencies

- Spring Boot Web
- Spring Data Elasticsearch
- Lombok (annotation processing configured in `maven-compiler-plugin`)
