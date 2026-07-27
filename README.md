# outbox-support

Shared support library for implementing the transactional outbox pattern in Spring Boot services.

The package provides:

- `OutboxEventEntity` - base JPA mapped superclass for outbox event tables.
- `OutboxEventStatus` - common event statuses: `PENDING`, `PUBLISHED`, `FAILED`.
- `KafkaOnSentHandler` - helper interface for marking events as published or failed after Kafka send callbacks.

## Requirements

- Java 17
- Maven
- Spring Data JPA
- Hibernate 6
- PostgreSQL-compatible `jsonb` column for event payloads

## Installation

Add the GitHub Packages repository:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/aburov30/bankingoutboxsupport</url>
    </repository>
</repositories>
```

Add the dependency:

```xml
<dependency>
    <groupId>com.burov</groupId>
    <artifactId>outbox-support</artifactId>
    <version>0.0.1</version>
</dependency>
```

For local development with private GitHub Packages, configure Maven credentials in `~/.m2/settings.xml`:

```xml
<servers>
    <server>
        <id>github</id>
        <username>YOUR_GITHUB_USERNAME</username>
        <password>YOUR_GITHUB_TOKEN</password>
    </server>
</servers>
```

The token needs permission to read packages.

## Usage

Create a service-specific outbox entity by extending `OutboxEventEntity`:

```java
package com.example.outbox;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import outboxsupport.OutboxEventEntity;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent extends OutboxEventEntity {
}
```

Create a Spring Data repository:

```java
package com.example.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
}
```

Use `KafkaOnSentHandler` in the component that processes Kafka send results:

```java
package com.example.outbox;

import outboxsupport.KafkaOnSentHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OutboxKafkaResultHandler implements KafkaOnSentHandler {
    private final OutboxEventRepository repository;

    public OutboxKafkaResultHandler(OutboxEventRepository repository) {
        this.repository = repository;
    }

    public void handleSuccess(UUID eventId) {
        onPublish(eventId, repository);
    }

    public void handleFailure(UUID eventId, Throwable error) {
        onFailed(eventId, error, repository);
    }
}
```

## Publishing

The package is published to GitHub Packages by the workflow in `.github/workflows/publish.yml`.

Publication can be triggered manually from GitHub Actions or by creating a GitHub release. The workflow runs:

```bash
mvn --batch-mode clean deploy
```

Before publishing a new release, update the version in `pom.xml`.

## Build

```bash
mvn clean package
```
