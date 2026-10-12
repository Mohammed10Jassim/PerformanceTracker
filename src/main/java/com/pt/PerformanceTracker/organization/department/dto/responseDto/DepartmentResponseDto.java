package com.pt.PerformanceTracker.organization.department.dto.responseDto;

import lombok.Generated;

public class DepartmentResponseDto {
   private Long id;
   private Long departmentCode;
   private String departmentName;
   private String description;
   private Boolean active;

   @Generated
   public static DepartmentResponseDto.DepartmentResponseDtoBuilder builder() {
      return new DepartmentResponseDto.DepartmentResponseDtoBuilder();
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
   public String getDescription() {
      return this.description;
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
   public void setDepartmentCode(final Long departmentCode) {
      this.departmentCode = departmentCode;
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
   public void setActive(final Boolean active) {
      this.active = active;
   }

   @Generated
   public DepartmentResponseDto() {
   }

   @Generated
   public DepartmentResponseDto(final Long id, final Long departmentCode, final String departmentName, final String description, final Boolean active) {
      this.id = id;
      this.departmentCode = departmentCode;
      this.departmentName = departmentName;
      this.description = description;
      this.active = active;
   }

   @Generated
   public static class DepartmentResponseDtoBuilder {
      @Generated
      private Long id;
      @Generated
      private Long departmentCode;
      @Generated
      private String departmentName;
      @Generated
      private String description;
      @Generated
      private Boolean active;

      @Generated
      DepartmentResponseDtoBuilder() {
      }

      @Generated
      public DepartmentResponseDto.DepartmentResponseDtoBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public DepartmentResponseDto.DepartmentResponseDtoBuilder departmentCode(final Long departmentCode) {
         this.departmentCode = departmentCode;
         return this;
      }

      @Generated
      public DepartmentResponseDto.DepartmentResponseDtoBuilder departmentName(final String departmentName) {
         this.departmentName = departmentName;
         return this;
      }

      @Generated
      public DepartmentResponseDto.DepartmentResponseDtoBuilder description(final String description) {
         this.description = description;
         return this;
      }

      @Generated
      public DepartmentResponseDto.DepartmentResponseDtoBuilder active(final Boolean active) {
         this.active = active;
         return this;
      }

      @Generated
      public DepartmentResponseDto build() {
         return new DepartmentResponseDto(this.id, this.departmentCode, this.departmentName, this.description, this.active);
      }

      @Generated
      @Override
      public String toString() {
         return "DepartmentResponseDto.DepartmentResponseDtoBuilder(id="
            + this.id
            + ", departmentCode="
            + this.departmentCode
            + ", departmentName="
            + this.departmentName
            + ", description="
            + this.description
            + ", active="
            + this.active
            + ")";
      }
   }
}
