package com.pt.PerformanceTracker.organization.designation.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.Generated;

public class CreateDesignationRequestDto {
   @NotBlank(
      message = "Designation name is required."
   )
   @Size(
      max = 150
   )
   private String designationName;
   @Size(
      max = 500
   )
   private String description;
   private Set<Long> departmentIds = new HashSet<>();

   @Generated
   public String getDesignationName() {
      return this.designationName;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public Set<Long> getDepartmentIds() {
      return this.departmentIds;
   }

   @Generated
   public void setDesignationName(final String designationName) {
      this.designationName = designationName;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public void setDepartmentIds(final Set<Long> departmentIds) {
      this.departmentIds = departmentIds;
   }
}
