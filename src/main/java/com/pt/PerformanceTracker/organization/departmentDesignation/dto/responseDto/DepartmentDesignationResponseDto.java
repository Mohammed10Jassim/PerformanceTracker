package com.pt.PerformanceTracker.organization.departmentDesignation.dto.responseDto;

import lombok.Generated;

public class DepartmentDesignationResponseDto {
   private Long id;
   private Long departmentId;
   private String departmentName;
   private Long designationId;
   private String designationName;
   private Boolean active;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getDepartmentId() {
      return this.departmentId;
   }

   @Generated
   public String getDepartmentName() {
      return this.departmentName;
   }

   @Generated
   public Long getDesignationId() {
      return this.designationId;
   }

   @Generated
   public String getDesignationName() {
      return this.designationName;
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
   public void setDepartmentId(final Long departmentId) {
      this.departmentId = departmentId;
   }

   @Generated
   public void setDepartmentName(final String departmentName) {
      this.departmentName = departmentName;
   }

   @Generated
   public void setDesignationId(final Long designationId) {
      this.designationId = designationId;
   }

   @Generated
   public void setDesignationName(final String designationName) {
      this.designationName = designationName;
   }

   @Generated
   public void setActive(final Boolean active) {
      this.active = active;
   }
}
