package com.pt.PerformanceTracker.organization.user.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import com.pt.PerformanceTracker.security.role.entity.RoleEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import lombok.Generated;

@Entity
@Table(
   name = "users",
   uniqueConstraints = {@UniqueConstraint(
      name = "uk_user_keycloak_id",
      columnNames = {"keycloak_id"}
   ), @UniqueConstraint(
      name = "uk_user_email",
      columnNames = {"email"}
   ), @UniqueConstraint(
      name = "uk_user_employee",
      columnNames = {"employee_id"}
   )},
   indexes = {@Index(
      name = "idx_user_keycloak_id",
      columnList = "keycloak_id"
   ), @Index(
      name = "idx_user_email",
      columnList = "email"
   ), @Index(
      name = "idx_user_employee",
      columnList = "employee_id"
   ), @Index(
      name = "idx_user_enabled",
      columnList = "enabled"
   )}
)
public class UserEntity extends BaseEntity {
   @OneToOne(
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(
      name = "employee_id",
      nullable = false,
      foreignKey = @ForeignKey(
         name = "fk_user_employee"
      )
   )
   private EmployeeEntity employee;
   @Column(
      name = "first_name",
      nullable = false,
      length = 100
   )
   private String firstName;
   @Column(
      name = "last_name",
      length = 100
   )
   private String lastName;
   @Column(
      name = "keycloak_id",
      nullable = false,
      length = 100
   )
   private String keycloakId;
   @Column(
      name = "email",
      nullable = false,
      length = 150
   )
   private String email;
   @Column(
      name = "enabled",
      nullable = false
   )
   private Boolean enabled;
   @ManyToMany(
      fetch = FetchType.LAZY
   )
   @JoinTable(
      name = "user_roles",
      joinColumns = {@JoinColumn(
         name = "user_id",
         foreignKey = @ForeignKey(
            name = "fk_user_role_user"
         )
      )},
      inverseJoinColumns = {@JoinColumn(
         name = "role_id",
         foreignKey = @ForeignKey(
            name = "fk_user_role_role"
         )
      )},
      uniqueConstraints = {@UniqueConstraint(
         name = "uk_user_role",
         columnNames = {"user_id", "role_id"}
      )}
   )
   private Set<RoleEntity> roles;

   @Generated
   private static Boolean $default$enabled() {
      return true;
   }

   @Generated
   private static Set<RoleEntity> $default$roles() {
      return new HashSet<>();
   }

   @Generated
   public static UserEntity.UserEntityBuilder builder() {
      return new UserEntity.UserEntityBuilder();
   }

   @Generated
   public EmployeeEntity getEmployee() {
      return this.employee;
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
   public String getKeycloakId() {
      return this.keycloakId;
   }

   @Generated
   public String getEmail() {
      return this.email;
   }

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
   }

   @Generated
   public Set<RoleEntity> getRoles() {
      return this.roles;
   }

   @Generated
   public void setEmployee(final EmployeeEntity employee) {
      this.employee = employee;
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
   public void setKeycloakId(final String keycloakId) {
      this.keycloakId = keycloakId;
   }

   @Generated
   public void setEmail(final String email) {
      this.email = email;
   }

   @Generated
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setRoles(final Set<RoleEntity> roles) {
      this.roles = roles;
   }

   @Generated
   public UserEntity() {
      this.enabled = $default$enabled();
      this.roles = $default$roles();
   }

   @Generated
   public UserEntity(
      final EmployeeEntity employee,
      final String firstName,
      final String lastName,
      final String keycloakId,
      final String email,
      final Boolean enabled,
      final Set<RoleEntity> roles
   ) {
      this.employee = employee;
      this.firstName = firstName;
      this.lastName = lastName;
      this.keycloakId = keycloakId;
      this.email = email;
      this.enabled = enabled;
      this.roles = roles;
   }

   @Generated
   public static class UserEntityBuilder {
      @Generated
      private EmployeeEntity employee;
      @Generated
      private String firstName;
      @Generated
      private String lastName;
      @Generated
      private String keycloakId;
      @Generated
      private String email;
      @Generated
      private boolean enabled$set;
      @Generated
      private Boolean enabled$value;
      @Generated
      private boolean roles$set;
      @Generated
      private Set<RoleEntity> roles$value;

      @Generated
      UserEntityBuilder() {
      }

      @Generated
      public UserEntity.UserEntityBuilder employee(final EmployeeEntity employee) {
         this.employee = employee;
         return this;
      }

      @Generated
      public UserEntity.UserEntityBuilder firstName(final String firstName) {
         this.firstName = firstName;
         return this;
      }

      @Generated
      public UserEntity.UserEntityBuilder lastName(final String lastName) {
         this.lastName = lastName;
         return this;
      }

      @Generated
      public UserEntity.UserEntityBuilder keycloakId(final String keycloakId) {
         this.keycloakId = keycloakId;
         return this;
      }

      @Generated
      public UserEntity.UserEntityBuilder email(final String email) {
         this.email = email;
         return this;
      }

      @Generated
      public UserEntity.UserEntityBuilder enabled(final Boolean enabled) {
         this.enabled$value = enabled;
         this.enabled$set = true;
         return this;
      }

      @Generated
      public UserEntity.UserEntityBuilder roles(final Set<RoleEntity> roles) {
         this.roles$value = roles;
         this.roles$set = true;
         return this;
      }

      @Generated
      public UserEntity build() {
         Boolean enabled$value = this.enabled$value;
         if (!this.enabled$set) {
            enabled$value = UserEntity.$default$enabled();
         }

         Set<RoleEntity> roles$value = this.roles$value;
         if (!this.roles$set) {
            roles$value = UserEntity.$default$roles();
         }

         return new UserEntity(this.employee, this.firstName, this.lastName, this.keycloakId, this.email, enabled$value, roles$value);
      }

      @Generated
      @Override
      public String toString() {
         return "UserEntity.UserEntityBuilder(employee="
            + this.employee
            + ", firstName="
            + this.firstName
            + ", lastName="
            + this.lastName
            + ", keycloakId="
            + this.keycloakId
            + ", email="
            + this.email
            + ", enabled$value="
            + this.enabled$value
            + ", roles$value="
            + this.roles$value
            + ")";
      }
   }
}
