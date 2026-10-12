package com.pt.PerformanceTracker.common.exception.response;

import java.util.Map;
import lombok.Generated;

public class ValidationErrorResponse extends ErrorResponse {
   private Map<String, String> validationErrors;

   @Generated
   protected ValidationErrorResponse(final ValidationErrorResponse.ValidationErrorResponseBuilder<?, ?> b) {
      super(b);
      this.validationErrors = b.validationErrors;
   }

   @Generated
   public static ValidationErrorResponse.ValidationErrorResponseBuilder<?, ?> builder() {
      return new ValidationErrorResponse.ValidationErrorResponseBuilderImpl();
   }

   @Generated
   public Map<String, String> getValidationErrors() {
      return this.validationErrors;
   }

   @Generated
   public abstract static class ValidationErrorResponseBuilder<C extends ValidationErrorResponse, B extends ValidationErrorResponse.ValidationErrorResponseBuilder<C, B>>
      extends ErrorResponse.ErrorResponseBuilder<C, B> {
      @Generated
      private Map<String, String> validationErrors;

      @Generated
      public B validationErrors(final Map<String, String> validationErrors) {
         this.validationErrors = validationErrors;
         return this.self();
      }

      @Generated
      protected abstract B self();

      @Generated
      public abstract C build();

      @Generated
      @Override
      public String toString() {
         return "ValidationErrorResponse.ValidationErrorResponseBuilder(super=" + super.toString() + ", validationErrors=" + this.validationErrors + ")";
      }
   }

   @Generated
   private static final class ValidationErrorResponseBuilderImpl
      extends ValidationErrorResponse.ValidationErrorResponseBuilder<ValidationErrorResponse, ValidationErrorResponse.ValidationErrorResponseBuilderImpl> {
      @Generated
      protected ValidationErrorResponse.ValidationErrorResponseBuilderImpl self() {
         return this;
      }

      @Generated
      @Override
      public ValidationErrorResponse build() {
         return new ValidationErrorResponse(this);
      }
   }
}
