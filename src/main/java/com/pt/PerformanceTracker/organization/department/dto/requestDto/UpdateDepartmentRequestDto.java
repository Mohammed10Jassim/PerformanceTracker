package com.pt.PerformanceTracker.organization.department.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Generated;

public class UpdateDepartmentRequestDto {
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
   public static UpdateDepartmentRequestDto.UpdateDepartmentRequestDtoBuilder builder() {
      return new UpdateDepartmentRequestDto.UpdateDepartmentRequestDtoBuilder();
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
   public UpdateDepartmentRequestDto() {
   }

   @Generated
   public UpdateDepartmentRequestDto(final String departmentName, final String description) {
      this.departmentName = departmentName;
      this.description = description;
   }

   @Generated
   public static class UpdateDepartmentRequestDtoBuilder {
      @Generated
      private String departmentName;
      @Generated
      private String description;

      @Generated
      UpdateDepartmentRequestDtoBuilder() {
      }

      @Generated
      public UpdateDepartmentRequestDto.UpdateDepartmentRequestDtoBuilder departmentName(final String departmentName) {
         this.departmentName = departmentName;
         return this;
      }

      @Generated
      public UpdateDepartmentRequestDto.UpdateDepartmentRequestDtoBuilder description(final String description) {
         this.description = description;
         return this;
      }

      @Generated
      public UpdateDepartmentRequestDto build() {
         return new UpdateDepartmentRequestDto(this.departmentName, this.description);
      }

      @Generated
      @Override
      public String toString() {
         return "UpdateDepartmentRequestDto.UpdateDepartmentRequestDtoBuilder(departmentName="
            + this.departmentName
            + ", description="
            + this.description
            + ")";
      }
   }
}
