package com.pt.PerformanceTracker.organization.departmentDesignation.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Generated;

public class CreateDepartmentDesignationRequestDto {
   @NotNull(
      message = "Department Id is required."
   )
   private Long departmentId;
   @NotNull(
      message = "Designation Id is required."
   )
   private Long designationId;

   @Generated
   public Long getDepartmentId() {
      return this.departmentId;
   }

   @Generated
   public Long getDesignationId() {
      return this.designationId;
   }

   @Generated
   public void setDepartmentId(final Long departmentId) {
      this.departmentId = departmentId;
   }

   @Generated
   public void setDesignationId(final Long designationId) {
      this.designationId = designationId;
   }
}
