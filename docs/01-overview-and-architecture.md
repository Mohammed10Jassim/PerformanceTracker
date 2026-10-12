# 01 — Overview & Architecture

## 1. Purpose

PerformanceTracker is the backend of a Performance Management System. It stores the company
organisation, holds the performance review hierarchy for projects, tracks employee tasks against
KPAs, and computes per-assignment completion percentages. Identity and authentication are delegated
to **Keycloak**; the application is a pure **stateless JWT resource server**.

## 2. Technology stack

### Build and runtime

| Concern | Choice | Version | Where |
|---------|--------|---------|-------|
| Language | Java | 21 | `<java.version>` in `pom.xml` |
| Framework | Spring Boot | 4.1.0 | parent POM |
| Build tool | Maven | — | `pom.xml`, `mvnw`, `mvnw.cmd` |
| Servlet container | Embedded Tomcat | managed | `spring-boot-starter-web` |

### Libraries

| Library | Version | Purpose |
|---------|---------|---------|
| `spring-boot-starter-web` | managed | MVC, embedded Tomcat, Jackson **3** |
| `spring-boot-starter-validation` | managed | Jakarta Bean Validation (`jakarta.validation`) |
| `spring-boot-starter-data-jpa` | managed | Hibernate ORM, Spring Data repositories |
| `spring-boot-starter-security` | managed | Spring Security core |
| `spring-boot-starter-security-oauth2-resource-server` | managed | JWT validation via issuer / JWKS |
| `spring-boot-starter-actuator` | managed | Health/metrics endpoints |
| `org.postgresql:postgresql` | managed (`runtime`) | JDBC driver |
| `org.projectlombok:lombok` | 1.18.38 | `@Getter/@Setter/@Builder/@RequiredArgsConstructor` |
| `org.mapstruct:mapstruct` | 1.6.3 | Entity ⇄ DTO mapping (compile-time) |
| `org.projectlombok:lombok-mapstruct-binding` | 0.2.0 (`provided`) | Makes Lombok and MapStruct cooperate |
| `org.keycloak:keycloak-admin-client` | 26.0.11 | Admin REST API client (read/import Keycloak users) |
| `org.springdoc:springdoc-openapi-starter-webmvc-ui` | 2.8.13 | OpenAPI 3 + Swagger UI |
| `spring-boot-starter-test`, `spring-security-test` | managed (`test`) | Testing (currently only a context-load smoke test) |

> **Note on Jackson:** Spring Boot 4 uses **Jackson 3**, so the security handlers inject
> `tools.jackson.databind.ObjectMapper` (not `com.fasterxml.jackson.databind.ObjectMapper`).

### Infrastructure

| Service | Purpose | Dev location |
|---------|---------|--------------|
| PostgreSQL | System of record | `localhost:5432/performance_tracker`, user `postgres` |
| Keycloak | Identity & access | `http://localhost:8081`, realm `rkt` |

## 3. Architectural style

The application is a **monolithic layered REST service**. There are no microservices, no message
brokers, no scheduled jobs (except one `CommandLineRunner`, see §7).

```
HTTP request
   │
   ▼
[ Spring Security Filter Chain ]           ← JWT validated here (stateless)
   │                                       ← 401 via CustomAuthenticationEntrypoint
   │                                       ← 403 via CustomAccessDeniedHandler
   ▼
[ @RestController ]                        ← thin: @Valid, @PreAuthorize, status codes
   │
   ▼
[ @Service interface ]  →  [ @Service impl ]   ← all business logic + data scoping lives here
   │                             │
   │                             ├── [ AccessScopeService ]  role/department scope checks
   │                             ├── [ CustomUserService ]   current user from JWT
   │                             ├── [ Repository ]           Spring Data JPA + Specification
   │                             └── [ MapStruct Mapper ]    entity → response DTO
   ▼
[ JPA / Hibernate ] ──► PostgreSQL
```

### Layer responsibilities

**Controller**
- Declares the HTTP contract: path, method, query params, status codes.
- Triggers validation with `@Valid` on `@RequestBody`.
- Gates access with `@PreAuthorize("hasAnyRole(...)")`.
- Wraps successful responses (either via `ResponseEntity` + `ApiResponseBuilder`, via
  `@ResponseStatus`, or by returning the record DTO directly — the codebase uses all three styles).

**Service / ServiceImpl**
- The only place business rules live. In particular **all data scoping happens here**, not in the
  repository.
