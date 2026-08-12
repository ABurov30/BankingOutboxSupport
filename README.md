# outbox-support

Shared Java 17 library for implementing the transactional outbox pattern and
idempotent event consumption in Spring Boot services.

## What Is Included

- `OutboxEventEntity` - base JPA mapped superclass for service-specific outbox
  event tables.
- `OutboxEventStatus` - common event states: `PENDING`, `PUBLISHED`, `FAILED`.
- `KafkaOnSentHandler` - helper interface for updating outbox events after Kafka
  send callbacks.
- `ProcessedEvent` - base mapped superclass for processed-event tables.
- `BaseProcessedEventRepository` - Spring Data repository base for processed
  events.
- `IdempotencyHandler` - helper interface for checking and storing processed
  event keys.

## Documentation

- [Usage guide](docs/usage.md) - dependency setup, entity examples, Kafka send
  handling, and idempotency support.
- [Development guide](docs/development.md) - project layout, verification,
  release, and maintenance notes.
- [Agent instructions](AGENTS.md) - repository-specific guidance for coding
  agents.

## Requirements

- Java 17
- Maven
- Spring Data JPA
- Hibernate 6
- PostgreSQL-compatible `jsonb` column support for outbox payloads

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
    <version>0.0.5</version>
</dependency>
```

For private GitHub Packages access, configure Maven credentials in
`~/.m2/settings.xml`:

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

## Quick Start

Create a service-specific outbox entity:

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

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
}
```

Use `KafkaOnSentHandler` from the component that receives Kafka send results.
See the [usage guide](docs/usage.md) for a complete example and retry behavior.

## Build

```bash
mvn clean package
```

Use the full verification command before publishing or opening a pull request:

```bash
mvn --batch-mode verify
```
