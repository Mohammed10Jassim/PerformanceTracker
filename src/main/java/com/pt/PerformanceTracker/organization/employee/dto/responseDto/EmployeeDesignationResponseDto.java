package com.pt.PerformanceTracker.organization.employee.dto.responseDto;

import lombok.Generated;

public class EmployeeDesignationResponseDto {
   private Long id;
   private String designationName;

   @Generated
   EmployeeDesignationResponseDto(final Long id, final String designationName) {
      this.id = id;
      this.designationName = designationName;
   }

   @Generated
   public static EmployeeDesignationResponseDto.EmployeeDesignationResponseDtoBuilder builder() {
      return new EmployeeDesignationResponseDto.EmployeeDesignationResponseDtoBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getDesignationName() {
      return this.designationName;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setDesignationName(final String designationName) {
      this.designationName = designationName;
   }

   @Generated
   public static class EmployeeDesignationResponseDtoBuilder {
      @Generated
      private Long id;
      @Generated
      private String designationName;

      @Generated
      EmployeeDesignationResponseDtoBuilder() {
      }

      @Generated
      public EmployeeDesignationResponseDto.EmployeeDesignationResponseDtoBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public EmployeeDesignationResponseDto.EmployeeDesignationResponseDtoBuilder designationName(final String designationName) {
         this.designationName = designationName;
         return this;
      }

      @Generated
      public EmployeeDesignationResponseDto build() {
         return new EmployeeDesignationResponseDto(this.id, this.designationName);
      }

      @Generated
      @Override
      public String toString() {
         return "EmployeeDesignationResponseDto.EmployeeDesignationResponseDtoBuilder(id=" + this.id + ", designationName=" + this.designationName + ")";
      }
   }
}
