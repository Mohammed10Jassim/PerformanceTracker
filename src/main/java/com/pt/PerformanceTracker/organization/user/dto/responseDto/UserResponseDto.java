package com.pt.PerformanceTracker.organization.user.dto.responseDto;

import java.util.Set;
import lombok.Generated;

public class UserResponseDto {
   private Long id;
   private Long employeeId;
   private Long employeeCode;
   private String keycloakId;
   private String firstName;
   private String lastName;
   private String email;
   private Boolean enabled;
   private Set<UserRoleResponseDto> roles;

   @Generated
   public static UserResponseDto.UserResponseDtoBuilder builder() {
      return new UserResponseDto.UserResponseDtoBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getEmployeeId() {
      return this.employeeId;
   }

   @Generated
   public Long getEmployeeCode() {
      return this.employeeCode;
   }

   @Generated
   public String getKeycloakId() {
      return this.keycloakId;
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
   public Boolean getEnabled() {
      return this.enabled;
   }

   @Generated
   public Set<UserRoleResponseDto> getRoles() {
      return this.roles;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setEmployeeId(final Long employeeId) {
      this.employeeId = employeeId;
   }

   @Generated
   public void setEmployeeCode(final Long employeeCode) {
      this.employeeCode = employeeCode;
   }

   @Generated
   public void setKeycloakId(final String keycloakId) {
      this.keycloakId = keycloakId;
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
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setRoles(final Set<UserRoleResponseDto> roles) {
      this.roles = roles;
   }

   @Generated
   public UserResponseDto() {
   }

   @Generated
   public UserResponseDto(
      final Long id,
      final Long employeeId,
      final Long employeeCode,
      final String keycloakId,
      final String firstName,
      final String lastName,
      final String email,
      final Boolean enabled,
      final Set<UserRoleResponseDto> roles
   ) {
      this.id = id;
      this.employeeId = employeeId;
      this.employeeCode = employeeCode;
      this.keycloakId = keycloakId;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.enabled = enabled;
      this.roles = roles;
   }

   @Generated
   public static class UserResponseDtoBuilder {
      @Generated
      private Long id;
      @Generated
      private Long employeeId;
      @Generated
      private Long employeeCode;
      @Generated
      private String keycloakId;
      @Generated
      private String firstName;
      @Generated
      private String lastName;
      @Generated
      private String email;
      @Generated
      private Boolean enabled;
      @Generated
      private Set<UserRoleResponseDto> roles;

      @Generated
      UserResponseDtoBuilder() {
      }

      @Generated
      public UserResponseDto.UserResponseDtoBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public UserResponseDto.UserResponseDtoBuilder employeeId(final Long employeeId) {
         this.employeeId = employeeId;
         return this;
      }

      @Generated
      public UserResponseDto.UserResponseDtoBuilder employeeCode(final Long employeeCode) {
         this.employeeCode = employeeCode;
         return this;
      }

      @Generated
      public UserResponseDto.UserResponseDtoBuilder keycloakId(final String keycloakId) {
         this.keycloakId = keycloakId;
         return this;
      }

      @Generated
      public UserResponseDto.UserResponseDtoBuilder firstName(final String firstName) {
         this.firstName = firstName;
         return this;
      }

      @Generated
      public UserResponseDto.UserResponseDtoBuilder lastName(final String lastName) {
         this.lastName = lastName;
         return this;
      }

      @Generated
      public UserResponseDto.UserResponseDtoBuilder email(final String email) {
         this.email = email;
         return this;
      }

      @Generated
      public UserResponseDto.UserResponseDtoBuilder enabled(final Boolean enabled) {
         this.enabled = enabled;
         return this;
      }

      @Generated
      public UserResponseDto.UserResponseDtoBuilder roles(final Set<UserRoleResponseDto> roles) {
         this.roles = roles;
         return this;
      }

      @Generated
      public UserResponseDto build() {
         return new UserResponseDto(
            this.id, this.employeeId, this.employeeCode, this.keycloakId, this.firstName, this.lastName, this.email, this.enabled, this.roles
         );
      }

      @Generated
      @Override
      public String toString() {
         return "UserResponseDto.UserResponseDtoBuilder(id="
            + this.id
            + ", employeeId="
            + this.employeeId
            + ", employeeCode="
            + this.employeeCode
            + ", keycloakId="
            + this.keycloakId
            + ", firstName="
            + this.firstName
            + ", lastName="
            + this.lastName
            + ", email="
            + this.email
            + ", enabled="
            + this.enabled
            + ", roles="
            + this.roles
            + ")";
      }
   }
}
