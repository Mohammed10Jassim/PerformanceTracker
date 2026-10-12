package com.pt.PerformanceTracker.organization.department.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectDepartmentEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Set;
import lombok.Generated;

@Entity
@Table(
   name = "departments",
   uniqueConstraints = {@UniqueConstraint(
      name = "uk_department_code",
      columnNames = {"department_code"}
   ), @UniqueConstraint(
      name = "uk_department_name",
      columnNames = {"department_name"}
   )},
   indexes = {@Index(
      name = "idx_department_code",
      columnList = "department_code"
   ), @Index(
      name = "idx_department_name",
      columnList = "department_name"
   )}
)
public class DepartmentEntity extends BaseEntity {
   @Column(
      name = "department_code",
      nullable = false,
      updatable = false
   )
   private Long departmentCode;
   @Column(
      name = "department_name",
      nullable = false,
      length = 150
   )
   private String departmentName;
   @Column(
      length = 500
   )
   private String description;
   @OneToMany(
      mappedBy = "department",
      fetch = FetchType.LAZY
   )
   private Set<ProjectDepartmentEntity> projectDepartments;

   @Generated
   public static DepartmentEntity.DepartmentEntityBuilder builder() {
      return new DepartmentEntity.DepartmentEntityBuilder();
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
   public Set<ProjectDepartmentEntity> getProjectDepartments() {
      return this.projectDepartments;
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
   public void setProjectDepartments(final Set<ProjectDepartmentEntity> projectDepartments) {
      this.projectDepartments = projectDepartments;
   }

   @Generated
   public DepartmentEntity(
      final Long departmentCode, final String departmentName, final String description, final Set<ProjectDepartmentEntity> projectDepartments
   ) {
      this.departmentCode = departmentCode;
      this.departmentName = departmentName;
      this.description = description;
      this.projectDepartments = projectDepartments;
   }

   @Generated
   public DepartmentEntity() {
   }

   @Generated
   public static class DepartmentEntityBuilder {
      @Generated
      private Long departmentCode;
      @Generated
      private String departmentName;
      @Generated
      private String description;
      @Generated
      private Set<ProjectDepartmentEntity> projectDepartments;

      @Generated
      DepartmentEntityBuilder() {
      }

      @Generated
      public DepartmentEntity.DepartmentEntityBuilder departmentCode(final Long departmentCode) {
         this.departmentCode = departmentCode;
         return this;
      }

      @Generated
      public DepartmentEntity.DepartmentEntityBuilder departmentName(final String departmentName) {
         this.departmentName = departmentName;
         return this;
      }

      @Generated
      public DepartmentEntity.DepartmentEntityBuilder description(final String description) {
         this.description = description;
         return this;
      }

      @Generated
      public DepartmentEntity.DepartmentEntityBuilder projectDepartments(final Set<ProjectDepartmentEntity> projectDepartments) {
         this.projectDepartments = projectDepartments;
         return this;
      }

      @Generated
      public DepartmentEntity build() {
         return new DepartmentEntity(this.departmentCode, this.departmentName, this.description, this.projectDepartments);
      }

      @Generated
      @Override
      public String toString() {
         return "DepartmentEntity.DepartmentEntityBuilder(departmentCode="
            + this.departmentCode
            + ", departmentName="
            + this.departmentName
            + ", description="
            + this.description
            + ", projectDepartments="
            + this.projectDepartments
            + ")";
      }
   }
}
