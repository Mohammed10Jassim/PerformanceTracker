# 04 — Security & Authorization

## 1. Model overview

Security is split into two layers:
1. **Keycloak** — authenticates users, issues JWTs, and holds *client roles* on the client
   `performance-tracker`.
2. **Spring Security** — validates JWTs (resource server), converts Keycloak client roles to Spring
   `GrantedAuthority` with `ROLE_` prefix, and enforces access via method security and
   service-level data scoping.

The application is **stateless** (`SessionCreationPolicy.STATELESS`).

## 2. JWT structure (what matters)

The resource server expects a JWT with the issuer:

```text
http://192.168.0.120:8081/realms/rkt  (dev)
```

The role converter reads **only**:

```json
{
  "resource_access": {
    "performance-tracker": {
      "roles": ["ADMIN","EMPLOYEE","HR_ADMIN","MANAGER"]
    }
  }
}
```

It **does not** read:
- `realm_access.roles` (realm roles)
- `scope` / `scp`
- groups

Each client role is turned into `SimpleGrantedAuthority("ROLE_" + role)`. For example `ADMIN` →
`ROLE_ADMIN`. The prefix is required by `hasRole("ADMIN")` which checks for `ROLE_ADMIN`.

## 3. Role model

### 3.1 Roles seeded in the app

`RoleDataInitializer` runs at startup and idempotently creates these roles in the `roles` table:

| Role name | Seeded | Where used |
|-----------|--------|------------|
| `ADMIN` | yes | many controllers, access scope |
| `HR_ADMIN` | yes | many controllers (note spelling `HR_ADMIN`) |
| `MANAGER` | yes | many controllers + scope |
| `EMPLOYEE` | yes | seeded only; **not used in any `@PreAuthorize` expression** |

### 3.2 Roles referenced but not seeded

| Role name | Referenced in | Notes |
|-----------|---------------|-------|
| `PROJECT_MANAGER` | `AccessScopeServiceImpl.hasManagementRole` | never inserted by `RoleDataInitializer` |
| `PROJECT_LEAD` | `AccessScopeServiceImpl.hasManagementRole` | never inserted by `RoleDataInitializer` |
| `HR_MANAGER` | `ProjectKpaController` `@PreAuthorize` | **exact string** appears only here; not seeded and not in the role enum logic. Probably a typo for `HR_ADMIN`. |

The management scope is defined as: `MANAGER OR PROJECT_MANAGER OR PROJECT_LEAD`.

### 3.3 Token vs DB double-check

`AccessScopeService.hasVerifiedRole(String roleName)` requires **both** to be true:

1. The current user has that role in the local `users` ↔ `user_roles` ↔ `roles` table
   (`roleExistsInDb = user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase(roleName))`)
2. The JWT contains `ROLE_<roleName>` in authorities
   (`roleExistsInToken = customUserService.hasRole(roleName)`)

If either is missing, the check fails. This means a user synced from Keycloak but with **no roles
assigned in the PMS `user_roles` join table** will fail every `hasVerifiedRole` check. Newly created
users via `KeycloakUserSyncServiceImpl` receive **no roles** at creation time (see the code comment
in the sync implementation), so they effectively have no application-level roles until roles are
assigned in the database.

## 4. Method-level authorization

`@EnableMethodSecurity` is enabled. Controllers use `@PreAuthorize("hasAnyRole(...)")`.

