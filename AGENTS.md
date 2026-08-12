# AGENTS.md

Repository-specific instructions for coding agents working on `outbox-support`.

## Start Here

- Read [README.md](README.md) for the project overview and public dependency
  coordinates.
- Read [docs/usage.md](docs/usage.md) before changing public APIs or examples.
- Read [docs/development.md](docs/development.md) before changing build,
  publishing, or repository maintenance behavior.

## Project Context

This repository is a Maven library, not a runnable service. It provides shared
JPA mapped superclasses and helper interfaces under the `outboxsupport` package
for services that implement:

- transactional outbox publishing;
- Kafka send result handling;
- idempotent event consumption.

The published artifact is:

```xml
<groupId>com.burov</groupId>
<artifactId>outbox-support</artifactId>
```

## Working Rules

- Keep changes scoped to the library surface requested by the task.
- Preserve the package name `outboxsupport` unless the task explicitly requires
  a breaking API migration.
- Do not add service-specific business logic to this library.
- Keep examples and documentation aligned with the current version in
  `pom.xml`.
- Use the existing Java style: two-space indentation, concise classes, and
  straightforward public API classes/interfaces.
- Do not commit generated artifacts from `target/` or IDE metadata.
- Update `README.md` and the relevant files under `docs/` when public behavior,
  setup, or release flow changes.

## Verification

Run the project verification command after code changes:

```bash
mvn --batch-mode verify
```

Documentation-only changes do not require a Maven build unless they also modify
examples that should be checked against source code.
