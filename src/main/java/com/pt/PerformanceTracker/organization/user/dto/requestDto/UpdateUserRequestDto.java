package com.pt.PerformanceTracker.organization.user.dto.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Generated;

public class UpdateUserRequestDto {
   @NotEmpty(
      message = "At least one role is required"
   )
   private Set<Long> roleIds;
   @NotNull(
      message = "Enabled status is required"
   )
   private Boolean enabled;

   @Generated
   public static UpdateUserRequestDto.UpdateUserRequestDtoBuilder builder() {
      return new UpdateUserRequestDto.UpdateUserRequestDtoBuilder();
   }

   @Generated
   public Set<Long> getRoleIds() {
      return this.roleIds;
   }

   @Generated
   public Boolean getEnabled() {
      return this.enabled;
   }

   @Generated
   public void setRoleIds(final Set<Long> roleIds) {
      this.roleIds = roleIds;
   }

   @Generated
   public void setEnabled(final Boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public UpdateUserRequestDto() {
   }

   @Generated
   public UpdateUserRequestDto(final Set<Long> roleIds, final Boolean enabled) {
      this.roleIds = roleIds;
      this.enabled = enabled;
   }

   @Generated
   public static class UpdateUserRequestDtoBuilder {
      @Generated
      private Set<Long> roleIds;
      @Generated
      private Boolean enabled;

      @Generated
      UpdateUserRequestDtoBuilder() {
      }

      @Generated
      public UpdateUserRequestDto.UpdateUserRequestDtoBuilder roleIds(final Set<Long> roleIds) {
         this.roleIds = roleIds;
         return this;
      }

      @Generated
      public UpdateUserRequestDto.UpdateUserRequestDtoBuilder enabled(final Boolean enabled) {
         this.enabled = enabled;
         return this;
      }

      @Generated
      public UpdateUserRequestDto build() {
         return new UpdateUserRequestDto(this.roleIds, this.enabled);
      }

      @Generated
      @Override
      public String toString() {
         return "UpdateUserRequestDto.UpdateUserRequestDtoBuilder(roleIds=" + this.roleIds + ", enabled=" + this.enabled + ")";
      }
   }
}
