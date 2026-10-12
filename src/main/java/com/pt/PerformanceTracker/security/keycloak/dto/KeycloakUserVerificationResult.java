package com.pt.PerformanceTracker.security.keycloak.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class KeycloakUserVerificationResult {
   private int pmsUsersChecked;
   private int matched;
   private int missingInKeycloak;
   private int emailMismatches;
   private int employeeLinkErrors;
   private int keycloakAccountsWithoutPmsUser;
   private List<String> missingUsers;
   private List<String> emailMismatchUsers;
   private List<String> employeeLinkErrorsDetails;
   private List<String> keycloakIdMismatchUsers;
   private List<String> keycloakAccountsWithoutPmsUserDetails;

   @Generated
   private static List<String> $default$missingUsers() {
      return new ArrayList<>();
   }

   @Generated
   private static List<String> $default$emailMismatchUsers() {
      return new ArrayList<>();
   }

   @Generated
   private static List<String> $default$employeeLinkErrorsDetails() {
      return new ArrayList<>();
   }

   @Generated
   private static List<String> $default$keycloakIdMismatchUsers() {
      return new ArrayList<>();
   }

   @Generated
   private static List<String> $default$keycloakAccountsWithoutPmsUserDetails() {
      return new ArrayList<>();
   }

   @Generated
   public static KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder builder() {
      return new KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder();
   }

   @Generated
   public int getPmsUsersChecked() {
      return this.pmsUsersChecked;
   }

   @Generated
   public int getMatched() {
      return this.matched;
   }

   @Generated
   public int getMissingInKeycloak() {
      return this.missingInKeycloak;
   }

   @Generated
   public int getEmailMismatches() {
      return this.emailMismatches;
   }

   @Generated
   public int getEmployeeLinkErrors() {
      return this.employeeLinkErrors;
   }

   @Generated
   public int getKeycloakAccountsWithoutPmsUser() {
      return this.keycloakAccountsWithoutPmsUser;
   }

   @Generated
   public List<String> getMissingUsers() {
      return this.missingUsers;
   }

   @Generated
   public List<String> getEmailMismatchUsers() {
      return this.emailMismatchUsers;
   }

   @Generated
   public List<String> getEmployeeLinkErrorsDetails() {
      return this.employeeLinkErrorsDetails;
   }

   @Generated
   public List<String> getKeycloakIdMismatchUsers() {
      return this.keycloakIdMismatchUsers;
   }

   @Generated
   public List<String> getKeycloakAccountsWithoutPmsUserDetails() {
      return this.keycloakAccountsWithoutPmsUserDetails;
   }

   @Generated
   public void setPmsUsersChecked(final int pmsUsersChecked) {
      this.pmsUsersChecked = pmsUsersChecked;
   }

   @Generated
   public void setMatched(final int matched) {
      this.matched = matched;
   }

   @Generated
   public void setMissingInKeycloak(final int missingInKeycloak) {
      this.missingInKeycloak = missingInKeycloak;
   }

   @Generated
   public void setEmailMismatches(final int emailMismatches) {
      this.emailMismatches = emailMismatches;
   }

   @Generated
   public void setEmployeeLinkErrors(final int employeeLinkErrors) {
      this.employeeLinkErrors = employeeLinkErrors;
   }

   @Generated
   public void setKeycloakAccountsWithoutPmsUser(final int keycloakAccountsWithoutPmsUser) {
      this.keycloakAccountsWithoutPmsUser = keycloakAccountsWithoutPmsUser;
   }

   @Generated
   public void setMissingUsers(final List<String> missingUsers) {
      this.missingUsers = missingUsers;
   }

   @Generated
   public void setEmailMismatchUsers(final List<String> emailMismatchUsers) {
      this.emailMismatchUsers = emailMismatchUsers;
   }

   @Generated
   public void setEmployeeLinkErrorsDetails(final List<String> employeeLinkErrorsDetails) {
      this.employeeLinkErrorsDetails = employeeLinkErrorsDetails;
   }

   @Generated
   public void setKeycloakIdMismatchUsers(final List<String> keycloakIdMismatchUsers) {
      this.keycloakIdMismatchUsers = keycloakIdMismatchUsers;
   }

   @Generated
   public void setKeycloakAccountsWithoutPmsUserDetails(final List<String> keycloakAccountsWithoutPmsUserDetails) {
      this.keycloakAccountsWithoutPmsUserDetails = keycloakAccountsWithoutPmsUserDetails;
   }

   @Generated
   public KeycloakUserVerificationResult() {
      this.missingUsers = $default$missingUsers();
      this.emailMismatchUsers = $default$emailMismatchUsers();
      this.employeeLinkErrorsDetails = $default$employeeLinkErrorsDetails();
      this.keycloakIdMismatchUsers = $default$keycloakIdMismatchUsers();
      this.keycloakAccountsWithoutPmsUserDetails = $default$keycloakAccountsWithoutPmsUserDetails();
   }

   @Generated
   public KeycloakUserVerificationResult(
      final int pmsUsersChecked,
      final int matched,
      final int missingInKeycloak,
      final int emailMismatches,
      final int employeeLinkErrors,
      final int keycloakAccountsWithoutPmsUser,
      final List<String> missingUsers,
      final List<String> emailMismatchUsers,
      final List<String> employeeLinkErrorsDetails,
      final List<String> keycloakIdMismatchUsers,
      final List<String> keycloakAccountsWithoutPmsUserDetails
   ) {
      this.pmsUsersChecked = pmsUsersChecked;
      this.matched = matched;
      this.missingInKeycloak = missingInKeycloak;
      this.emailMismatches = emailMismatches;
      this.employeeLinkErrors = employeeLinkErrors;
      this.keycloakAccountsWithoutPmsUser = keycloakAccountsWithoutPmsUser;
      this.missingUsers = missingUsers;
      this.emailMismatchUsers = emailMismatchUsers;
      this.employeeLinkErrorsDetails = employeeLinkErrorsDetails;
      this.keycloakIdMismatchUsers = keycloakIdMismatchUsers;
      this.keycloakAccountsWithoutPmsUserDetails = keycloakAccountsWithoutPmsUserDetails;
   }

   @Generated
   public static class KeycloakUserVerificationResultBuilder {
      @Generated
      private int pmsUsersChecked;
      @Generated
      private int matched;
      @Generated
      private int missingInKeycloak;
      @Generated
      private int emailMismatches;
      @Generated
      private int employeeLinkErrors;
      @Generated
      private int keycloakAccountsWithoutPmsUser;
      @Generated
      private boolean missingUsers$set;
      @Generated
      private List<String> missingUsers$value;
      @Generated
      private boolean emailMismatchUsers$set;
      @Generated
      private List<String> emailMismatchUsers$value;
      @Generated
      private boolean employeeLinkErrorsDetails$set;
      @Generated
      private List<String> employeeLinkErrorsDetails$value;
      @Generated
      private boolean keycloakIdMismatchUsers$set;
      @Generated
      private List<String> keycloakIdMismatchUsers$value;
      @Generated
      private boolean keycloakAccountsWithoutPmsUserDetails$set;
      @Generated
      private List<String> keycloakAccountsWithoutPmsUserDetails$value;

      @Generated
      KeycloakUserVerificationResultBuilder() {
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder pmsUsersChecked(final int pmsUsersChecked) {
         this.pmsUsersChecked = pmsUsersChecked;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder matched(final int matched) {
         this.matched = matched;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder missingInKeycloak(final int missingInKeycloak) {
         this.missingInKeycloak = missingInKeycloak;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder emailMismatches(final int emailMismatches) {
         this.emailMismatches = emailMismatches;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder employeeLinkErrors(final int employeeLinkErrors) {
         this.employeeLinkErrors = employeeLinkErrors;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder keycloakAccountsWithoutPmsUser(final int keycloakAccountsWithoutPmsUser) {
         this.keycloakAccountsWithoutPmsUser = keycloakAccountsWithoutPmsUser;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder missingUsers(final List<String> missingUsers) {
         this.missingUsers$value = missingUsers;
         this.missingUsers$set = true;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder emailMismatchUsers(final List<String> emailMismatchUsers) {
         this.emailMismatchUsers$value = emailMismatchUsers;
         this.emailMismatchUsers$set = true;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder employeeLinkErrorsDetails(final List<String> employeeLinkErrorsDetails) {
         this.employeeLinkErrorsDetails$value = employeeLinkErrorsDetails;
         this.employeeLinkErrorsDetails$set = true;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder keycloakIdMismatchUsers(final List<String> keycloakIdMismatchUsers) {
         this.keycloakIdMismatchUsers$value = keycloakIdMismatchUsers;
         this.keycloakIdMismatchUsers$set = true;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder keycloakAccountsWithoutPmsUserDetails(
         final List<String> keycloakAccountsWithoutPmsUserDetails
      ) {
         this.keycloakAccountsWithoutPmsUserDetails$value = keycloakAccountsWithoutPmsUserDetails;
         this.keycloakAccountsWithoutPmsUserDetails$set = true;
         return this;
      }

      @Generated
      public KeycloakUserVerificationResult build() {
         List<String> missingUsers$value = this.missingUsers$value;
         if (!this.missingUsers$set) {
            missingUsers$value = KeycloakUserVerificationResult.$default$missingUsers();
         }

         List<String> emailMismatchUsers$value = this.emailMismatchUsers$value;
         if (!this.emailMismatchUsers$set) {
            emailMismatchUsers$value = KeycloakUserVerificationResult.$default$emailMismatchUsers();
         }

         List<String> employeeLinkErrorsDetails$value = this.employeeLinkErrorsDetails$value;
         if (!this.employeeLinkErrorsDetails$set) {
            employeeLinkErrorsDetails$value = KeycloakUserVerificationResult.$default$employeeLinkErrorsDetails();
         }

         List<String> keycloakIdMismatchUsers$value = this.keycloakIdMismatchUsers$value;
         if (!this.keycloakIdMismatchUsers$set) {
            keycloakIdMismatchUsers$value = KeycloakUserVerificationResult.$default$keycloakIdMismatchUsers();
         }

         List<String> keycloakAccountsWithoutPmsUserDetails$value = this.keycloakAccountsWithoutPmsUserDetails$value;
         if (!this.keycloakAccountsWithoutPmsUserDetails$set) {
            keycloakAccountsWithoutPmsUserDetails$value = KeycloakUserVerificationResult.$default$keycloakAccountsWithoutPmsUserDetails();
         }

         return new KeycloakUserVerificationResult(
            this.pmsUsersChecked,
            this.matched,
            this.missingInKeycloak,
            this.emailMismatches,
            this.employeeLinkErrors,
            this.keycloakAccountsWithoutPmsUser,
            missingUsers$value,
            emailMismatchUsers$value,
            employeeLinkErrorsDetails$value,
            keycloakIdMismatchUsers$value,
            keycloakAccountsWithoutPmsUserDetails$value
         );
      }

      @Generated
      @Override
      public String toString() {
         return "KeycloakUserVerificationResult.KeycloakUserVerificationResultBuilder(pmsUsersChecked="
            + this.pmsUsersChecked
            + ", matched="
            + this.matched
            + ", missingInKeycloak="
            + this.missingInKeycloak
            + ", emailMismatches="
            + this.emailMismatches
            + ", employeeLinkErrors="
            + this.employeeLinkErrors
            + ", keycloakAccountsWithoutPmsUser="
            + this.keycloakAccountsWithoutPmsUser
            + ", missingUsers$value="
            + this.missingUsers$value
            + ", emailMismatchUsers$value="
            + this.emailMismatchUsers$value
            + ", employeeLinkErrorsDetails$value="
            + this.employeeLinkErrorsDetails$value
            + ", keycloakIdMismatchUsers$value="
            + this.keycloakIdMismatchUsers$value
            + ", keycloakAccountsWithoutPmsUserDetails$value="
            + this.keycloakAccountsWithoutPmsUserDetails$value
            + ")";
      }
   }
}