- Annotated `@Transactional` (read operations use `@Transactional(readOnly = true)`).
- Throws either the typed `BaseException` family (organisation module) or plain
  `IllegalArgumentException` / `IllegalStateException` / `SecurityException` (performance module).
  This inconsistency is real and affects HTTP status mapping — see
  [06 Error Handling](./06-error-handling.md#4-the-500-problem).

**Mapper (MapStruct)**
- Compile-time generated implementation in `target/generated-sources/annotations/...`.
- `componentModel = "spring"`, so mappers are injectable beans.
- Update-style methods use `@MappingTarget` with
  `nullValuePropertyMappingStrategy = IGNORE` to give PATCH semantics.

**Repository**
- `JpaRepository<T, Long>` plus `JpaSpecificationExecutor<T>` where dynamic filtering is needed.
- Almost no `@Query` annotations; filtering is done with `Specification` classes.

**Specification**
- `final` classes with a private constructor and **static factory methods** returning
  `Specification<T>` or `null` (null = "no constraint", which Spring Data ignores).
- `null`/blank inputs are ignored, which makes optional filters composable.

## 4. Package layout in detail

### `common` — cross-cutting concerns

| Package | Contents |
|---------|----------|
| `common.entity` | `BaseEntity` — the `@MappedSuperclass` every entity extends |
| `common.response` | `ApiResponse<T>`, `ApiResponseBuilder` — success envelope |
| `common.constants` | `ApiMessages` — 25 static message constants |
| `common.exception` | `BaseException` + 8 concrete exception types |
| `common.exception.code` | `ErrorCode` enum (44 constants, each with `code` + `defaultMessage`) |
| `common.exception.handler` | `GlobalExceptionHandler` — `@RestControllerAdvice` |
| `common.exception.response` | `ErrorResponse`, `ValidationErrorResponse` |
| `common.config` | `JpaAuditConfig` — `AuditorAware<String>` |
| `common.enums` | `EnumController` — publishes enum values to clients |
| `common.sequence` | `SequenceType`, `SequenceGenerator` (interface), `DatabaseSequenceGenerator` (unused stub) |

### `organization` — company structure

| Module | Table | Purpose |
|--------|-------|---------|
| `department` | `departments` | Departments; code auto-generated from a DB sequence |
| `designation` | `designations` | Job titles |
| `departmentDesignation` | `department_designations` | Which designations exist in which department |
| `employee` | `employees` | People; many-to-many with department-designation mappings |
| `user` | `users` | Login identity linked to one employee + Keycloak id + roles |
| `projects` | `projects`, `project_departments` | Projects and the departments participating in them |

### `performance` — review chain

| Module | Table | Purpose |
|--------|-------|---------|
| `kra` | `project_kras` | Key Result Area — belongs to one project-department |
| `kpa` | `project_kpas` | Key Performance Area — belongs to one KRA |
| `kpaEmployee` | `project_kpa_employees` | KPA ⇄ employee assignment; owns `completion_percentage` |
| `kpaTask` | `project_kpa_tasks` | Tasks under an assignment; owns `TaskStatus` |

The chain is strictly linear:

```
Project ─< ProjectDepartment >─ Department
              │
              └─< ProjectKra
                      │
                      └─< ProjectKpa
                              │
                              └─< ProjectKpaEmployee >─ Employee
                                        │
                                        └─< ProjectKpaTask
```

### `security`

| Package | Contents |
|---------|----------|
| `security.config` | `SecurityConfig` — filter chain, CORS, `JwtAuthenticationConverter` |
| `security.converter` | `KeycloakJwtRoleConverter` — JWT `resource_access` → `ROLE_*` authorities |
| `security.handler` | `CustomAuthenticationEntrypoint` (401), `CustomAccessDeniedHandler` (403) |
| `security.access` | `AccessScopeService` — role checks and department scoping |
| `security.service` | `CustomUserService` — current user/claims/authorities |
| `security.role` | `RoleEntity`, `RoleRepository`, `RoleDataInitializer` |
| `security.keycloak` | `KeycloakAdminConfig`, `KeycloakAdminProperties`, user import/sync |
| `security.controller` | `SecurityTestController` — `/security/me`, `/security/authorities` |

## 5. Data model highlights

**`BaseEntity`** (inherited by every table):

| Column | Type | Notes |
|--------|------|-------|
| `id` | `BIGINT` | PK, `IDENTITY` |
| `created_at` | `TIMESTAMP` | not null, immutable |
| `updated_at` | `TIMESTAMP` | |
| `created_by` | `VARCHAR` | immutable; JWT `email` or `SYSTEM` |
| `updated_by` | `VARCHAR` | JWT `email` or `SYSTEM` |
| `is_active` | `BOOLEAN` | not null, default `true` — **soft delete / activation flag** |

Because every entity is soft-deletable, "deactivate" endpoints set `is_active = false` instead of
issuing a `DELETE`. Nothing is ever hard-deleted; there are no `DELETE` endpoints at all.

**Two enums:**

- `ProjectStatus` — `DRAFT`, `PLANNED`, `IN_PROGRESS`, `ON_HOLD`, `COMPLETED`, `CANCELLED`, `CLOSED`
- `TaskStatus` — `PENDING`, `IN_PROGRESS`, `COMPLETED`

Full column-level detail is in [03 Database Schema](./03-database-schema.md).

## 6. Request lifecycle

1. **CORS pre-flight** — handled by the CORS configuration source (all origins, 6 methods, 4 headers).
2. **Security filter chain**
   - Path in the permit list (`/error`, `/actuator/**`, `/swagger-ui/**`, `/v3/api-docs/**`,
     `/swagger-ui.html`) → allowed.
   - Otherwise the `Authorization: Bearer <jwt>` header is required.
   - The issuer's JWKS is fetched lazily and cached; the JWT signature and expiry are validated.
   - `sub`, `email`, `given_name`, `family_name` and `resource_access` are read from the token.
3. **Authorities** — `KeycloakJwtRoleConverter` reads
   `resource_access["performance-tracker"]["roles"]` and prefixes each value with `ROLE_`.
   Realm roles (`realm_access`) and scopes are **not** read.
4. **Method security** — `@PreAuthorize` expressions evaluate against those authorities.
5. **Controller** — bean validation (`@Valid`) then delegation to the service.
6. **Service** — data-scope restrictions are appended as `Specification` predicates; business
   validations run; entities are persisted.
7. **Auditing** — `JpaAuditConfig` writes the current user's email (or `SYSTEM`) into
   `created_by` / `updated_by`.
8. **Serialisation** — Jackson 3 renders the DTO/record.
9. **On failure** — `GlobalExceptionHandler` maps the exception to an `ErrorResponse`.

## 7. Startup-time behaviour

| Runner | Effect |
|--------|--------|
| `RoleDataInitializer` (`CommandLineRunner`) | Idempotently inserts the roles `ADMIN`, `HR_ADMIN`, `MANAGER`, `EMPLOYEE` if absent |
| Hibernate DDL | `ddl-auto=update` in `dev` (creates/migrates tables); `validate` in `prod` |
| `KeycloakAdminConfig` | Builds the Keycloak admin client bean; `@PostConstruct` prints server URL, realm and client id to stdout |

## 8. Key design decisions

**Why does the app not own authentication?**
Keycloak owns users, credentials and roles. The app is a resource server: it validates the JWT
and reads roles out of the token. Identity provisioning (importing and syncing users) is done via the
Keycloak admin API using a service account — see [08](./08-keycloak-provisioning.md).

**Why are permissions enforced in services rather than only in `@PreAuthorize`?**
`@PreAuthorize` can only answer "does this role exist in the token". Most rules are about *which
rows* a caller may see ("managers only see their departments"). Those are enforced by narrowing the
`Specification` in each service implementation. See
[04 Security & Authorization](./04-security-and-authorization.md).

**Why does the performance module throw plain exceptions instead of the typed hierarchy?**
Inconsistency in the codebase. The organisation module consistently uses
`ResourceNotFoundException` / `ResourceAlreadyExistsException` etc.; the performance module uses
`IllegalArgumentException` / `IllegalStateException` / `SecurityException`. Consequence: business
errors in the performance module surface as **HTTP 500**. This is documented as a defect in
[10 Known Issues](./10-known-issues.md#1-performance-module-errors-return-http-500).

**Why no hard deletes?**
Every entity carries `is_active`, and each module exposes `activate` / `deactivate` endpoints.
Historical performance data must remain intact, so rows are never removed.

## 9. Cross-cutting conventions

| Convention | Rule |
|------------|------|
| Soft delete | `is_active` flag; `deactivate` sets `false`, `activate` sets `true` |
| Naming | snake_case tables/columns; PascalCase entities; `XxxEntity`, `XxxMapper`, `XxxSpecification` suffixes |
| Paging | All list endpoints accept Spring `Pageable` and return `Page<T>`; `activeOnly` defaults to `false` |
| PATCH semantics | `null` fields in the request body are ignored (`nullValuePropertyMappingStrategy = IGNORE` or explicit null checks) |
| Trim | Names are trimmed before uniqueness checks in most create paths (inconsistent — see known issues) |
| Transactions | Service-level `@Transactional`; read paths `readOnly = true` |
| Lazy loading | `spring.jpa.open-in-view=false`, so all lazy relations must be touched inside the service transaction |
| Uniqueness | Enforced both in service code (friendly errors) and by DB unique constraints (safety net) |

## 10. Next documents

- How to actually run it → [02 Setup & Configuration](./02-setup-and-configuration.md)
- Tables and columns → [03 Database Schema](./03-database-schema.md)
- Who can do what → [04 Security & Authorization](./04-security-and-authorization.md)
- Endpoints → [05 API Reference](./05-api-reference.md)