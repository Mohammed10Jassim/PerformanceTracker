# Performance Tracker — Documentation

Complete technical documentation for the **PerformanceTracker** Performance Management System (PMS)
backend service.

| # | Document | Contents |
|---|----------|----------|
| 01 | [Overview & Architecture](./01-overview-and-architecture.md) | What the system does, tech stack, package layout, layered design, request lifecycle |
| 02 | [Setup & Configuration](./02-setup-and-configuration.md) | Prerequisites, PostgreSQL, Keycloak realm and clients, environment variables, profiles, running, Postman |
| 03 | [Database Schema](./03-database-schema.md) | ERD, every table, column, constraint, index, sequence |
| 04 | [Security & Authorization](./04-security-and-authorization.md) | JWT resource server, role model, per-role data scope, access matrix |
| 05 | [API Reference](./05-api-reference.md) | All 68 endpoints with roles, params, request/response bodies |
| 06 | [Error Handling](./06-error-handling.md) | Response envelopes, exception hierarchy, full `ErrorCode` table, HTTP status mapping |
| 07 | [Business Rules](./07-business-rules.md) | Validation rules, project status machine, task rules, completion-percentage formula |
| 08 | [Keycloak User Provisioning](./08-keycloak-provisioning.md) | Client setup, one-time import, Keycloak → PMS sync, role assignment |
| 09 | [Development Guide](./09-development-guide.md) | Conventions, how to add a module, build/run/test, troubleshooting |
| 10 | [Known Issues](./10-known-issues.md) | Defects and inconsistencies found during documentation |

---

## 1. Quick facts

| Item | Value |
|------|-------|
| Artifact | `com.performanceTrackingSystem:PerformanceTracker:0.0.1-SNAPSHOT` |
| Framework | Spring Boot **4.1.0** |
| Language | Java **21** |
| Build | Maven (`mvnw` / `mvnw.cmd` wrapper present) |
| Database | PostgreSQL (dev: `localhost:5432/performance_tracker`) |
| Identity provider | Keycloak **26.x**, realm `rkt` (external service) |
| API style | REST, JSON, stateless JWT resource server |
| Port | `8080` |
| OpenAPI / Swagger UI | `/v3/api-docs`, `/swagger-ui.html` |
| Actuator | `/actuator/**` (all endpoints exposed) |
| Entry point | `com.pt.PerformanceTracker.PerformanceTrackerApplication` |

## 2. System in one paragraph

Performance Tracker models a company performance cycle. An **organisation** layer defines the
company structure (departments, designations, their mapping, employees, and app users linked to
Keycloak identities). On top of that, a **performance** layer builds the review chain
`Project → Project-Department → KRA → KPA → KPA-Employee assignment → Task`. Employees are assigned
KPAs, break them into tasks, and the system derives a `completionPercentage` per assignment from the
ratio of completed tasks. Every read and write is scoped by the caller's role: admins and HR see
everything, managers see their own departments, employees see only what they are assigned to.

## 3. Module map

```
com.pt.PerformanceTracker
├── PerformanceTrackerApplication      @SpringBootApplication @EnableJpaAuditing
├── common                             cross-cutting: response envelope, errors, auditing, enums, sequences
├── organization
│   ├── department                     departments
│   ├── designation                    designations (job titles)
│   ├── departmentDesignation          department ⇄ designation mapping
│   ├── employee                       employees (many-to-many to department-designation mappings)
│   ├── user                           app users ⇄ employees, Keycloak identity, roles
│   └── projects                       projects + project⇄department
├── performance
│   ├── kra                            KRAs (per project-department)
│   ├── kpa                            KPAs (per KRA)
│   ├── kpaEmployee                    KPA ⇄ employee assignments (+ completionPercentage)
│   └── kpaTask                        tasks per assignment (+ TaskStatus)
└── security
    ├── config                         Spring Security filter chain
    ├── converter                      JWT → authorities
    ├── handler                        401 / 403 JSON handlers
    ├── access                         AccessScopeService (role + department scope)
    ├── service                        CustomUserService (current-user helper)
    ├── role                           RoleEntity, repository, RoleDataInitializer
    └── keycloak                       Keycloak admin client, user import/sync
```

Each feature module follows the same 6-package shape:
`controller` → `service` (interface) → `serviceImpl` → `mapper` (MapStruct) → `repository` →
`entity`, plus `dto` (request/response records or classes) and `specification` (dynamic filters).

## 4. Reader shortcuts

- **Setting up for the first time?** → [02 Setup & Configuration](./02-setup-and-configuration.md)
- **Calling an endpoint?** → [05 API Reference](./05-api-reference.md)
- **"Why can't this user see that row?"** → [04 Security & Authorization](./04-security-and-authorization.md)
- **"Why is this a 500?"** → [06 Error Handling](./06-error-handling.md) + [10 Known Issues](./10-known-issues.md)
- **"How is completion % computed?"** → [07 Business Rules](./07-business-rules.md)