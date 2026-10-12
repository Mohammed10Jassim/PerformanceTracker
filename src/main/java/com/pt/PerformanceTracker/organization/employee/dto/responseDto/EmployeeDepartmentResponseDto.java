package com.pt.PerformanceTracker.organization.employee.dto.responseDto;

import lombok.Generated;

public class EmployeeDepartmentResponseDto {
   private Long id;
   private Long departmentCode;
   private String departmentName;

   @Generated
   EmployeeDepartmentResponseDto(final Long id, final Long departmentCode, final String departmentName) {
      this.id = id;
      this.departmentCode = departmentCode;
      this.departmentName = departmentName;
   }

   @Generated
   public static EmployeeDepartmentResponseDto.EmployeeDepartmentResponseDtoBuilder builder() {
      return new EmployeeDepartmentResponseDto.EmployeeDepartmentResponseDtoBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getDepartmentCode() {
      return this.departmentCode;
   }

   @Generated
   public String getDepartmentName() {
      return this.departmentName;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setDepartmentCode(final Long departmentCode) {
      this.departmentCode = departmentCode;
   }

   @Generated
   public void setDepartmentName(final String departmentName) {
      this.departmentName = departmentName;
   }

   @Generated
   public static class EmployeeDepartmentResponseDtoBuilder {
      @Generated
      private Long id;
      @Generated
      private Long departmentCode;
      @Generated
      private String departmentName;

      @Generated
      EmployeeDepartmentResponseDtoBuilder() {
      }

      @Generated
      public EmployeeDepartmentResponseDto.EmployeeDepartmentResponseDtoBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public EmployeeDepartmentResponseDto.EmployeeDepartmentResponseDtoBuilder departmentCode(final Long departmentCode) {
         this.departmentCode = departmentCode;
         return this;
      }

      @Generated
      public EmployeeDepartmentResponseDto.EmployeeDepartmentResponseDtoBuilder departmentName(final String departmentName) {
         this.departmentName = departmentName;
         return this;
      }

      @Generated
      public EmployeeDepartmentResponseDto build() {
         return new EmployeeDepartmentResponseDto(this.id, this.departmentCode, this.departmentName);
      }

      @Generated
      @Override
      public String toString() {
         return "EmployeeDepartmentResponseDto.EmployeeDepartmentResponseDtoBuilder(id="
            + this.id
            + ", departmentCode="
            + this.departmentCode
            + ", departmentName="
            + this.departmentName
            + ")";
      }
   }
}
