package com.pt.PerformanceTracker.common.response;

import org.springframework.http.HttpStatus;

public final class ApiResponseBuilder {

   private ApiResponseBuilder() {
   }

   public static <T> ApiResponse<T> success(String message, T data) {
      return build(HttpStatus.OK, message, data);
   }

   public static <T> ApiResponse<T> created(String message, T data) {
      return build(HttpStatus.CREATED, message, data);
   }

   public static <T> ApiResponse<T> build(
           HttpStatus status,
           String message,
           T data
   ) {
      return ApiResponse.<T>builder()
              .success(status.is2xxSuccessful())
              .status(status.value())
              .message(message)
              .data(data)
              .build();
   }

   public static <T> ApiResponse<T> noContent(String message) {
      return ApiResponse.<T>builder()
              .success(true)
              .status(HttpStatus.NO_CONTENT.value())
              .message(message)
              .build();
   }
}