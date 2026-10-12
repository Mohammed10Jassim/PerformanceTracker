package com.pt.PerformanceTracker.organization.employee.dto.responseDto;

import java.time.LocalDate;
import java.util.Set;
import lombok.Generated;

public class EmployeeResponseDto {
   private Long id;
   private Long employeeCode;
   private String firstName;
   private String lastName;
   private String email;
   private LocalDate joiningDate;
   private EmployeeDepartmentResponseDto department;
   private Set<EmployeeDesignationResponseDto> designations;
   private Boolean active;

   @Generated
   EmployeeResponseDto(
      final Long id,
      final Long employeeCode,
      final String firstName,
      final String lastName,
      final String email,
      final LocalDate joiningDate,
      final EmployeeDepartmentResponseDto department,
      final Set<EmployeeDesignationResponseDto> designations,
      final Boolean active
   ) {
      this.id = id;
      this.employeeCode = employeeCode;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.joiningDate = joiningDate;
      this.department = department;
      this.designations = designations;
      this.active = active;
   }

   @Generated
   public static EmployeeResponseDto.EmployeeResponseDtoBuilder builder() {
      return new EmployeeResponseDto.EmployeeResponseDtoBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

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
   public EmployeeDepartmentResponseDto getDepartment() {
      return this.department;
   }

   @Generated
   public Set<EmployeeDesignationResponseDto> getDesignations() {
      return this.designations;
   }

   @Generated
   public Boolean getActive() {
      return this.active;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
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
   public void setDepartment(final EmployeeDepartmentResponseDto department) {
      this.department = department;
   }

   @Generated
   public void setDesignations(final Set<EmployeeDesignationResponseDto> designations) {
      this.designations = designations;
   }

   @Generated
   public void setActive(final Boolean active) {
      this.active = active;
   }

   @Generated
   public static class EmployeeResponseDtoBuilder {
      @Generated
      private Long id;
      @Generated
      private Long employeeCode;
      @Generated
      private String firstName;
      @Generated
      private String lastName;
      @Generated
      private String email;
      @Generated
      private LocalDate joiningDate;
      @Generated
      private EmployeeDepartmentResponseDto department;
      @Generated
      private Set<EmployeeDesignationResponseDto> designations;
      @Generated
      private Boolean active;

      @Generated
      EmployeeResponseDtoBuilder() {
      }

      @Generated
      public EmployeeResponseDto.EmployeeResponseDtoBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public EmployeeResponseDto.EmployeeResponseDtoBuilder employeeCode(final Long employeeCode) {
         this.employeeCode = employeeCode;
         return this;
      }

      @Generated
      public EmployeeResponseDto.EmployeeResponseDtoBuilder firstName(final String firstName) {
         this.firstName = firstName;
         return this;
      }

      @Generated
      public EmployeeResponseDto.EmployeeResponseDtoBuilder lastName(final String lastName) {
         this.lastName = lastName;
         return this;
      }

      @Generated
      public EmployeeResponseDto.EmployeeResponseDtoBuilder email(final String email) {
         this.email = email;
         return this;
      }

      @Generated
      public EmployeeResponseDto.EmployeeResponseDtoBuilder joiningDate(final LocalDate joiningDate) {
         this.joiningDate = joiningDate;
         return this;
      }

      @Generated
      public EmployeeResponseDto.EmployeeResponseDtoBuilder department(final EmployeeDepartmentResponseDto department) {
         this.department = department;
         return this;
      }

      @Generated
      public EmployeeResponseDto.EmployeeResponseDtoBuilder designations(final Set<EmployeeDesignationResponseDto> designations) {
         this.designations = designations;
         return this;
      }

      @Generated
      public EmployeeResponseDto.EmployeeResponseDtoBuilder active(final Boolean active) {
         this.active = active;
         return this;
      }

      @Generated
      public EmployeeResponseDto build() {
         return new EmployeeResponseDto(
            this.id, this.employeeCode, this.firstName, this.lastName, this.email, this.joiningDate, this.department, this.designations, this.active
         );
      }

      @Generated
      @Override
      public String toString() {
         return "EmployeeResponseDto.EmployeeResponseDtoBuilder(id="
            + this.id
            + ", employeeCode="
            + this.employeeCode
            + ", firstName="
            + this.firstName
            + ", lastName="
            + this.lastName
            + ", email="
            + this.email
            + ", joiningDate="
            + this.joiningDate
            + ", department="
            + this.department
            + ", designations="
            + this.designations
            + ", active="
            + this.active
            + ")";
      }
   }
}
