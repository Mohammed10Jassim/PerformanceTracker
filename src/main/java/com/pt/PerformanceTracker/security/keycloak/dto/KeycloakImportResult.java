package com.pt.PerformanceTracker.security.keycloak.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class KeycloakImportResult {
   private int total;
   private int created;
   private int skipped;
   private int failed;
   private List<String> createdUsers;
   private List<String> skippedUsers;
   private List<String> failedUsers;

   @Generated
   private static List<String> $default$createdUsers() {
      return new ArrayList<>();
   }

   @Generated
   private static List<String> $default$skippedUsers() {
      return new ArrayList<>();
   }

   @Generated
   private static List<String> $default$failedUsers() {
      return new ArrayList<>();
   }

   @Generated
   KeycloakImportResult(
      final int total,
      final int created,
      final int skipped,
      final int failed,
      final List<String> createdUsers,
      final List<String> skippedUsers,
      final List<String> failedUsers
   ) {
      this.total = total;
      this.created = created;
      this.skipped = skipped;
      this.failed = failed;
      this.createdUsers = createdUsers;
      this.skippedUsers = skippedUsers;
      this.failedUsers = failedUsers;
   }

   @Generated
   public static KeycloakImportResult.KeycloakImportResultBuilder builder() {
      return new KeycloakImportResult.KeycloakImportResultBuilder();
   }

   @Generated
   public int getTotal() {
      return this.total;
   }

   @Generated
   public int getCreated() {
      return this.created;
   }

   @Generated
   public int getSkipped() {
      return this.skipped;
   }

   @Generated
   public int getFailed() {
      return this.failed;
   }

   @Generated
   public List<String> getCreatedUsers() {
      return this.createdUsers;
   }

   @Generated
   public List<String> getSkippedUsers() {
      return this.skippedUsers;
   }

   @Generated
   public List<String> getFailedUsers() {
      return this.failedUsers;
   }

   @Generated
   public static class KeycloakImportResultBuilder {
      @Generated
      private int total;
      @Generated
      private int created;
      @Generated
      private int skipped;
      @Generated
      private int failed;
      @Generated
      private boolean createdUsers$set;
      @Generated
      private List<String> createdUsers$value;
      @Generated
      private boolean skippedUsers$set;
      @Generated
      private List<String> skippedUsers$value;
      @Generated
      private boolean failedUsers$set;
      @Generated
      private List<String> failedUsers$value;

      @Generated
      KeycloakImportResultBuilder() {
      }

      @Generated
      public KeycloakImportResult.KeycloakImportResultBuilder total(final int total) {
         this.total = total;
         return this;
      }

      @Generated
      public KeycloakImportResult.KeycloakImportResultBuilder created(final int created) {
         this.created = created;
         return this;
      }

      @Generated
      public KeycloakImportResult.KeycloakImportResultBuilder skipped(final int skipped) {
         this.skipped = skipped;
         return this;
      }

      @Generated
      public KeycloakImportResult.KeycloakImportResultBuilder failed(final int failed) {
         this.failed = failed;
         return this;
      }

      @Generated
      public KeycloakImportResult.KeycloakImportResultBuilder createdUsers(final List<String> createdUsers) {
         this.createdUsers$value = createdUsers;
         this.createdUsers$set = true;
         return this;
      }

      @Generated
      public KeycloakImportResult.KeycloakImportResultBuilder skippedUsers(final List<String> skippedUsers) {
         this.skippedUsers$value = skippedUsers;
         this.skippedUsers$set = true;
         return this;
      }

      @Generated
      public KeycloakImportResult.KeycloakImportResultBuilder failedUsers(final List<String> failedUsers) {
         this.failedUsers$value = failedUsers;
         this.failedUsers$set = true;
         return this;
      }

      @Generated
      public KeycloakImportResult build() {
         List<String> createdUsers$value = this.createdUsers$value;
         if (!this.createdUsers$set) {
            createdUsers$value = KeycloakImportResult.$default$createdUsers();
         }

         List<String> skippedUsers$value = this.skippedUsers$value;
         if (!this.skippedUsers$set) {
            skippedUsers$value = KeycloakImportResult.$default$skippedUsers();
         }

         List<String> failedUsers$value = this.failedUsers$value;
         if (!this.failedUsers$set) {
            failedUsers$value = KeycloakImportResult.$default$failedUsers();
         }

         return new KeycloakImportResult(this.total, this.created, this.skipped, this.failed, createdUsers$value, skippedUsers$value, failedUsers$value);
      }

      @Generated
      @Override
      public String toString() {
         return "KeycloakImportResult.KeycloakImportResultBuilder(total="
            + this.total
            + ", created="
            + this.created
            + ", skipped="
            + this.skipped
            + ", failed="
            + this.failed
            + ", createdUsers$value="
            + this.createdUsers$value
            + ", skippedUsers$value="
            + this.skippedUsers$value
            + ", failedUsers$value="
            + this.failedUsers$value
            + ")";
      }
   }
}
