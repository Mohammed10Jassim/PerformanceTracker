package com.pt.PerformanceTracker.security.keycloak.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class KeycloakUserSyncResult {
   private int total;
   private int created;
   private int updated;
   private int skipped;
   private int failed;
   private List<String> createdUsers;
   private List<String> updatedUsers;
   private List<String> skippedUsers;
   private List<String> failedUsers;

   @Generated
   private static List<String> $default$createdUsers() {
      return new ArrayList<>();
   }

   @Generated
   private static List<String> $default$updatedUsers() {
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
   public static KeycloakUserSyncResult.KeycloakUserSyncResultBuilder builder() {
      return new KeycloakUserSyncResult.KeycloakUserSyncResultBuilder();
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
   public int getUpdated() {
      return this.updated;
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
   public List<String> getUpdatedUsers() {
      return this.updatedUsers;
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
   public void setTotal(final int total) {
      this.total = total;
   }

   @Generated
   public void setCreated(final int created) {
      this.created = created;
   }

   @Generated
   public void setUpdated(final int updated) {
      this.updated = updated;
   }

   @Generated
   public void setSkipped(final int skipped) {
      this.skipped = skipped;
   }

   @Generated
   public void setFailed(final int failed) {
      this.failed = failed;
   }

   @Generated
   public void setCreatedUsers(final List<String> createdUsers) {
      this.createdUsers = createdUsers;
   }

   @Generated
   public void setUpdatedUsers(final List<String> updatedUsers) {
      this.updatedUsers = updatedUsers;
   }

   @Generated
   public void setSkippedUsers(final List<String> skippedUsers) {
      this.skippedUsers = skippedUsers;
   }

   @Generated
   public void setFailedUsers(final List<String> failedUsers) {
      this.failedUsers = failedUsers;
   }

   @Generated
   public KeycloakUserSyncResult() {
      this.createdUsers = $default$createdUsers();
      this.updatedUsers = $default$updatedUsers();
      this.skippedUsers = $default$skippedUsers();
      this.failedUsers = $default$failedUsers();
   }

   @Generated
   public KeycloakUserSyncResult(
      final int total,
      final int created,
      final int updated,
      final int skipped,
      final int failed,
      final List<String> createdUsers,
      final List<String> updatedUsers,
      final List<String> skippedUsers,
      final List<String> failedUsers
   ) {
      this.total = total;
      this.created = created;
      this.updated = updated;
      this.skipped = skipped;
      this.failed = failed;
      this.createdUsers = createdUsers;
      this.updatedUsers = updatedUsers;
      this.skippedUsers = skippedUsers;
      this.failedUsers = failedUsers;
   }

   @Generated
   public static class KeycloakUserSyncResultBuilder {
      @Generated
      private int total;
      @Generated
      private int created;
      @Generated
      private int updated;
      @Generated
      private int skipped;
      @Generated
      private int failed;
      @Generated
      private boolean createdUsers$set;
      @Generated
      private List<String> createdUsers$value;
      @Generated
      private boolean updatedUsers$set;
      @Generated
      private List<String> updatedUsers$value;
      @Generated
      private boolean skippedUsers$set;
      @Generated
      private List<String> skippedUsers$value;
      @Generated
      private boolean failedUsers$set;
      @Generated
      private List<String> failedUsers$value;

      @Generated
      KeycloakUserSyncResultBuilder() {
      }

      @Generated
      public KeycloakUserSyncResult.KeycloakUserSyncResultBuilder total(final int total) {
         this.total = total;
         return this;
      }

      @Generated
      public KeycloakUserSyncResult.KeycloakUserSyncResultBuilder created(final int created) {
         this.created = created;
         return this;
      }

      @Generated
      public KeycloakUserSyncResult.KeycloakUserSyncResultBuilder updated(final int updated) {
         this.updated = updated;
         return this;
      }

      @Generated
      public KeycloakUserSyncResult.KeycloakUserSyncResultBuilder skipped(final int skipped) {
         this.skipped = skipped;
         return this;
      }

      @Generated
      public KeycloakUserSyncResult.KeycloakUserSyncResultBuilder failed(final int failed) {
         this.failed = failed;
         return this;
      }

      @Generated
      public KeycloakUserSyncResult.KeycloakUserSyncResultBuilder createdUsers(final List<String> createdUsers) {
         this.createdUsers$value = createdUsers;
         this.createdUsers$set = true;
         return this;
      }

      @Generated
      public KeycloakUserSyncResult.KeycloakUserSyncResultBuilder updatedUsers(final List<String> updatedUsers) {
         this.updatedUsers$value = updatedUsers;
         this.updatedUsers$set = true;
         return this;
      }

      @Generated
      public KeycloakUserSyncResult.KeycloakUserSyncResultBuilder skippedUsers(final List<String> skippedUsers) {
         this.skippedUsers$value = skippedUsers;
         this.skippedUsers$set = true;
         return this;
      }

      @Generated
      public KeycloakUserSyncResult.KeycloakUserSyncResultBuilder failedUsers(final List<String> failedUsers) {
         this.failedUsers$value = failedUsers;
         this.failedUsers$set = true;
         return this;
      }

      @Generated
      public KeycloakUserSyncResult build() {
         List<String> createdUsers$value = this.createdUsers$value;
         if (!this.createdUsers$set) {
            createdUsers$value = KeycloakUserSyncResult.$default$createdUsers();
         }

         List<String> updatedUsers$value = this.updatedUsers$value;
         if (!this.updatedUsers$set) {
            updatedUsers$value = KeycloakUserSyncResult.$default$updatedUsers();
         }

         List<String> skippedUsers$value = this.skippedUsers$value;
         if (!this.skippedUsers$set) {
            skippedUsers$value = KeycloakUserSyncResult.$default$skippedUsers();
         }

         List<String> failedUsers$value = this.failedUsers$value;
         if (!this.failedUsers$set) {
            failedUsers$value = KeycloakUserSyncResult.$default$failedUsers();
         }

         return new KeycloakUserSyncResult(
            this.total, this.created, this.updated, this.skipped, this.failed, createdUsers$value, updatedUsers$value, skippedUsers$value, failedUsers$value
         );
      }

      @Generated
      @Override
      public String toString() {
         return "KeycloakUserSyncResult.KeycloakUserSyncResultBuilder(total="
            + this.total
            + ", created="
            + this.created
            + ", updated="
            + this.updated
            + ", skipped="
            + this.skipped
            + ", failed="
            + this.failed
            + ", createdUsers$value="
            + this.createdUsers$value
            + ", updatedUsers$value="
            + this.updatedUsers$value
            + ", skippedUsers$value="
            + this.skippedUsers$value
            + ", failedUsers$value="
            + this.failedUsers$value
            + ")";
      }
   }
}