| Controller | Public read endpoints | Write endpoints (require role) | Role list |
|------------|----------------------|-------------------------------|-----------|
| `DepartmentController` | `GET /departments/{id}`, `GET /departments` | `POST /departments/create`, `PUT /departments/{id}`, `PATCH /activate/{id}`, `PATCH /deactivate/{id}` | `ADMIN`, `HR_ADMIN` |
| `DesignationController` | `GET /designations/{id}`, `GET /designations` | `POST /designations`, `PUT /designations/{id}`, `PATCH /activate/{id}`, `PATCH /deactivate/{id}` | `ADMIN`, `HR_ADMIN` |
| `DepartmentDesignationController` | `GET /department-designations/{id}`, `GET /department-designations` | `POST /department-designations`, `PATCH /activate/{id}`, `PATCH /deactivate/{id}` | `ADMIN`, `HR_ADMIN` |
| `EmployeeController` | `GET /employees/{id}`, `GET /employees` | `POST /employees`, `PUT /employees/{id}`, `PATCH /activate/{id}`, `PATCH /deactivate/{id}` | `ADMIN`, `HR_ADMIN` |
| `ProjectController` | `GET /projects/{id}`, `GET /projects`, `GET /projects/{id}/available-statuses` | `POST /projects/create`, `PATCH /{id}`, `PATCH /activate/{id}`, `PATCH /{id}/status`, `PATCH /deactivate/{id}` | `ADMIN`, `HR_ADMIN` |
| `ProjectDepartmentController` | `GET /project-departments/{id}`, `GET /project-departments` | `POST /project-departments/assign`, `PATCH /activate/{id}`, `PATCH /deactivate/{id}` | `ADMIN`, `HR_ADMIN` |
| `ProjectKraController` | `GET /project-kras/{kraId}`, `GET /project-kras` | `POST /project-kras/create`, `PATCH /{kraId}`, `PATCH /activate/{kraId}`, `PATCH /deactivate/{kraId}` | `ADMIN`, `HR_ADMIN`, `MANAGER` |
| `ProjectKpaController` | `GET /project-kpas/{projectKpaId}`, `GET /project-kpas` | `POST /project-kpas/create`, `PATCH /{projectKpaId}`, `PATCH /activate/{projectKpaId}`, `PATCH /deactivate/{projectKpaId}` | `ADMIN`, **`HR_MANAGER`**, `MANAGER` |
| `ProjectKpaEmployeeController` | `GET /project-kpa-employees/{id}`, `GET /project-kpa-employees` | `POST /project-kpa-employees/assign`, `PATCH /activate/{id}`, `PATCH /deactivate/{id}` | `ADMIN`, `HR_ADMIN`, `MANAGER` |
| `ProjectKpaTaskController` | **all GET endpoints** (`/{taskId}`, list) | **all write endpoints** (`POST /create`, `PATCH /{taskId}`, `PATCH /activate/{taskId}`, `PATCH /deactivate/{taskId}`) | **NO `@PreAuthorize`** — any authenticated user may call them (scoped in service) |
| `UserController` | `GET /users/{id}`, `GET /users/employee/{id}`, `GET /users` | `POST /users`, `PUT /users/{id}`, `PATCH /activate/{id}`, `PATCH /deactivate/{id}` | `ADMIN`, `HR_ADMIN` |
| `KeycloakUserImportController` | `GET /keycloak/users` | `POST /keycloak/import-users`, `POST /keycloak/sync-users` | **none** — only `anyRequest().authenticated()` |

**Notes:**
- `ProjectKpaController` uses **`HR_MANAGER`** (not `HR_ADMIN`). This is the only occurrence. If your
  Keycloak roles are `HR_ADMIN`, users with that role cannot write KPAs via that controller.
- `ProjectKpaTaskController` has **zero method-security annotations**. Authorization is entirely
  service-scoped (see §5.4).
- `KeycloakUserImportController` exposes privileged admin operations but has no `@PreAuthorize` —
  anyone with a valid JWT can call them (they require the Keycloak service account to actually
  succeed, but the HTTP endpoint is not role-gated).

## 5. Data scoping (row-level access)

`@PreAuthorize` is not enough for multi-tenant/department-scoped data. Every read operation that
returns lists or can be filtered enforces scope in the service implementation using
`AccessScopeService` and `Specification`.

### 5.1 AccessScopeService methods

| Method | Behaviour |
|--------|-----------|
| `hasVerifiedRole(String roleName)` | `dbHasRole && tokenHasRole` (current user). **Ignores the `UserEntity` parameter passed to the other helpers.** |
| `isAdmin(UserEntity user)` | returns `hasVerifiedRole("ADMIN")` — always uses current security context |
| `isHrAdmin(UserEntity user)` | `hasVerifiedRole("HR_ADMIN")` |
| `hasManagementRole(UserEntity user)` | `hasVerifiedRole("MANAGER") OR PROJECT_MANAGER OR PROJECT_LEAD` |
| `canAccessDepartment(UserEntity user, Long departmentId)` | - true if `ADMIN` or `HR_ADMIN` - else if management role → check if `user.employee.departmentDesignations` contains any mapping whose `department.id == departmentId` - else false. May NPE if `user.employee` is null |
| `validateDepartmentAccess(Long departmentId)` | calls `canAccessDepartment` on current user; if false throws **`java.lang.SecurityException("You do not have permission to access this department")`**. This is **not** a `BaseException`, so it maps to **HTTP 500 / `GEN-002`**. |

### 5.2 Projects

**`ProjectServiceImpl.getProjects`**:
- `ADMIN` or `HR_ADMIN` → no department filter
- `MANAGER` (hasManagementRole) → restrict to projects that have any `projectDepartments.department.id`
  in the current user's authorised department set (derived from
  `currentUser.employee.departmentDesignations`). If the authorised set is empty → `Page.empty(pageable)`.
