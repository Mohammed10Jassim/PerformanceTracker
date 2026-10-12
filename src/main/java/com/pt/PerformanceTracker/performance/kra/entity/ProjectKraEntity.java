package com.pt.PerformanceTracker.performance.kra.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectDepartmentEntity;
import jakarta.persistence.Column;
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
   name = "project_kras",
   uniqueConstraints = {@UniqueConstraint(
      name = "uk_project_department_kra_name",
      columnNames = {"project_department_id", "kra_name"}
   )},
   indexes = {@Index(
      name = "idx_project_kra_project_department",
      columnList = "project_department_id"
   ), @Index(
      name = "idx_project_kra_name",
      columnList = "kra_name"
   ), @Index(
      name = "idx_project_kra_active",
      columnList = "is_active"
   )}
)
public class ProjectKraEntity extends BaseEntity {
   @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(
      name = "project_department_id",
      nullable = false,
      foreignKey = @ForeignKey(
         name = "fk_project_kra_project_department"
      )
   )
   private ProjectDepartmentEntity projectDepartment;
   @Column(
      name = "kra_name",
      nullable = false,
      length = 150
   )
   private String kraName;
   @Column(
      name = "description",
      length = 500
   )
   private String description;

   @Generated
   public static ProjectKraEntity.ProjectKraEntityBuilder builder() {
      return new ProjectKraEntity.ProjectKraEntityBuilder();
   }

   @Generated
   public ProjectDepartmentEntity getProjectDepartment() {
      return this.projectDepartment;
   }

   @Generated
   public String getKraName() {
      return this.kraName;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public void setProjectDepartment(final ProjectDepartmentEntity projectDepartment) {
      this.projectDepartment = projectDepartment;
   }

   @Generated
   public void setKraName(final String kraName) {
      this.kraName = kraName;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public ProjectKraEntity() {
   }

   @Generated
   public ProjectKraEntity(final ProjectDepartmentEntity projectDepartment, final String kraName, final String description) {
      this.projectDepartment = projectDepartment;
      this.kraName = kraName;
      this.description = description;
   }

   @Generated
   public static class ProjectKraEntityBuilder {
      @Generated
      private ProjectDepartmentEntity projectDepartment;
      @Generated
      private String kraName;
      @Generated
      private String description;

      @Generated
      ProjectKraEntityBuilder() {
      }

      @Generated
      public ProjectKraEntity.ProjectKraEntityBuilder projectDepartment(final ProjectDepartmentEntity projectDepartment) {
         this.projectDepartment = projectDepartment;
         return this;
      }

      @Generated
      public ProjectKraEntity.ProjectKraEntityBuilder kraName(final String kraName) {
         this.kraName = kraName;
         return this;
      }

      @Generated
      public ProjectKraEntity.ProjectKraEntityBuilder description(final String description) {
         this.description = description;
         return this;
      }

      @Generated
      public ProjectKraEntity build() {
         return new ProjectKraEntity(this.projectDepartment, this.kraName, this.description);
      }

      @Generated
      @Override
      public String toString() {
         return "ProjectKraEntity.ProjectKraEntityBuilder(projectDepartment="
            + this.projectDepartment
            + ", kraName="
            + this.kraName
            + ", description="
            + this.description
            + ")";
      }
   }
}
