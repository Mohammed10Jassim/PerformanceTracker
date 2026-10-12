# PerformanceTracker – Performance Management System

A configurable Performance Management System built using Java 21, Spring Boot, PostgreSQL, Spring Security, and Keycloak.

## Table of Contents

1. [Overview](#overview)
2. [Objectives](#objectives)
3. [Technology Stack](#technology-stack)
4. [System Architecture](#system-architecture)
5. [Functional Modules](#functional-modules)
6. [Performance Management Workflow](#performance-management-workflow)
7. [User Roles and Permissions](#user-roles-and-permissions)
8. [Authentication and Security](#authentication-and-security)
9. [Database Design](#database-design)
10. [API Response Structure](#api-response-structure)
11. [Project Structure](#project-structure)
12. [Prerequisites](#prerequisites)
13. [Configuration](#configuration)
14. [Build and Run](#build-and-run)
15. [API Documentation and Monitoring](#api-documentation-and-monitoring)
16. [Deployment](#deployment)
17. [Security Guidelines](#security-guidelines)
18. [Future Enhancements](#future-enhancements)

---

## 1. Overview

PerformanceTracker is a web-based Performance Management System (PMS) designed to organize employee performance activities around projects, Key Result Areas (KRAs), Key Performance Areas (KPAs), employee assignments, and tasks.

The application provides centralized authentication through Keycloak and uses role-based authorization to control access to protected operations. PostgreSQL stores organizational information, employee records, user mappings, and performance-related data.

The system follows a **modular monolithic architecture**, keeping related functionality within a single Spring Boot application while maintaining separation between business modules.

### Key Capabilities

- Organizational master data management
- Employee management
- Project management
- KRA and KPA management
- Employee KPA assignments
- Task management and status tracking
- Keycloak-based authentication
- Role-based authorization
- Department-level access control
- PostgreSQL data persistence

## 2. Objectives

- Maintain consistent organizational master data.
- Associate projects with defined KRAs and KPAs.
- Assign performance areas to employees.
- Track tasks associated with employee assignments.
- Centralize user authentication and application roles.
- Restrict access according to roles and organizational scope.
- Maintain data integrity between employee records and user identities.
- Provide a foundation for future performance reporting and calculations.

## 3. Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Backend programming language |
| Spring Boot 4.1.0 | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access |
| Hibernate | Object-relational mapping |
| PostgreSQL | Relational database |
| Spring Security | Application security |
| OAuth2 Resource Server | JWT validation |
| Keycloak | Identity and access management |
| Maven | Build and dependency management |
| MapStruct | Entity and DTO mapping |
| Lombok | Boilerplate code reduction |
| Spring Boot Actuator | Health and application information |
| Springdoc OpenAPI | API documentation |
| JUnit / Spring Boot Test | Automated testing |

## 4. System Architecture

PerformanceTracker uses a modular monolithic architecture. The backend is deployed as one Spring Boot application, with business responsibilities separated into modules.

### Architecture Diagram

```text
                  Web Client / Postman
                           |
                           v
                  Spring Boot REST APIs
                           |
                           v
                    Spring Security
                           |
                    JWT Validation
                           |
                           v
                        Keycloak
                           |
                           v
                  Application Modules
                           |
          +----------------+----------------+
          |                |                |
          v                v                v
    Organization       Performance        Common
       Module             Module        Components
          |                |                |
          +----------------+----------------+
                           |
                           v
                       PostgreSQL
```

### Architectural Principles

- Controllers handle HTTP requests and responses.
- Services contain business logic.
- Repositories handle database operations.
- DTOs define API request and response structures.
- MapStruct is used where configured for object mapping.
- Spring Security protects application endpoints.
- Keycloak provides identity and application role information.
- PostgreSQL stores business data and organizational relationships.
- Configuration is maintained separately for different environments.

## 5. Functional Modules

### 5.1 Organizational Master Data

Maintains the organizational structure required by the PMS.

Includes:

- Departments
- Designations
- Department-designation mappings
- Employee records

Department and designation information provides organizational context for employee assignments and access restrictions.

### 5.2 Employee Management

Maintains employee information and organizational associations.

Responsibilities include:

- Employee codes
- Employee names and email addresses
- Department and designation associations
- Employee lookup and filtering
- Active/inactive state management where supported

Employee records are stored in PostgreSQL and linked to PMS user records where applicable.

### 5.3 Project Management

Projects provide the context for managing KRAs, KPAs, employee assignments, and tasks.

### 5.4 Key Result Areas (KRA)

KRAs represent result areas associated with projects. They provide a higher-level structure for organizing KPAs.

### 5.5 Key Performance Areas (KPA)

KPAs represent performance areas associated with KRAs. They can be assigned to employees within projects to organize performance-related work.

### 5.6 Employee KPA Assignment

Connects employees to KPAs within a project. Assignments provide the relationship between a project's performance areas and the employees responsible for the associated work.

### 5.7 Task Management

Supports task-related operations associated with employee KPA assignments.

Responsibilities include:

- Task creation
- Task updates
- Task status management
- Task activation and deactivation
- Task retrieval

Task progress calculations and final performance scoring should be considered available only when their corresponding business logic has been implemented and verified.

### 5.8 User and Access Management

Integrates PMS user records with Keycloak identities.

Responsibilities include:

- Keycloak user lookup
- PMS-to-Keycloak identity mapping
- Synchronization of eligible Keycloak users
- Verification of user mapping consistency
- Role-based authorization

Not every Keycloak account needs to have a corresponding PMS employee record.

---

## 6. Performance Management Workflow

The current functional workflow is:

```text
Organizational Master Data
           |
           v
         Project
           |
           v
           KRA
           |
           v
           KPA
           |
           v
  Employee KPA Assignment
           |
           v
          Tasks
           |
           v
   Task Status Tracking
```

### Workflow Description

1. Maintain departments, designations, and employee records.
2. Create and manage projects.
3. Associate KRAs with the appropriate projects.
4. Define KPAs under the applicable KRAs.
5. Assign KPAs to employees within a project.
6. Create and manage tasks under employee assignments.
7. Track task statuses for performance monitoring and future reporting.

The current implementation focuses on the project-to-task workflow. Advanced KPI/KRI calculations and final performance scoring are outside the current scope unless implemented separately.

## 7. User Roles and Permissions

The agreed application roles are:

| Role | Responsibility |
|---|---|
| `ADMIN` | Administrative access to authorized system functions |
| `HR_ADMIN` | HR and organizational administration within permitted operations |
| `MANAGER` | Management operations and data within the permitted scope |
| `EMPLOYEE` | Employee-level operations and authorized self-access |

Actual permissions are determined by the endpoint authorization rules and the associated business-scope checks.

### Authorization Principles

- Keycloak is the intended source of truth for application roles.
- JWT role claims are converted into Spring Security authorities.
- PostgreSQL stores employee data and organizational relationships.
- Department-level restrictions must be enforced for applicable operations.
- Authentication does not automatically grant permission to perform every operation.
- Role names must be consistent across Keycloak, JWT conversion, controller annotations, and security tests.

`HR_MANAGER` is not an agreed application role. Use `HR_ADMIN` where HR administration permissions are intended.

## 8. Authentication and Security

### 8.1 Keycloak Integration

Keycloak provides centralized authentication and issues JWT access tokens.

The Spring Boot application acts as an OAuth2 Resource Server and validates tokens before allowing access to protected endpoints.

### 8.2 Authentication Flow

1. The user authenticates through the configured Keycloak client.
2. Keycloak issues an access token.
3. The client sends the token with API requests.
4. Spring Security validates the token.
5. The application extracts the configured role claims.
6. Spring Security authorities and business-scope rules determine whether the request is permitted.

Example request header:

```http
Authorization: Bearer <access-token>
```

The application client is named `performance-tracker`. Client roles are expected under:

```text
resource_access.performance-tracker.roles
```

The actual token structure must match the Keycloak configuration and the application's JWT role converter.

### 8.3 Keycloak Administration

The application uses the Keycloak Admin Client for supported administrative operations, including user lookup, synchronization, and verification.

Administrative operations require a service account with the appropriate Keycloak permissions.

Client secrets must be provided through secure configuration and must never be committed to Git.

### 8.4 Former Employee Accounts

Former employee accounts may be retained in Keycloak for record retention. They must not be automatically re-enabled by bulk operations.

Keycloak accounts without corresponding PMS employee records must not automatically generate new employee records.

User verification should identify mapping inconsistencies without deleting accounts or changing their enabled status.

## 9. Database Design

PostgreSQL is the primary database for the application.

### Main Business Entities

- Departments
- Designations
- Department-designation mappings
- Employees
- PMS users and roles
- Projects
- KRAs
- KPAs
- Employee KPA assignments
- Tasks

### Data Integrity

The application should maintain:

- Unique employee codes and applicable email constraints
- Valid employee-to-organization relationships
- Valid project and performance entity relationships
- Consistent PMS user-to-Keycloak identity mappings
- Appropriate active/inactive state handling

Indexes, database constraints, and repository queries should support data integrity and efficient data access.

**Important:** Back up PostgreSQL before deployment or schema changes. Do not drop or recreate the existing database when deploying an application update.

## 10. API Response Structure

The application exposes REST endpoints and includes shared API response and exception-handling components where implemented.

The common response model contains these fields:

| Field | Description |
|---|---|
| `success` | Indicates whether the operation succeeded |
| `status` | HTTP status code |
| `message` | Response message |
| `data` | Response payload, when applicable |
| `timestamp` | Response creation timestamp |

Example response:

```json
{
  "success": true,
  "status": 200,
  "message": "Request completed successfully",
  "data": {},
  "timestamp": "2026-10-12T10:30:00"
}
```

This is an illustrative response. Actual values depend on the endpoint and returned data.

### Common HTTP Status Codes

| Status | Meaning |
|---|---|
| `200 OK` | Request completed successfully |
| `201 Created` | Resource created successfully |
| `400 Bad Request` | Invalid request or validation failure |
| `401 Unauthorized` | Authentication is missing or invalid |
| `403 Forbidden` | Authenticated caller is not authorized |
| `404 Not Found` | Requested resource does not exist |
| `409 Conflict` | Request conflicts with existing data |
| `500 Internal Server Error` | Unexpected server-side failure |

Actual status codes depend on endpoint implementation.

## 11. Project Structure

The main application code is organized under:

```text
src/
├── main/
│   ├── java/
│   │   └── com/pt/PerformanceTracker/
│   │       ├── common/
│   │       │   ├── config/
│   │       │   ├── constants/
│   │       │   ├── entity/
│   │       │   ├── enums/
│   │       │   ├── exception/
│   │       │   ├── response/
│   │       │   └── sequence/
│   │       ├── organization/
│   │       ├── performance/
│   │       ├── security/
│   │       └── PerformanceTrackerApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test/
```

Other repository resources may include:

| Path | Purpose |
|---|---|
| `pom.xml` | Maven dependencies and build configuration |
| `docker/` | Docker-related resources |
| `docs/` | Technical and security documentation |
| `postman/` or `.postman/` | API testing resources |

The exact package and folder contents may change as the project evolves.

## 12. Prerequisites

Install or configure:

- Java Development Kit 21
- Apache Maven 3.9 or a compatible version
- PostgreSQL
- A reachable Keycloak instance
- The required Keycloak realm, clients, and roles
- Environment-specific application configuration

Verify the installation:

```bash
java -version
mvn -version
```

## 13. Configuration

### 13.1 Application Profiles

The project contains:

```text
src/main/resources/
├── application.properties
├── application-dev.properties
└── application-prod.properties
```

The base configuration currently activates the development profile by default. Select the appropriate profile explicitly when running in another environment.

### 13.2 Database Configuration

The development database configuration expects:

```text
Database: performance_tracker
Host: localhost
Port: 5432
```

The JDBC URL format is:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/performance_tracker
```

Database credentials must be configured locally or supplied through environment variables.

### 13.3 Keycloak Configuration

Configure the correct values for:

- Keycloak server URL
- Realm
- JWT issuer URI
- JWK set URI
- Administrative client ID
- Administrative client secret
- Target realm for user administration

The issuer and JWK set URLs must match the actual Keycloak environment. Do not assume that a development IP address is accessible from the deployment server.

### 13.4 Environment Variables

The production configuration uses the following environment-variable names:

| Variable | Purpose |
|---|---|
| `DB_URL` | PostgreSQL JDBC URL |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `KEYCLOAK_ISSUER_URI` | JWT issuer URL |
| `KEYCLOAK_JWK_SET_URI` | JWT signing-key endpoint |
| `KEYCLOAK_SERVER_URL` | Keycloak administrative API URL |
| `KEYCLOAK_ADMIN_REALM` | Administrative authentication realm |
| `KEYCLOAK_TARGET_REALM` | Realm containing target users and client |
| `KEYCLOAK_ADMIN_CLIENT_ID` | Administrative client ID |
| `KEYCLOAK_ADMIN_CLIENT_SECRET` | Administrative client secret |
| `CORS_ALLOWED_ORIGINS` | Trusted frontend origins |

Confirm that these names match the actual properties in the selected profile. Never commit passwords, client secrets, tokens, or sensitive employee information.

## 14. Build and Run

Run Maven commands from the directory containing `pom.xml`.

### Compile the Application

```bash
mvn clean compile
```

### Run Tests

```bash
mvn test
```

### Package the Application

```bash
mvn clean package
```

The executable JAR is normally generated under the `target/` directory. Its exact filename depends on the Maven project version.

### Run Locally

```bash
mvn spring-boot:run
```

### Run the Packaged JAR

Replace the filename below with the JAR generated by Maven:

```bash
java -jar target/PerformanceTracker-0.0.1-SNAPSHOT.jar
```

### Run with the Production Profile

Provide the required environment variables and run:

```bash
java -jar target/PerformanceTracker-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

The default application port is `8080`, unless overridden by configuration.

A successful build does not prove that all authorization rules, external integrations, or deployment settings work correctly. Runtime testing is also required.

## 15. API Documentation and Monitoring

### Actuator

The configured Actuator paths include:

- `/actuator/health`
- `/actuator/info`

Availability depends on the deployed configuration and security rules.

### OpenAPI / Swagger

The configured paths include:

- `/v3/api-docs`
- `/swagger-ui.html`

API documentation is intended to be restricted to `ADMIN` and `HR_ADMIN` in the current security-audit iteration. Verify actual endpoint behavior after deployment.

## 16. Deployment

The application can be deployed as an executable JAR to an environment with a compatible Java runtime and network connectivity to PostgreSQL and Keycloak.

### Deployment Checklist

1. Back up the PostgreSQL database.
2. Build and test the application.
3. Transfer the JAR to the deployment server.
4. Configure environment variables and the production profile.
5. Confirm database and Keycloak connectivity.
6. Start the application using the selected process manager.
7. Inspect application startup logs.
8. Verify application health and login.
9. Test role-based authorization.
10. Configure restart behavior, monitoring, and rollback procedures.

For Ubuntu deployment, use the actual server addresses and environment settings. Windows `localhost` does not automatically refer to the Windows machine when used from Ubuntu.

The deployment topology, frontend origin, database host, and Keycloak host must be confirmed for the target environment.

## 17. Security Guidelines

- Never commit passwords, client secrets, access tokens, or other credentials.
- Use separate configuration for development and production.
- Use HTTPS across untrusted networks.
- Restrict CORS to trusted frontend origins.
- Limit Keycloak service-account permissions.
- Do not automatically re-enable former employee accounts.
- Enforce authorization in addition to authentication.
- Enforce department-level scope for applicable operations.
- Protect administrative and data-modifying endpoints.
- Avoid verbose SQL parameter and security trace logging in production.
- Back up PostgreSQL before deployment or schema changes.
- Test `401 Unauthorized` and `403 Forbidden` behavior.
- Verify authorization at both controller and service boundaries.

The security audit remains subject to further verification. A successful build alone is not a complete security audit. Test the relevant controller and service access paths before exposing the application publicly.

## 18. Future Enhancements

Potential future improvements include:

- Automated role-based authorization tests
- Department-level data-isolation tests
- Further service-level authorization review
- More complete performance progress calculations
- KPI/KRI management and performance scoring
- Performance reports and dashboards
- Deployment automation and operational monitoring
- Database and query performance optimization

These are potential future improvements, not claims that every feature is already implemented.

---

## Project Information

| Property | Value |
|---|---|
| Project Name | PerformanceTracker |
| Description | Performance Management System |
| Architecture | Modular monolith |
| Backend | Java 21, Spring Boot |
| Database | PostgreSQL |
| Authentication | Keycloak |
| Authorization | Spring Security and Keycloak roles |
| Build Tool | Maven |
