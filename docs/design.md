# Blocked Users Service Design

## DDD Layers

### 1. Domain Layer (core business logic)
- `BlockedUser` - Entity/Aggregate Root (with @Document annotation)
  - Fields: id, userId, blockedAt, blockedBy, blockReason

### 2. Application Layer (use cases)
- `BlockUserService` - Application service
  - `blockUser(BlockUserRequest)` - Block a user
  - `unblockUser(String id)` - Unblock a user
- DTOs:
  - `BlockUserRequest` - Input DTO
  - `BlockUserResponse` - Output DTO

### 3. Infrastructure Layer (technical implementations)
- `BlockedUserRepository` - Spring Data Elasticsearch repository interface
  - Extends `ElasticsearchRepository<BlockedUser, String>`
  - Spring Boot auto-implements CRUD methods

### 4. Interface Layer (REST API)
- `BlockedUserController` - REST endpoints

## Project Structure
```
src/main/java/com/example/demo/
├── domain/
│   └── BlockedUser.java
├── application/
│   ├── BlockUserService.java
│   └── dto/
│       ├── BlockUserRequest.java
│       └── BlockUserResponse.java
├── infrastructure/
│   └── BlockedUserRepository.java
└── rest/
    └── BlockedUserController.java

src/main/resources/
└── application.yml
```

## Domain Model

### BlockedUser Entity
```java
@Document(indexName = "blocked-users")
public class BlockedUser {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Date)
    private Instant blockedAt;

    @Field(type = FieldType.Keyword)
    private String blockedBy;

    @Field(type = FieldType.Text)
    private String blockReason;
}
```

### Repository Interface
```java
public interface BlockedUserRepository extends ElasticsearchRepository<BlockedUser, String> {
    // Spring Data auto-implements:
    // - save(BlockedUser)
    // - deleteById(String)
    // - findById(String)
    // - findAll()
    // - etc.

    // Optional: custom query methods
    Optional<BlockedUser> findByUserId(String userId);
}
```

## API Endpoints
```
POST   /api/blocked-users      - Block a user
DELETE /api/blocked-users/{id} - Unblock a user
```

### Request/Response Examples

**Block User Request:**
```json
{
  "userId": "user123",
  "blockedBy": "admin",
  "blockReason": "Violation of terms"
}
```

**Block User Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "userId": "user123",
  "blockedAt": "2025-10-08T10:30:00Z",
  "blockedBy": "admin",
  "blockReason": "Violation of terms"
}
```

## Elasticsearch Index

**Index Name:** `blocked-users`

**Note:** Index mapping is automatically created by Spring Data Elasticsearch based on `@Document` and `@Field` annotations on the `BlockedUser` entity.

## Configuration (application.yml)
```yaml
spring:
  application:
    name: blocked-users-service

  elasticsearch:
    uris: http://localhost:9200
    # Optional: username and password if needed
    # username: elastic
    # password: changeme
```

## Dependencies (pom.xml)
```xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Data Elasticsearch -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
    </dependency>

    <!-- Lombok (optional, for cleaner code) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

## Key Benefits of Spring Data Elasticsearch

1. **No manual repository implementation needed** - Spring auto-generates based on interface
2. **Automatic index mapping** - Created from entity annotations
3. **Less boilerplate code** - No need for ElasticsearchConfig or manual client setup
4. **Query derivation** - Method names like `findByUserId` automatically generate queries
