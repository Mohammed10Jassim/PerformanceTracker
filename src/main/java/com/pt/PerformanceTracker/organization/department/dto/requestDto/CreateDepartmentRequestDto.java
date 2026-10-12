package com.pt.PerformanceTracker.organization.department.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Generated;

public class CreateDepartmentRequestDto {
   @NotBlank(
      message = "Department name is required."
   )
   @Size(
      max = 150,
      message = "Department name cannot exceed 150 characters."
   )
   private String departmentName;
   @Size(
      max = 500,
      message = "Description cannot exceed 500 characters."
   )
   private String description;

   @Generated
   public static CreateDepartmentRequestDto.CreateDepartmentRequestDtoBuilder builder() {
      return new CreateDepartmentRequestDto.CreateDepartmentRequestDtoBuilder();
   }

   @Generated
   public String getDepartmentName() {
      return this.departmentName;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public void setDepartmentName(final String departmentName) {
      this.departmentName = departmentName;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public CreateDepartmentRequestDto() {
   }

   @Generated
   public CreateDepartmentRequestDto(final String departmentName, final String description) {
      this.departmentName = departmentName;
      this.description = description;
   }

   @Generated
   public static class CreateDepartmentRequestDtoBuilder {
      @Generated
      private String departmentName;
      @Generated
      private String description;

      @Generated
      CreateDepartmentRequestDtoBuilder() {
      }

      @Generated
      public CreateDepartmentRequestDto.CreateDepartmentRequestDtoBuilder departmentName(final String departmentName) {
         this.departmentName = departmentName;
         return this;
      }

      @Generated
      public CreateDepartmentRequestDto.CreateDepartmentRequestDtoBuilder description(final String description) {
         this.description = description;
         return this;
      }

      @Generated
      public CreateDepartmentRequestDto build() {
         return new CreateDepartmentRequestDto(this.departmentName, this.description);
      }

      @Generated
      @Override
      public String toString() {
         return "CreateDepartmentRequestDto.CreateDepartmentRequestDtoBuilder(departmentName="
            + this.departmentName
            + ", description="
            + this.description
            + ")";
      }
   }
}