- `EMPLOYEE` → restrict via `ProjectSpecification.hasEmployeeAssignment(currentEmployeeId)`
- else → `Page.empty(pageable)`

**`ProjectServiceImpl.getProjectById`, `updateProject`, `updateProjectStatus`, activate/deactivate`**:
call `canViewProject(project)` first. `canViewProject` returns true if `ADMIN`/`HR_ADMIN`, or
`MANAGER` can access any department linked to the project, or `EMPLOYEE` has an active assignment to
that project; else false → throws `SecurityException` (becomes HTTP 500).

### 5.3 KRA / KPA

**`ProjectKraServiceImpl.getProjectKras`**:
- `ADMIN`/`HR_ADMIN` → unfiltered
- `MANAGER` → if `departmentId` provided → `validateDepartmentAccess(departmentId)`; else filter by
  authorised department IDs; empty set → `Page.empty(pageable)`
- `EMPLOYEE` → filter by `hasEmployeeAssignment(employeeId)`

**Read access (`validateReadAccess`)**:
- `ADMIN`/`HR_ADMIN` → allowed
- `MANAGER` → `validateDepartmentAccess` on the KRA's department
- `EMPLOYEE` → allowed only if there exists an **active** assignment to some KPA under that KRA for
  that employee
- else → `SecurityException`

**`ProjectKpaServiceImpl.getProjectKpas`** mirrors KRA. Employee scope uses `hasEmployeeAssignment`.
`validateReadAccess` checks for an **active** assignment
`findByProjectKpaIdAndEmployeeIdAndActiveTrue(kpaId, employeeId)`.

### 5.4 KPA-Employee assignments

**`ProjectKpaEmployeeServiceImpl.getAssignments`**:
- `ADMIN`/`HR_ADMIN` → unfiltered
- `MANAGER` → restrict to authorised departments (or validate supplied department)
- `EMPLOYEE` → **overrides** any client-supplied `employeeId` and forces `hasEmployeeId(currentEmployeeId)`

**`getById` / activate / deactivate**: allow if `ADMIN`/`HR_ADMIN`, or `MANAGER` can access
department, or assignment's `employee.id == current employee.id`.

**Creation**: enforces employee belongs to the **same department** as the KPA (mapping exists in
`employee.departmentDesignations`). `activate(id)` does **not** re-verify this membership after
reactivation.

### 5.5 Tasks

`ProjectKpaTaskController` has **no `@PreAuthorize`**. All access control in
`ProjectKpaTaskServiceImpl`.

**`validateAssignmentAccess(assignment, currentUser)`**:
- `ADMIN`/`HR_ADMIN` → allowed
- `MANAGER` → `validateDepartmentAccess` on that department
- `EMPLOYEE` → allowed **only if** `assignment.getEmployee().id == currentUser.getEmployee().id`
- else → `SecurityException`

**`canViewTask(task, currentUser)`**:
- `ADMIN`/`HR_ADMIN` → true
- `MANAGER` → `canAccessDepartment` (boolean)
- `EMPLOYEE` → assignment.employee.id == current employee.id

Employees may create, update (including setting `status` to `COMPLETED`), activate and deactivate
tasks belonging to their own assignments. Managers may manage tasks in their departments; admins/HR
may manage all.

## 6. Public endpoints

PermitAll in `SecurityConfig`:

| Path | Reason |
|------|--------|
| `/error` | Spring Boot error handling |
| `/actuator/**` | health/metrics/info (all exposed) |
| `/swagger-ui/**` | Swagger UI assets |
| `/v3/api-docs/**` | OpenAPI JSON |
| `/swagger-ui.html` | Swagger UI root |

`/enums/**`, `/security/**`, `/keycloak/**` require authentication.

## 7. CORS

```java
allowedOriginPatterns = ["*"]
allowedMethods = [GET, POST, PUT, PATCH, DELETE, OPTIONS]
allowedHeaders = [Authorization, Cache-Control, Content-Type, Accept]
exposedHeaders = [Authorization, Cache-Control, Content-Type]
allowCredentials = true
path = /**
```

## 8. Exception mapping for security

| Exception source | Thrown by | Becomes HTTP |
|------------------|-----------|--------------|
| Missing/invalid JWT | Spring Security filters | **401** via `CustomAuthenticationEntrypoint` |
| Insufficient authorities (`@PreAuthorize`) | Method security | **403** via `CustomAccessDeniedHandler` |
| `AccessScopeService.validateDepartmentAccess` throws `SecurityException` | Service | **500 / `GEN-002`** (not `BaseException`) |
| Other `SecurityException` | Service | **500 / `GEN-002`** |
| `UsernameNotFoundException` | `CustomUserServiceImpl` | **500** (not `BaseException`) |