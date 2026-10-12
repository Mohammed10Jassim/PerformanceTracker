package com.pt.PerformanceTracker.common.exception.response;

import java.time.LocalDateTime;
import lombok.Generated;

public class ErrorResponse {
   private LocalDateTime timestamp;
   private Integer status;
   private String error;
   private String errorCode;
   private String message;
   private String path;
   private String traceId;

   @Generated
   protected ErrorResponse(final ErrorResponse.ErrorResponseBuilder<?, ?> b) {
      this.timestamp = b.timestamp;
      this.status = b.status;
      this.error = b.error;
      this.errorCode = b.errorCode;
      this.message = b.message;
      this.path = b.path;
      this.traceId = b.traceId;
   }

   @Generated
   public static ErrorResponse.ErrorResponseBuilder<?, ?> builder() {
      return new ErrorResponse.ErrorResponseBuilderImpl();
   }

   @Generated
   public LocalDateTime getTimestamp() {
      return this.timestamp;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public String getError() {
      return this.error;
   }

   @Generated
   public String getErrorCode() {
      return this.errorCode;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public String getPath() {
      return this.path;
   }

   @Generated
   public String getTraceId() {
      return this.traceId;
   }

   @Generated
   public ErrorResponse(
      final LocalDateTime timestamp,
      final Integer status,
      final String error,
      final String errorCode,
      final String message,
      final String path,
      final String traceId
   ) {
      this.timestamp = timestamp;
      this.status = status;
      this.error = error;
      this.errorCode = errorCode;
      this.message = message;
      this.path = path;
      this.traceId = traceId;
   }

   @Generated
   public abstract static class ErrorResponseBuilder<C extends ErrorResponse, B extends ErrorResponse.ErrorResponseBuilder<C, B>> {
      @Generated
      private LocalDateTime timestamp;
      @Generated
      private Integer status;
      @Generated
      private String error;
      @Generated
      private String errorCode;
      @Generated
      private String message;
      @Generated
      private String path;
      @Generated
      private String traceId;

      @Generated
      public B timestamp(final LocalDateTime timestamp) {
         this.timestamp = timestamp;
         return this.self();
      }

      @Generated
      public B status(final Integer status) {
         this.status = status;
         return this.self();
      }

      @Generated
      public B error(final String error) {
         this.error = error;
         return this.self();
      }

      @Generated
      public B errorCode(final String errorCode) {
         this.errorCode = errorCode;
         return this.self();
      }

      @Generated
      public B message(final String message) {
         this.message = message;
         return this.self();
      }

      @Generated
      public B path(final String path) {
         this.path = path;
         return this.self();
      }

      @Generated
      public B traceId(final String traceId) {
         this.traceId = traceId;
         return this.self();
      }

      @Generated
      protected abstract B self();

      @Generated
      public abstract C build();

      @Generated
      @Override
      public String toString() {
         return "ErrorResponse.ErrorResponseBuilder(timestamp="
            + this.timestamp
            + ", status="
            + this.status
            + ", error="
            + this.error
            + ", errorCode="
            + this.errorCode
            + ", message="
            + this.message
            + ", path="
            + this.path
            + ", traceId="
            + this.traceId
            + ")";
      }
   }

   @Generated
   private static final class ErrorResponseBuilderImpl extends ErrorResponse.ErrorResponseBuilder<ErrorResponse, ErrorResponse.ErrorResponseBuilderImpl> {
      @Generated
      protected ErrorResponse.ErrorResponseBuilderImpl self() {
         return this;
      }

      @Generated
      @Override
      public ErrorResponse build() {
         return new ErrorResponse(this);
      }
   }
}
