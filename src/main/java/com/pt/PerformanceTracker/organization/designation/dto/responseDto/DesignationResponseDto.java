package com.pt.PerformanceTracker.organization.designation.dto.responseDto;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class DesignationResponseDto {
   private Long id;
   private String designationName;
   private String description;
   private Boolean active;
   private List<Long> departmentIds = new ArrayList<>();
   private List<Long> departmentDesignationIds = new ArrayList<>();

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getDesignationName() {
      return this.designationName;
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
   public List<Long> getDepartmentIds() {
      return this.departmentIds;
   }

   @Generated
   public List<Long> getDepartmentDesignationIds() {
      return this.departmentDesignationIds;
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
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public void setActive(final Boolean active) {
      this.active = active;
   }

   @Generated
   public void setDepartmentIds(final List<Long> departmentIds) {
      this.departmentIds = departmentIds;
   }

   @Generated
   public void setDepartmentDesignationIds(final List<Long> departmentDesignationIds) {
      this.departmentDesignationIds = departmentDesignationIds;
   }
}
