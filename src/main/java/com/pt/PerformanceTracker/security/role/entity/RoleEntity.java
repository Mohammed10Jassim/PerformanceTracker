package com.pt.PerformanceTracker.security.role.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import lombok.Generated;

@Entity
@Table(
   name = "roles",
   uniqueConstraints = {@UniqueConstraint(
      name = "uk_role_name",
      columnNames = {"name"}
   )},
   indexes = {@Index(
      name = "idx_role_name",
      columnList = "name"
   )}
)
public class RoleEntity extends BaseEntity {
   @Column(
      name = "name",
      nullable = false,
      length = 50
   )
   private String name;
   @ManyToMany(
      mappedBy = "roles",
      fetch = FetchType.LAZY
   )
   private Set<UserEntity> users;

   @Generated
   private static Set<UserEntity> $default$users() {
      return new HashSet<>();
   }

   @Generated
   public static RoleEntity.RoleEntityBuilder builder() {
      return new RoleEntity.RoleEntityBuilder();
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public Set<UserEntity> getUsers() {
      return this.users;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setUsers(final Set<UserEntity> users) {
      this.users = users;
   }

   @Generated
   public RoleEntity() {
      this.users = $default$users();
   }

   @Generated
   public RoleEntity(final String name, final Set<UserEntity> users) {
      this.name = name;
      this.users = users;
   }

   @Generated
   public static class RoleEntityBuilder {
      @Generated
      private String name;
      @Generated
      private boolean users$set;
      @Generated
      private Set<UserEntity> users$value;

      @Generated
      RoleEntityBuilder() {
      }

      @Generated
      public RoleEntity.RoleEntityBuilder name(final String name) {
         this.name = name;
         return this;
      }

      @Generated
      public RoleEntity.RoleEntityBuilder users(final Set<UserEntity> users) {
         this.users$value = users;
         this.users$set = true;
         return this;
      }

      @Generated
      public RoleEntity build() {
         Set<UserEntity> users$value = this.users$value;
         if (!this.users$set) {
            users$value = RoleEntity.$default$users();
         }

         return new RoleEntity(this.name, users$value);
      }

      @Generated
      @Override
      public String toString() {
         return "RoleEntity.RoleEntityBuilder(name=" + this.name + ", users$value=" + this.users$value + ")";
      }
   }
}
