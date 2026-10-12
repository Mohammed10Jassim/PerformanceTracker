package com.pt.PerformanceTracker.organization.projects.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Set;
import lombok.Generated;

@Entity
@Table(
   name = "projects",
   indexes = {@Index(
      name = "idx_project_name",
      columnList = "project_name"
   ), @Index(
      name = "idx_project_start_date",
      columnList = "start_date"
   ), @Index(
      name = "idx_project_end_date",
      columnList = "end_date"
   ), @Index(
      name = "idx_project_status",
      columnList = "status"
   )}
)
public class ProjectEntity extends BaseEntity {
   @Column(
      name = "project_name",
      nullable = false,
      length = 150
   )
   private String projectName;
   @Column(
      name = "description",
      length = 1000
   )
   private String description;
   @Column(
      name = "start_date"
   )
   private LocalDate startDate;
   @Column(
      name = "end_date"
   )
   private LocalDate endDate;
   @OneToMany(
      mappedBy = "project",
      fetch = FetchType.LAZY
   )
   private Set<ProjectDepartmentEntity> projectDepartments;
   @Enumerated(EnumType.STRING)
   @Column(
      name = "status",
      nullable = false,
      length = 30
   )
   private ProjectStatus status;

   @Generated
   private static ProjectStatus $default$status() {
      return ProjectStatus.DRAFT;
   }

   @Generated
   public static ProjectEntity.ProjectEntityBuilder builder() {
      return new ProjectEntity.ProjectEntityBuilder();
   }

   @Generated
   public String getProjectName() {
      return this.projectName;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public LocalDate getStartDate() {
      return this.startDate;
   }

   @Generated
   public LocalDate getEndDate() {
      return this.endDate;
   }

   @Generated
   public Set<ProjectDepartmentEntity> getProjectDepartments() {
      return this.projectDepartments;
   }

   @Generated
   public ProjectStatus getStatus() {
      return this.status;
   }

   @Generated
   public void setProjectName(final String projectName) {
      this.projectName = projectName;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public void setStartDate(final LocalDate startDate) {
      this.startDate = startDate;
   }

   @Generated
   public void setEndDate(final LocalDate endDate) {
      this.endDate = endDate;
   }

   @Generated
   public void setProjectDepartments(final Set<ProjectDepartmentEntity> projectDepartments) {
      this.projectDepartments = projectDepartments;
   }

   @Generated
   public void setStatus(final ProjectStatus status) {
      this.status = status;
   }

   @Generated
   public ProjectEntity() {
      this.status = $default$status();
   }

   @Generated
   public ProjectEntity(
      final String projectName,
      final String description,
      final LocalDate startDate,
      final LocalDate endDate,
      final Set<ProjectDepartmentEntity> projectDepartments,
      final ProjectStatus status
   ) {
      this.projectName = projectName;
      this.description = description;
      this.startDate = startDate;
      this.endDate = endDate;
      this.projectDepartments = projectDepartments;
      this.status = status;
   }

   @Generated
   public static class ProjectEntityBuilder {
      @Generated
      private String projectName;
      @Generated
      private String description;
      @Generated
      private LocalDate startDate;
      @Generated
      private LocalDate endDate;
      @Generated
      private Set<ProjectDepartmentEntity> projectDepartments;
      @Generated
      private boolean status$set;
      @Generated
      private ProjectStatus status$value;

      @Generated
      ProjectEntityBuilder() {
      }

      @Generated
      public ProjectEntity.ProjectEntityBuilder projectName(final String projectName) {
         this.projectName = projectName;
         return this;
      }

      @Generated
      public ProjectEntity.ProjectEntityBuilder description(final String description) {
         this.description = description;
         return this;
      }

      @Generated
      public ProjectEntity.ProjectEntityBuilder startDate(final LocalDate startDate) {
         this.startDate = startDate;
         return this;
      }

      @Generated
      public ProjectEntity.ProjectEntityBuilder endDate(final LocalDate endDate) {
         this.endDate = endDate;
         return this;
      }

      @Generated
      public ProjectEntity.ProjectEntityBuilder projectDepartments(final Set<ProjectDepartmentEntity> projectDepartments) {
         this.projectDepartments = projectDepartments;
         return this;
      }

      @Generated
      public ProjectEntity.ProjectEntityBuilder status(final ProjectStatus status) {
         this.status$value = status;
         this.status$set = true;
         return this;
      }

      @Generated
      public ProjectEntity build() {
         ProjectStatus status$value = this.status$value;
         if (!this.status$set) {
            status$value = ProjectEntity.$default$status();
         }

         return new ProjectEntity(this.projectName, this.description, this.startDate, this.endDate, this.projectDepartments, status$value);
      }

      @Generated
      @Override
      public String toString() {
         return "ProjectEntity.ProjectEntityBuilder(projectName="
            + this.projectName
            + ", description="
            + this.description
            + ", startDate="
            + this.startDate
            + ", endDate="
            + this.endDate
            + ", projectDepartments="
            + this.projectDepartments
            + ", status$value="
            + this.status$value
            + ")";
      }
   }
}
