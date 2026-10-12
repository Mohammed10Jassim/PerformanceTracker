package com.pt.PerformanceTracker.organization.employee.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import lombok.Generated;

public class CreateEmployeeRequestDto {
   @NotNull(
      message = "Employee code is required."
   )
   private Long employeeCode;
   @NotBlank(
      message = "First name is required."
   )
   @Size(
      max = 100,
      message = "First name cannot exceed 100 characters."
   )
   private String firstName;
   @Size(
      max = 100,
      message = "Last name cannot exceed 100 characters."
   )
   private String lastName;
   @NotBlank(
      message = "Email is required."
   )
   @Email(
      message = "Please provide a valid email address."
   )
   @Size(
      max = 150,
      message = "Email cannot exceed 150 characters."
   )
   private String email;
   private LocalDate joiningDate;
   @NotEmpty(
      message = "At least one designation is required."
   )
   private Set<Long> departmentDesignationIds;

   @Generated
   public Long getEmployeeCode() {
      return this.employeeCode;
   }

   @Generated
   public String getFirstName() {
      return this.firstName;
   }

   @Generated
   public String getLastName() {
      return this.lastName;
   }

   @Generated
   public String getEmail() {
      return this.email;
   }

   @Generated
   public LocalDate getJoiningDate() {
      return this.joiningDate;
   }

   @Generated
   public Set<Long> getDepartmentDesignationIds() {
      return this.departmentDesignationIds;
   }

   @Generated
   public void setEmployeeCode(final Long employeeCode) {
      this.employeeCode = employeeCode;
   }

   @Generated
   public void setFirstName(final String firstName) {
      this.firstName = firstName;
   }

   @Generated
   public void setLastName(final String lastName) {
      this.lastName = lastName;
   }

   @Generated
   public void setEmail(final String email) {
      this.email = email;
   }

   @Generated
   public void setJoiningDate(final LocalDate joiningDate) {
      this.joiningDate = joiningDate;
   }

   @Generated
   public void setDepartmentDesignationIds(final Set<Long> departmentDesignationIds) {
      this.departmentDesignationIds = departmentDesignationIds;
   }
}
