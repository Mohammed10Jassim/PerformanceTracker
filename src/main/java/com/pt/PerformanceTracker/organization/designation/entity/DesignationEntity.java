package com.pt.PerformanceTracker.organization.designation.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Generated;

@Entity
@Table(
   name = "designations",
   indexes = {@Index(
      name = "idx_designation_name",
      columnList = "designation_name"
   )}
)
public class DesignationEntity extends BaseEntity {
   @Column(
      name = "designation_name",
      nullable = false,
      unique = true,
      length = 150
   )
   private String designationName;
   @Column(
      name = "description",
      length = 500
   )
   private String description;

   @Generated
   public static DesignationEntity.DesignationEntityBuilder builder() {
      return new DesignationEntity.DesignationEntityBuilder();
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
   public void setDesignationName(final String designationName) {
      this.designationName = designationName;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public DesignationEntity() {
   }

   @Generated
   public DesignationEntity(final String designationName, final String description) {
      this.designationName = designationName;
      this.description = description;
   }

   @Generated
   public static class DesignationEntityBuilder {
      @Generated
      private String designationName;
      @Generated
      private String description;

      @Generated
      DesignationEntityBuilder() {
      }

      @Generated
      public DesignationEntity.DesignationEntityBuilder designationName(final String designationName) {
         this.designationName = designationName;
         return this;
      }

      @Generated
      public DesignationEntity.DesignationEntityBuilder description(final String description) {
         this.description = description;
         return this;
      }

      @Generated
      public DesignationEntity build() {
         return new DesignationEntity(this.designationName, this.description);
      }

      @Generated
      @Override
      public String toString() {
         return "DesignationEntity.DesignationEntityBuilder(designationName=" + this.designationName + ", description=" + this.description + ")";
      }
   }
}
