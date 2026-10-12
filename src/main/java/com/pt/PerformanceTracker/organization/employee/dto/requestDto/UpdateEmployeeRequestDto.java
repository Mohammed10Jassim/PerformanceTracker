package com.pt.PerformanceTracker.organization.employee.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import lombok.Generated;

public class UpdateEmployeeRequestDto {
   @NotBlank(
      message = "First name is required."
   )
   @Size(
      max = 100,
      message = "First name cannot exceed 100 characters."
   )
   private String firstName;
   @NotBlank(
      message = "Last name is required."
   )
   @Size(
      max = 100,
      message = "Last name cannot exceed 100 characters."
   )
   private String lastName;
   private LocalDate joiningDate;
   @NotEmpty(
      message = "At least one designation is required."
   )
   private Set<Long> departmentDesignationIds;

   @Generated
   public String getFirstName() {
      return this.firstName;
   }

   @Generated
   public String getLastName() {
      return this.lastName;
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
   public void setFirstName(final String firstName) {
      this.firstName = firstName;
   }

   @Generated
   public void setLastName(final String lastName) {
      this.lastName = lastName;
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
