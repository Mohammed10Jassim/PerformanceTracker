package com.pt.PerformanceTracker.security.keycloak.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Generated;

public class KeycloakUserResponse {
   private String id;
   private String username;
   private String firstName;
   private String lastName;
   private String email;
   private Boolean enabled;
   private Boolean emailVerified;
   private Set<String> roles;

   @Generated
   private static Set<String> $default$roles() {
      return new HashSet<>();
   }

   @Generated
   public static KeycloakUserResponse.KeycloakUserResponseBuilder builder() {
      return new KeycloakUserResponse.KeycloakUserResponseBuilder();
   }

   @Generated
   public String getId() {
      return this.id;
   }

   @Generated
   public String getUsername() {
      return this.username;
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
   public Boolean getEmailVerified() {
      return this.emailVerified;
   }

   @Generated
   public Set<String> getRoles() {
      return this.roles;
   }

   @Generated
   public void setId(final String id) {
      this.id = id;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
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
   public void setEmailVerified(final Boolean emailVerified) {
      this.emailVerified = emailVerified;
   }

   @Generated
   public void setRoles(final Set<String> roles) {
      this.roles = roles;
   }

   @Generated
   public KeycloakUserResponse() {
      this.roles = $default$roles();
   }

   @Generated
   public KeycloakUserResponse(
      final String id,
      final String username,
      final String firstName,
      final String lastName,
      final String email,
      final Boolean enabled,
      final Boolean emailVerified,
      final Set<String> roles
   ) {
      this.id = id;
      this.username = username;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.enabled = enabled;
      this.emailVerified = emailVerified;
      this.roles = roles;
   }

   @Generated
   public static class KeycloakUserResponseBuilder {
      @Generated
      private String id;
      @Generated
      private String username;
      @Generated
      private String firstName;
      @Generated
      private String lastName;
      @Generated
      private String email;
      @Generated
      private Boolean enabled;
      @Generated
      private Boolean emailVerified;
      @Generated
      private boolean roles$set;
      @Generated
      private Set<String> roles$value;

      @Generated
      KeycloakUserResponseBuilder() {
      }

      @Generated
      public KeycloakUserResponse.KeycloakUserResponseBuilder id(final String id) {
         this.id = id;
         return this;
      }

      @Generated
      public KeycloakUserResponse.KeycloakUserResponseBuilder username(final String username) {
         this.username = username;
         return this;
      }

      @Generated
      public KeycloakUserResponse.KeycloakUserResponseBuilder firstName(final String firstName) {
         this.firstName = firstName;
         return this;
      }

      @Generated
      public KeycloakUserResponse.KeycloakUserResponseBuilder lastName(final String lastName) {
         this.lastName = lastName;
         return this;
      }

      @Generated
      public KeycloakUserResponse.KeycloakUserResponseBuilder email(final String email) {
         this.email = email;
         return this;
      }

      @Generated
      public KeycloakUserResponse.KeycloakUserResponseBuilder enabled(final Boolean enabled) {
         this.enabled = enabled;
         return this;
      }

      @Generated
      public KeycloakUserResponse.KeycloakUserResponseBuilder emailVerified(final Boolean emailVerified) {
         this.emailVerified = emailVerified;
         return this;
      }

      @Generated
      public KeycloakUserResponse.KeycloakUserResponseBuilder roles(final Set<String> roles) {
         this.roles$value = roles;
         this.roles$set = true;
         return this;
      }

      @Generated
      public KeycloakUserResponse build() {
         Set<String> roles$value = this.roles$value;
         if (!this.roles$set) {
            roles$value = KeycloakUserResponse.$default$roles();
         }

         return new KeycloakUserResponse(this.id, this.username, this.firstName, this.lastName, this.email, this.enabled, this.emailVerified, roles$value);
      }

      @Generated
      @Override
      public String toString() {
         return "KeycloakUserResponse.KeycloakUserResponseBuilder(id="
            + this.id
            + ", username="
            + this.username
            + ", firstName="
            + this.firstName
            + ", lastName="
            + this.lastName
            + ", email="
            + this.email
            + ", enabled="
            + this.enabled
            + ", emailVerified="
            + this.emailVerified
            + ", roles$value="
            + this.roles$value
            + ")";
      }
   }
}
