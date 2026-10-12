package com.pt.PerformanceTracker.performance.kpaTask.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
   name = "project_kpa_tasks",
   uniqueConstraints = {@UniqueConstraint(
      name = "uk_kpa_employee_task_name",
      columnNames = {"project_kpa_employee_id", "task_name"}
   )},
   indexes = {@Index(
      name = "idx_kpa_task_assignment",
      columnList = "project_kpa_employee_id"
   ), @Index(
      name = "idx_kpa_task_status",
      columnList = "status"
   ), @Index(
      name = "idx_kpa_task_active",
      columnList = "is_active"
   )}
)
public class ProjectKpaTaskEntity extends BaseEntity {
   @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(
      name = "project_kpa_employee_id",
      nullable = false,
      foreignKey = @ForeignKey(
         name = "fk_kpa_task_assignment"
      )
   )
   private ProjectKpaEmployeeEntity kpaEmployeeAssignment;
   @Column(
      name = "task_name",
      nullable = false,
      length = 200
   )
   private String taskName;
   @Column(
      name = "description",
      length = 1000
   )
   private String description;
   @Enumerated(EnumType.STRING)
   @Column(
      name = "status",
      nullable = false,
      length = 30
   )
   private TaskStatus status;

   @Generated
   private static TaskStatus $default$status() {
      return TaskStatus.PENDING;
   }

   @Generated
   public static ProjectKpaTaskEntity.ProjectKpaTaskEntityBuilder builder() {
      return new ProjectKpaTaskEntity.ProjectKpaTaskEntityBuilder();
   }

   @Generated
   public ProjectKpaEmployeeEntity getKpaEmployeeAssignment() {
      return this.kpaEmployeeAssignment;
   }

   @Generated
   public String getTaskName() {
      return this.taskName;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public TaskStatus getStatus() {
      return this.status;
   }

   @Generated
   public void setKpaEmployeeAssignment(final ProjectKpaEmployeeEntity kpaEmployeeAssignment) {
      this.kpaEmployeeAssignment = kpaEmployeeAssignment;
   }

   @Generated
   public void setTaskName(final String taskName) {
      this.taskName = taskName;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public void setStatus(final TaskStatus status) {
      this.status = status;
   }

   @Generated
   public ProjectKpaTaskEntity() {
      this.status = $default$status();
   }

   @Generated
   public ProjectKpaTaskEntity(final ProjectKpaEmployeeEntity kpaEmployeeAssignment, final String taskName, final String description, final TaskStatus status) {
      this.kpaEmployeeAssignment = kpaEmployeeAssignment;
      this.taskName = taskName;
      this.description = description;
      this.status = status;
   }

   @Generated
   public static class ProjectKpaTaskEntityBuilder {
      @Generated
      private ProjectKpaEmployeeEntity kpaEmployeeAssignment;
      @Generated
      private String taskName;
      @Generated
      private String description;
      @Generated
      private boolean status$set;
      @Generated
      private TaskStatus status$value;

      @Generated
      ProjectKpaTaskEntityBuilder() {
      }

      @Generated
      public ProjectKpaTaskEntity.ProjectKpaTaskEntityBuilder kpaEmployeeAssignment(final ProjectKpaEmployeeEntity kpaEmployeeAssignment) {
         this.kpaEmployeeAssignment = kpaEmployeeAssignment;
         return this;
      }

      @Generated
      public ProjectKpaTaskEntity.ProjectKpaTaskEntityBuilder taskName(final String taskName) {
         this.taskName = taskName;
         return this;
      }

      @Generated
      public ProjectKpaTaskEntity.ProjectKpaTaskEntityBuilder description(final String description) {
         this.description = description;
         return this;
      }

      @Generated
      public ProjectKpaTaskEntity.ProjectKpaTaskEntityBuilder status(final TaskStatus status) {
         this.status$value = status;
         this.status$set = true;
         return this;
      }

      @Generated
      public ProjectKpaTaskEntity build() {
         TaskStatus status$value = this.status$value;
         if (!this.status$set) {
            status$value = ProjectKpaTaskEntity.$default$status();
         }

         return new ProjectKpaTaskEntity(this.kpaEmployeeAssignment, this.taskName, this.description, status$value);
      }

      @Generated
      @Override
      public String toString() {
         return "ProjectKpaTaskEntity.ProjectKpaTaskEntityBuilder(kpaEmployeeAssignment="
            + this.kpaEmployeeAssignment
            + ", taskName="
            + this.taskName
            + ", description="
            + this.description
            + ", status$value="
            + this.status$value
            + ")";
      }
   }
}
