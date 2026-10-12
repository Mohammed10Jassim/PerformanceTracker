package com.pt.PerformanceTracker.organization.projects.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
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
   name = "project_departments",
   uniqueConstraints = {@UniqueConstraint(
      name = "uk_project_department",
      columnNames = {"project_id", "department_id"}
   )},
   indexes = {@Index(
      name = "idx_project_department_project",
      columnList = "project_id"
   ), @Index(
      name = "idx_project_department_department",
      columnList = "department_id"
   )}
)
public class ProjectDepartmentEntity extends BaseEntity {
   @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(
      name = "project_id",
      nullable = false,
      foreignKey = @ForeignKey(
         name = "fk_project_department_project"
      )
   )
   private ProjectEntity project;
   @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(
      name = "department_id",
      nullable = false,
      foreignKey = @ForeignKey(
         name = "fk_project_department_department"
      )
   )
   private DepartmentEntity department;

   @Generated
   public static ProjectDepartmentEntity.ProjectDepartmentEntityBuilder builder() {
      return new ProjectDepartmentEntity.ProjectDepartmentEntityBuilder();
   }

   @Generated
   public ProjectEntity getProject() {
      return this.project;
   }

   @Generated
   public DepartmentEntity getDepartment() {
      return this.department;
   }

   @Generated
   public void setProject(final ProjectEntity project) {
      this.project = project;
   }

   @Generated
   public void setDepartment(final DepartmentEntity department) {
      this.department = department;
   }

   @Generated
   public ProjectDepartmentEntity() {
   }

   @Generated
   public ProjectDepartmentEntity(final ProjectEntity project, final DepartmentEntity department) {
      this.project = project;
      this.department = department;
   }

   @Generated
   public static class ProjectDepartmentEntityBuilder {
      @Generated
      private ProjectEntity project;
      @Generated
      private DepartmentEntity department;

      @Generated
      ProjectDepartmentEntityBuilder() {
      }

      @Generated
      public ProjectDepartmentEntity.ProjectDepartmentEntityBuilder project(final ProjectEntity project) {
         this.project = project;
         return this;
      }

      @Generated
      public ProjectDepartmentEntity.ProjectDepartmentEntityBuilder department(final DepartmentEntity department) {
         this.department = department;
         return this;
      }

      @Generated
      public ProjectDepartmentEntity build() {
         return new ProjectDepartmentEntity(this.project, this.department);
      }

      @Generated
      @Override
      public String toString() {
         return "ProjectDepartmentEntity.ProjectDepartmentEntityBuilder(project=" + this.project + ", department=" + this.department + ")";
      }
   }
}
