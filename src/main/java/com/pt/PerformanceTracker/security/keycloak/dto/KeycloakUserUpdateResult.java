package com.pt.PerformanceTracker.security.keycloak.dto;

import lombok.Generated;

public class KeycloakUserUpdateResult {
   private int total;
   private int updated;
   private int alreadyValid;

   @Generated
   public static KeycloakUserUpdateResult.KeycloakUserUpdateResultBuilder builder() {
      return new KeycloakUserUpdateResult.KeycloakUserUpdateResultBuilder();
   }

   @Generated
   public int getTotal() {
      return this.total;
   }

   @Generated
   public int getUpdated() {
      return this.updated;
   }

   @Generated
   public int getAlreadyValid() {
      return this.alreadyValid;
   }

   @Generated
   public void setTotal(final int total) {
      this.total = total;
   }

   @Generated
   public void setUpdated(final int updated) {
      this.updated = updated;
   }

   @Generated
   public void setAlreadyValid(final int alreadyValid) {
      this.alreadyValid = alreadyValid;
   }

   @Generated
   public KeycloakUserUpdateResult() {
   }

   @Generated
   public KeycloakUserUpdateResult(final int total, final int updated, final int alreadyValid) {
      this.total = total;
      this.updated = updated;
      this.alreadyValid = alreadyValid;
   }

   @Generated
   public static class KeycloakUserUpdateResultBuilder {
      @Generated
      private int total;
      @Generated
      private int updated;
      @Generated
      private int alreadyValid;

      @Generated
      KeycloakUserUpdateResultBuilder() {
      }

      @Generated
      public KeycloakUserUpdateResult.KeycloakUserUpdateResultBuilder total(final int total) {
         this.total = total;
         return this;
      }

      @Generated
      public KeycloakUserUpdateResult.KeycloakUserUpdateResultBuilder updated(final int updated) {
         this.updated = updated;
         return this;
      }

      @Generated
      public KeycloakUserUpdateResult.KeycloakUserUpdateResultBuilder alreadyValid(final int alreadyValid) {
         this.alreadyValid = alreadyValid;
         return this;
      }

      @Generated
      public KeycloakUserUpdateResult build() {
         return new KeycloakUserUpdateResult(this.total, this.updated, this.alreadyValid);
      }

      @Generated
      @Override
      public String toString() {
         return "KeycloakUserUpdateResult.KeycloakUserUpdateResultBuilder(total="
            + this.total
            + ", updated="
            + this.updated
            + ", alreadyValid="
            + this.alreadyValid
            + ")";
      }
   }
}
