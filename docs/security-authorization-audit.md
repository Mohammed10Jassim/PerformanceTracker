# Security Authorization Audit — Initial Pass

## Changes applied

1. Replaced the obsolete `HR_MANAGER` role name with `HR_ADMIN` in `ProjectKpaController` annotations and the related `EmployeeServiceImpl` comment.
2. Updated `AccessScopeServiceImpl.hasVerifiedRole` so Keycloak JWT roles are the authorization source of truth. Database role assignments remain available as legacy/reference data but no longer gate this check.
3. Added explicit `ADMIN`, `HR_ADMIN`, or `MANAGER` role restrictions to task create, update, activate, and deactivate endpoints in `ProjectKpaTaskController`.

## Findings requiring a follow-up decision or validation

- Read endpoints in employee, assignment, task, KRA, KPA, project, and master-data controllers must be checked together with their service-level scope checks. An absent controller annotation does not alone prove an access-control defect when the service enforces scope, but service enforcement must be confirmed for every path.
- CORS currently permits all origin patterns with credentials. Before tightening it, configure the actual trusted frontend origins using deployment configuration; do not guess the production frontend URL.
- `SecurityConfig` allows Swagger and actuator paths without authentication. Confirm which actuator endpoints are exposed and whether Swagger should remain public outside development.
- `SecurityException` thrown by service scope checks should be checked against the exception handlers so denied access consistently returns HTTP 403 rather than HTTP 500.
- The `MANAGER` permission model should be validated for department scope and for each operation, especially changes to employee assignments and tasks.

## Validation status

Static source edits were applied. Automated Maven tests could not be run in this environment because Maven was unavailable and the Maven Wrapper could not download its distribution. Run `./mvnw test` locally before merging.


## Iteration 2 — read scopes, 403 responses, CORS, Actuator and Swagger

Changes made in this iteration:

- Added explicit application-role checks to employee read endpoints and project KPA employee assignment read endpoints. Their service methods retain self/department-scope filtering.
- Added explicit application-role checks to project and project-department read endpoints; their service methods retain scope validation.
- Replaced raw `java.lang.SecurityException` throws in access-denial paths with Spring Security `AccessDeniedException`, so the existing exception handler can return HTTP 403 instead of falling through to a generic 500 response.
- Restricted public Actuator access to `/actuator/health` and `/actuator/info`; limited exposed Actuator endpoints to `health,info`.
- Restricted Swagger UI and OpenAPI documents to `ADMIN` and `HR_ADMIN`.
- Replaced wildcard CORS origin patterns with an explicit configurable comma-separated allowlist via `CORS_ALLOWED_ORIGINS`. Default development origins are `http://localhost:3000` and `http://localhost:5173`. Set the environment variable to the actual trusted frontend origin(s) for each environment.

## Validation required

Automated build/tests were not run in this environment. After extracting the ZIP, run `./mvnw test` (or `mvn test` if Maven is installed). Also verify in Postman: unauthenticated protected endpoint returns 401; an authenticated token without the required role returns 403; an employee can read only their own employee record/assignments; a manager cannot read another department's records; admin/HR admin can perform authorized management operations; Swagger is accessible only to admin/HR admin; and only configured CORS origins are accepted.

The CORS defaults are suitable only for common local development ports. Configure `CORS_ALLOWED_ORIGINS` before running a frontend on another origin or deploying.


## Iteration 3 — production CORS failure on 192.168.0.57 and wider review

### Root cause of the CORS error

Iteration 2 replaced the wildcard CORS policy with an allowlist whose default was only
`http://localhost:3000,http://localhost:5173`. Unless `CORS_ALLOWED_ORIGINS` was exported on the
production host, a frontend served from `http://192.168.0.57[:port]` is rejected at preflight.
A second, independent CORS source exists: the Keycloak client `performance-tracker-frontend`
only lists `localhost:3000`, `127.0.0.1:3000` and `https://192.168.0.213:3000` in Web Origins /
Redirect URIs, so token requests from 192.168.0.57 are rejected by Keycloak itself.

### Changes applied (no functional behaviour changed for existing origins)

- `SecurityConfig`: `setAllowedOrigins` → `setAllowedOriginPatterns` (exact origins still match;
  `http://host:*` patterns now also work), trailing `/` on configured origins is tolerated,
  allowed request headers set to `*` (preflights no longer fail on extra headers), preflight cached
  for 1 hour, `Content-Disposition` exposed.
- Default CORS allowlist now also includes `http(s)://192.168.0.57` on any port
  (`application.properties`, `application-dev.properties`, `application-prod.properties`).
  `CORS_ALLOWED_ORIGINS` still overrides it.
- `ProjectKpaController`: added `@Valid` to create/update so the existing DTO constraints apply
  (a missing `kpaName` previously caused a NullPointerException → HTTP 500).

### Must do on the server / Keycloak (outside the code)

1. Keycloak → realm `rkt` → Clients → `performance-tracker-frontend`: add the real frontend origin
   (e.g. `http://192.168.0.57:3000`) to **Web origins** and `<origin>/*` to **Valid redirect URIs**
   (and post-logout redirect URIs).
2. Run the jar with `SPRING_PROFILES_ACTIVE=prod` — `application.properties` hard-codes `dev`, which
   enables TRACE security logging, SQL bind-parameter logging and `ddl-auto=update`.
