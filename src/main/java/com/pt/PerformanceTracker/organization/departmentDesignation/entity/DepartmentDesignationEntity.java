package com.pt.PerformanceTracker.organization.departmentDesignation.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import com.pt.PerformanceTracker.organization.designation.entity.DesignationEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Generated;

@Entity
@Table(
   name = "department_designations",
   uniqueConstraints = {@UniqueConstraint(
      name = "uk_department_designation",
      columnNames = {"department_id", "designation_id"}
   )},
   indexes = {@Index(
      name = "idx_department_designation_department",
      columnList = "department_id"
   ), @Index(
      name = "idx_department_designation_designation",
      columnList = "designation_id"
   )}
)
public class DepartmentDesignationEntity extends BaseEntity {
   @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(
      name = "department_id",
      nullable = false,
      foreignKey = @ForeignKey(
         name = "fk_departmentdesignation_department"
      )
   )
   private DepartmentEntity department;
   @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(
      name = "designation_id",
      nullable = false,
      foreignKey = @ForeignKey(
         name = "fk_departmentdesignation_designation"
      )
   )
   private DesignationEntity designation;

   @Generated
   public static DepartmentDesignationEntity.DepartmentDesignationEntityBuilder builder() {
      return new DepartmentDesignationEntity.DepartmentDesignationEntityBuilder();
   }

   @Generated
   public DepartmentEntity getDepartment() {
      return this.department;
   }

   @Generated
   public DesignationEntity getDesignation() {
      return this.designation;
   }

   @Generated
   public void setDepartment(final DepartmentEntity department) {
      this.department = department;
   }

   @Generated
   public void setDesignation(final DesignationEntity designation) {
      this.designation = designation;
   }

   @Generated
   public DepartmentDesignationEntity() {
   }

   @Generated
   public DepartmentDesignationEntity(final DepartmentEntity department, final DesignationEntity designation) {
      this.department = department;
      this.designation = designation;
   }

   @Generated
   public static class DepartmentDesignationEntityBuilder {
      @Generated
      private DepartmentEntity department;
      @Generated
      private DesignationEntity designation;

      @Generated
      DepartmentDesignationEntityBuilder() {
      }

      @Generated
      public DepartmentDesignationEntity.DepartmentDesignationEntityBuilder department(final DepartmentEntity department) {
         this.department = department;
         return this;
      }

      @Generated
      public DepartmentDesignationEntity.DepartmentDesignationEntityBuilder designation(final DesignationEntity designation) {
         this.designation = designation;
         return this;
      }

      @Generated
      public DepartmentDesignationEntity build() {
         return new DepartmentDesignationEntity(this.department, this.designation);
      }

      @Generated
      @Override
      public String toString() {
         return "DepartmentDesignationEntity.DepartmentDesignationEntityBuilder(department=" + this.department + ", designation=" + this.designation + ")";
      }
   }
}
