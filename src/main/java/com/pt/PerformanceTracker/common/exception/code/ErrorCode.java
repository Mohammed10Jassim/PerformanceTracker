package com.pt.PerformanceTracker.common.exception.code;

import lombok.Generated;

public enum ErrorCode {
   DEPARTMENT_NOT_FOUND("DEPT-001", "Department not found."),
   DEPARTMENT_ALREADY_EXISTS("DEPT-002", "Department already exists."),
   DEPARTMENT_ALREADY_ACTIVE("DEPT-003", "Department is already active."),
   DEPARTMENT_ALREADY_INACTIVE("DEPT-004", "Department is already inactive."),
   DESIGNATION_NOT_FOUND("DESG-001", "Designation not found."),
   DESIGNATION_ALREADY_EXISTS("DESG-002", "Designation already exists."),
   DESIGNATION_ALREADY_ACTIVE("DESG-003", "Designation is already active."),
   DESIGNATION_ALREADY_INACTIVE("DESG-004", "Designation is already inactive."),
   DEPARTMENT_DESIGNATION_ALREADY_ACTIVE("DEPT_DESG-001", "Department is already active."),
   DEPARTMENT_DESIGNATION_ALREADY_INACTIVE("DEPT_DESG-002", "Department is already inactive."),
   DEPARTMENT_DESIGNATION_ALREADY_EXISTS("DEPT_DESG-003", "Department already exists."),
   DEPARTMENT_DESIGNATION_NOT_FOUND("DESG-004", "Department not found."),
   EMPLOYEE_NOT_FOUND("EMP-001", "Employee not found."),
   EMPLOYEE_ALREADY_EXISTS("EMP-002", "Employee already exists."),
   EMPLOYEE_CODE_ALREADY_EXISTS("EMP-003", "Employee code already exists."),
   EMPLOYEE_EMAIL_ALREADY_EXISTS("EMP-004", "Employee email already exists."),
   EMPLOYEE_ALREADY_ACTIVE("EMP-005", "Employee is already active."),
   EMPLOYEE_ALREADY_INACTIVE("EMP-006", "Employee is inactive."),
   USER_NOT_FOUND("USER-001", "User not found."),
   USER_ALREADY_EXISTS("USER-002", "User already exists."),
   ROLE_NOT_FOUND("ROLE-001", "Role not found."),
   ROLE_ALREADY_EXISTS("ROLE-002", "Role already exists."),
   UNAUTHORIZED("AUTH-001", "Unauthorized."),
   FORBIDDEN("AUTH-002", "Access denied."),
   VALIDATION_FAILED("VAL-001", "Validation failed."),
   KEYCLOAK_USER_NOT_FOUND("KEYCLOAK-USER-001", "Keycloak User not found."),
   KRA_ALREADY_EXISTS("KRA-001", "Kra already exists."),
   KRA_NOT_FOUND("KRA-002", "Kra not found."),
   KPA_ALREADY_EXISTS("KPA-001", "KPA already exists."),
   KPA_NOT_FOUND("KPA-002", "KPA not found."),
   PERFORMANCE_TEMPLATE_ALREADY_EXISTS("PERFORMANCE-TEMP-001", "Performance template already exists."),
   PERFORMANCE_TEMPLATE_NOT_FOUND("PERFORMANCE-TEMP-002", "Performance template not found."),
   KPI_ALREADY_EXISTS("KPI-001", "KPI already exists."),
   KPI_NOT_FOUND("KPI-002", "KPI not found."),
   BAD_REQUEST("GEN-001", "Bad Request."),
   INTERNAL_SERVER_ERROR("GEN-002", "Internal Server Error.");

   private final String code;
   private final String defaultMessage;

   @Generated
   public String getCode() {
      return this.code;
   }

   @Generated
   public String getDefaultMessage() {
      return this.defaultMessage;
   }

   @Generated
   private ErrorCode(final String code, final String defaultMessage) {
      this.code = code;
      this.defaultMessage = defaultMessage;
   }
}
