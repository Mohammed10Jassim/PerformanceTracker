package com.pt.PerformanceTracker.organization.user.dto.responseDto;

import lombok.Generated;

public class UserRoleResponseDto {
   private Long id;
   private String name;

   @Generated
   UserRoleResponseDto(final Long id, final String name) {
      this.id = id;
      this.name = name;
   }

   @Generated
   public static UserRoleResponseDto.UserRoleResponseDtoBuilder builder() {
      return new UserRoleResponseDto.UserRoleResponseDtoBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public static class UserRoleResponseDtoBuilder {
      @Generated
      private Long id;
      @Generated
      private String name;

      @Generated
      UserRoleResponseDtoBuilder() {
      }

      @Generated
      public UserRoleResponseDto.UserRoleResponseDtoBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public UserRoleResponseDto.UserRoleResponseDtoBuilder name(final String name) {
         this.name = name;
         return this;
      }

      @Generated
      public UserRoleResponseDto build() {
         return new UserRoleResponseDto(this.id, this.name);
      }

      @Generated
      @Override
      public String toString() {
         return "UserRoleResponseDto.UserRoleResponseDtoBuilder(id=" + this.id + ", name=" + this.name + ")";
      }
   }
}
