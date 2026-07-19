# AGENTS.md

## Build & Run

```bash
./mvnw clean compile       # Compiles + generates OpenAPI DTOs/controller interfaces
./mvnw spring-boot:run     # Starts the app (requires PostgreSQL + Kafka)
./mvnw test               # Runs tests
```

**Required order:** `compile` before any code references generated classes.

## OpenAPI Code Generation (Critical)

DTOs and the `OrderControllerApi` interface are **auto-generated** from `src/main/resources/order-openAPI.yaml`. Generated files land in `target/generated-sources/openapi/`.

- **Never edit** files in `target/generated-sources/` — they are overwritten on compile.
- To change API contracts or DTOs, edit `order-openAPI.yaml`, then recompile.
- `OrderControllerImpl` implements the generated `OrderControllerApi` interface.

## Package Structure

- Root package: `ru.galuameev.order_saga` (note underscore, not hyphen)
- Controllers: `ru.galuameev.order_saga.controller`
- Services: `ru.galuameev.order_saga.service`
- DTOs (generated): `ru.galuameev.order_saga.dto`

## Infrastructure Dependencies

- **PostgreSQL** — required at runtime (default: `localhost:5432/orders`)
- **Kafka** — required at runtime (default: `localhost:9092`)
- **Liquibase** — handles DB migrations (`src/main/resources/db/changelog/`)

Configure via environment variables: `DB_HOST_PORT`, `DB_USER`, `DB_PASSWORD`, `KAFKA_BOOTSTRAP_SERVERS`.

## Project Status

This is a **skeleton project**. Service methods are stubs (return null). The controller delegates to generated default methods which return `501 NOT_IMPLEMENTED`.
