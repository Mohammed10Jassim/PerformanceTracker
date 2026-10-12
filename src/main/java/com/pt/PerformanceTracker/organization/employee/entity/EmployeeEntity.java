package com.pt.PerformanceTracker.organization.employee.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.organization.departmentDesignation.entity.DepartmentDesignationEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Generated;

@Entity
@Table(
   name = "employees",
   uniqueConstraints = {@UniqueConstraint(
      name = "uk_employee_code",
      columnNames = {"employee_code"}
   ), @UniqueConstraint(
      name = "uk_employee_email",
      columnNames = {"email"}
   )},
   indexes = {@Index(
      name = "idx_employee_email",
      columnList = "email"
   ), @Index(
      name = "idx_employee_code",
      columnList = "employee_code"
   ), @Index(
      name = "idx_employee_active",
      columnList = "is_active"
   ), @Index(
      name = "idx_employee_first_name",
      columnList = "first_name"
   ), @Index(
      name = "idx_employee_last_name",
      columnList = "last_name"
   )}
)
public class EmployeeEntity extends BaseEntity {
   @Column(
      name = "employee_code",
      nullable = false
   )
   private Long employeeCode;
   @Column(
      name = "first_name",
      nullable = false,
      length = 100
   )
   private String firstName;
   @Column(
      name = "last_name",
      nullable = false,
      length = 100
   )
   private String lastName;
   @Column(
      name = "email",
      nullable = false,
      length = 150
   )
   private String email;
   @Column(
      name = "joining_date"
   )
   private LocalDate joiningDate;
   @ManyToMany(
      fetch = FetchType.LAZY
   )
   @JoinTable(
      name = "employee_department_designations",
      joinColumns = {@JoinColumn(
         name = "employee_id",
         nullable = false,
         foreignKey = @ForeignKey(
            name = "fk_employee_department_designation_employee"
         )
      )},
      inverseJoinColumns = {@JoinColumn(
         name = "department_designation_id",
         nullable = false,
         foreignKey = @ForeignKey(
            name = "fk_employee_department_designation_mapping"
         )
      )},
      uniqueConstraints = {@UniqueConstraint(
         name = "uk_employee_department_designation",
         columnNames = {"employee_id", "department_designation_id"}
      )},
      indexes = {@Index(
         name = "idx_employee_department_designation_employee",
         columnList = "employee_id"
      ), @Index(
         name = "idx_employee_department_designation_mapping",
         columnList = "department_designation_id"
      )}
   )
   private Set<DepartmentDesignationEntity> departmentDesignations;

   @Generated
   private static Set<DepartmentDesignationEntity> $default$departmentDesignations() {
      return new HashSet<>();
   }

   @Generated
   public static EmployeeEntity.EmployeeEntityBuilder builder() {
      return new EmployeeEntity.EmployeeEntityBuilder();
   }

   @Generated
   public Long getEmployeeCode() {
      return this.employeeCode;
   }

   @Generated
   public String getFirstName() {
      return this.firstName;
   }

   @Generated
   public String getLastName() {
      return this.lastName;
   }

   @Generated
   public String getEmail() {
      return this.email;
   }

   @Generated
   public LocalDate getJoiningDate() {
      return this.joiningDate;
   }

   @Generated
   public Set<DepartmentDesignationEntity> getDepartmentDesignations() {
      return this.departmentDesignations;
   }

   @Generated
   public void setEmployeeCode(final Long employeeCode) {
      this.employeeCode = employeeCode;
   }

   @Generated
   public void setFirstName(final String firstName) {
      this.firstName = firstName;
   }

   @Generated
   public void setLastName(final String lastName) {
      this.lastName = lastName;
   }

   @Generated
   public void setEmail(final String email) {
      this.email = email;
   }

   @Generated
   public void setJoiningDate(final LocalDate joiningDate) {
      this.joiningDate = joiningDate;
   }

   @Generated
   public void setDepartmentDesignations(final Set<DepartmentDesignationEntity> departmentDesignations) {
      this.departmentDesignations = departmentDesignations;
   }

   @Generated
   public EmployeeEntity() {
      this.departmentDesignations = $default$departmentDesignations();
   }

   @Generated
   public EmployeeEntity(
      final Long employeeCode,
      final String firstName,
      final String lastName,
      final String email,
      final LocalDate joiningDate,
      final Set<DepartmentDesignationEntity> departmentDesignations
   ) {
      this.employeeCode = employeeCode;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.joiningDate = joiningDate;
      this.departmentDesignations = departmentDesignations;
   }

   @Generated
   public static class EmployeeEntityBuilder {
      @Generated
      private Long employeeCode;
      @Generated
      private String firstName;
      @Generated
      private String lastName;
      @Generated
      private String email;
      @Generated
      private LocalDate joiningDate;
      @Generated
      private boolean departmentDesignations$set;
      @Generated
      private Set<DepartmentDesignationEntity> departmentDesignations$value;

      @Generated
      EmployeeEntityBuilder() {
      }

      @Generated
      public EmployeeEntity.EmployeeEntityBuilder employeeCode(final Long employeeCode) {
         this.employeeCode = employeeCode;
         return this;
      }

      @Generated
      public EmployeeEntity.EmployeeEntityBuilder firstName(final String firstName) {
         this.firstName = firstName;
         return this;
      }

      @Generated
      public EmployeeEntity.EmployeeEntityBuilder lastName(final String lastName) {
         this.lastName = lastName;
         return this;
      }

      @Generated
      public EmployeeEntity.EmployeeEntityBuilder email(final String email) {
         this.email = email;
         return this;
      }

      @Generated
      public EmployeeEntity.EmployeeEntityBuilder joiningDate(final LocalDate joiningDate) {
         this.joiningDate = joiningDate;
         return this;
      }

      @Generated
      public EmployeeEntity.EmployeeEntityBuilder departmentDesignations(final Set<DepartmentDesignationEntity> departmentDesignations) {
         this.departmentDesignations$value = departmentDesignations;
         this.departmentDesignations$set = true;
         return this;
      }

      @Generated
      public EmployeeEntity build() {
         Set<DepartmentDesignationEntity> departmentDesignations$value = this.departmentDesignations$value;
         if (!this.departmentDesignations$set) {
            departmentDesignations$value = EmployeeEntity.$default$departmentDesignations();
         }

         return new EmployeeEntity(this.employeeCode, this.firstName, this.lastName, this.email, this.joiningDate, departmentDesignations$value);
      }

      @Generated
      @Override
      public String toString() {
         return "EmployeeEntity.EmployeeEntityBuilder(employeeCode="
            + this.employeeCode
            + ", firstName="
            + this.firstName
            + ", lastName="
            + this.lastName
            + ", email="
            + this.email
            + ", joiningDate="
            + this.joiningDate
            + ", departmentDesignations$value="
            + this.departmentDesignations$value
            + ")";
      }
   }
}
