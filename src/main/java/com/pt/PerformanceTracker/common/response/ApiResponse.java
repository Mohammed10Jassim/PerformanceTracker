package com.pt.PerformanceTracker.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

   @Builder.Default
   private boolean success = false;

   private Integer status;

   private String message;

   private T data;

   @Builder.Default
   private LocalDateTime timestamp = LocalDateTime.now();
}