3. `KEYCLOAK_ISSUER_URI` must equal the `iss` claim of tokens the browser receives (same host/port the
   frontend uses to reach Keycloak), otherwise every call returns 401.
4. If nginx/Apache sits in front of the API, do not add CORS headers there as well (duplicate
   `Access-Control-Allow-Origin` headers are also reported as a CORS error).

### Findings not changed (need a decision)

- CRITICAL: `docker/keycloak/realm-data/rkt-realm.json` contains the `performance-tracker-admin`
  client secret and password hashes for 151 users, and the Dockerfile bakes it into the image.
  Rotate the client secret and strip credentials from the export.
- Realm: `sslRequired=none`, brute-force protection off, `performance-tracker` is a public client with
  Direct Access Grants enabled (password grant), `security-admin-console`/HTTP-only deployment.
- `GlobalExceptionHandler` returns `ex.getMessage()` for 500s, and `IllegalArgumentException`,
  `IllegalStateException`, `RuntimeException("User not found")` and several `ErrorCode`s
  (e.g. `EMPLOYEE_CODE_ALREADY_EXISTS`, `DEPARTMENT_DESIGNATION_*`, `DESIGNATION_ALREADY_ACTIVE`)
  fall through to HTTP 500 instead of 400/404/409.
- `ProjectKpaTaskController.updateTask` is limited to ADMIN/HR_ADMIN/MANAGER although the service
  contains an EMPLOYEE ownership path — confirm whether employees should update their own task status.
- `hasManagementRole` recognises `PROJECT_MANAGER`/`PROJECT_LEAD`, which exist neither in Keycloak
  nor in any `@PreAuthorize` list.
- NullPointerException (500) when an authenticated user has no linked employee in EMPLOYEE/MANAGER
  paths (`getEmployees`, `getProjectKpas`, `getAssignments`, KPA/assignment read checks).
- `CreateEmployeeRequestDto.lastName` is optional but `employees.last_name` is NOT NULL → 500.
- Department, designation and department-designation reads have no role check (any token with a
  valid signature can read them); `SecurityTestController` exposes `/security/**` and uses
  `System.out.println` (also `KeycloakAdminConfig`).
- `UserServiceImpl.updateUser` updates Keycloak before the DB; a DB failure leaves them out of sync.
- `pom.xml`: springdoc-openapi 2.8.x targets Spring Boot 3; Spring Boot 4 needs springdoc 3.x.
- `KeycloakUserServiceImpl.toResponse` makes one extra Admin API call per user (N+1) for
  `/keycloak/users`, `/sync-users`, `/verify-users`.


## Iteration 4 — fixes for all Iteration 3 findings

| Finding | Fix |
|---------|-----|
| Secrets in realm export | Removed `performance-tracker-admin` secret, password hashes of 151 users and the realm signing/encryption keys from `rkt-realm.json`. Keycloak generates new ones on import. Running Keycloak: see `05-production-deployment.md` §3. |
| Realm hardening (export) | `sslRequired=external`, brute-force detection on, `performance-tracker-admin` limited to client credentials, `192.168.0.57:3000` added to the frontend client's web origins / redirect URIs. `performance-tracker` keeps Direct Access Grants because the documented Postman testing flow depends on it. |
| Wrong HTTP status codes | `GlobalExceptionHandler`: every `ErrorCode` mapped; `IllegalArgumentException`/`IllegalStateException` → 404 ("not found"), 409 ("already exists/assigned") or 400, same message text; `DataIntegrityViolationException` → 409; unknown URL → 404; wrong method → 405; wrong content type → 415. |
| 500 responses leaked internals | Generic message + trace ID returned; details only in the server log. |
| Valid token but no PMS user → 401 | Now 403 with "account is not linked" message, so the frontend does not drop a valid session. |
| `RuntimeException` in `UserServiceImpl` | `ResourceNotFoundException` (`USER_NOT_FOUND`, `ROLE_NOT_FOUND`) → 404. |
| Keycloak updated before DB | DB change saved and flushed first; a Keycloak failure rolls the transaction back. |
| Employees could not update their tasks | `PATCH /project-kpa-tasks/update/{id}` allows EMPLOYEE for tasks on their own assignment; employees may change `status` only (renaming / description edits return 403). |
| `PROJECT_MANAGER` / `PROJECT_LEAD` | Removed from `hasManagementRole`; only `MANAGER` exists. |
| NullPointerException without employee link | Guards added in employee, KPA and KPA-assignment list/read paths and `canAccessDepartment`. |
| `lastName` optional vs NOT NULL column | Missing last name stored as empty string on create. |
| Unrestricted reads | GET endpoints of departments, designations, department-designations, KRAs, KPAs and tasks now require ADMIN, HR_ADMIN, MANAGER or EMPLOYEE (data scoping in services unchanged). |
| `System.out.println` | Replaced with SLF4J logging; `/security/me` no longer returns 500 for users without a family name. |
| springdoc 2.8.13 on Boot 4 | Upgraded to springdoc-openapi 3.0.3 (Boot 4 line). |
| Keycloak N+1 | `findAllUsers` loads role membership once per client role, with fallback to the old per-user lookup. |

Not compiled in the authoring environment (no Maven repository access). Run `./mvnw clean verify`.
