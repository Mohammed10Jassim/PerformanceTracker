package com.pt.PerformanceTracker.performance.kpaEmployee.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import com.pt.PerformanceTracker.performance.kpa.entity.ProjectKpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.Generated;

@Entity
@Table(
   name = "project_kpa_employees",
   uniqueConstraints = {@UniqueConstraint(
      name = "uk_project_kpa_employee",
      columnNames = {"project_kpa_id", "employee_id"}
   )},
   indexes = {@Index(
      name = "idx_project_kpa_employee_kpa",
      columnList = "project_kpa_id"
   ), @Index(
      name = "idx_project_kpa_employee_employee",
      columnList = "employee_id"
   ), @Index(
      name = "idx_project_kpa_employee_active",
      columnList = "is_active"
   )}
)
public class ProjectKpaEmployeeEntity extends BaseEntity {
   @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(
      name = "project_kpa_id",
      nullable = false,
      foreignKey = @ForeignKey(
         name = "fk_project_kpa_employee_kpa"
      )
   )
   private ProjectKpaEntity projectKpa;
   @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(
      name = "employee_id",
      nullable = false,
      foreignKey = @ForeignKey(
         name = "fk_project_kpa_employee_employee"
      )
   )
   private EmployeeEntity employee;
   @Column(
      name = "completion_percentage",
      nullable = false,
      precision = 5,
      scale = 2
   )
   private BigDecimal completionPercentage;

   @Generated
   private static BigDecimal $default$completionPercentage() {
      return BigDecimal.ZERO;
   }

   @Generated
   public static ProjectKpaEmployeeEntity.ProjectKpaEmployeeEntityBuilder builder() {
      return new ProjectKpaEmployeeEntity.ProjectKpaEmployeeEntityBuilder();
   }

   @Generated
   public ProjectKpaEntity getProjectKpa() {
      return this.projectKpa;
   }

   @Generated
   public EmployeeEntity getEmployee() {
      return this.employee;
   }

   @Generated
   public BigDecimal getCompletionPercentage() {
      return this.completionPercentage;
   }

   @Generated
   public void setProjectKpa(final ProjectKpaEntity projectKpa) {
      this.projectKpa = projectKpa;
   }

   @Generated
   public void setEmployee(final EmployeeEntity employee) {
      this.employee = employee;
   }

   @Generated
   public void setCompletionPercentage(final BigDecimal completionPercentage) {
      this.completionPercentage = completionPercentage;
   }

   @Generated
   public ProjectKpaEmployeeEntity() {
      this.completionPercentage = $default$completionPercentage();
   }

   @Generated
   public ProjectKpaEmployeeEntity(final ProjectKpaEntity projectKpa, final EmployeeEntity employee, final BigDecimal completionPercentage) {
      this.projectKpa = projectKpa;
      this.employee = employee;
      this.completionPercentage = completionPercentage;
   }

   @Generated
   public static class ProjectKpaEmployeeEntityBuilder {
      @Generated
      private ProjectKpaEntity projectKpa;
      @Generated
      private EmployeeEntity employee;
      @Generated
      private boolean completionPercentage$set;
      @Generated
      private BigDecimal completionPercentage$value;

      @Generated
      ProjectKpaEmployeeEntityBuilder() {
      }

      @Generated
      public ProjectKpaEmployeeEntity.ProjectKpaEmployeeEntityBuilder projectKpa(final ProjectKpaEntity projectKpa) {
         this.projectKpa = projectKpa;
         return this;
      }

      @Generated
      public ProjectKpaEmployeeEntity.ProjectKpaEmployeeEntityBuilder employee(final EmployeeEntity employee) {
         this.employee = employee;
         return this;
      }

      @Generated
      public ProjectKpaEmployeeEntity.ProjectKpaEmployeeEntityBuilder completionPercentage(final BigDecimal completionPercentage) {
         this.completionPercentage$value = completionPercentage;
         this.completionPercentage$set = true;
         return this;
      }

      @Generated
      public ProjectKpaEmployeeEntity build() {
         BigDecimal completionPercentage$value = this.completionPercentage$value;
         if (!this.completionPercentage$set) {
            completionPercentage$value = ProjectKpaEmployeeEntity.$default$completionPercentage();
         }

         return new ProjectKpaEmployeeEntity(this.projectKpa, this.employee, completionPercentage$value);
      }

      @Generated
      @Override
      public String toString() {
         return "ProjectKpaEmployeeEntity.ProjectKpaEmployeeEntityBuilder(projectKpa="
            + this.projectKpa
            + ", employee="
            + this.employee
            + ", completionPercentage$value="
            + this.completionPercentage$value
            + ")";
      }
   }
}
