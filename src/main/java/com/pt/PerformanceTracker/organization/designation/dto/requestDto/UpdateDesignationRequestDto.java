package com.pt.PerformanceTracker.organization.designation.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Generated;

public class UpdateDesignationRequestDto {
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

   @Generated
   public String getDesignationName() {
      return this.designationName;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public void setDesignationName(final String designationName) {
      this.designationName = designationName;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }
}
