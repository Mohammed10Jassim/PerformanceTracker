package com.pt.PerformanceTracker.common.exception;

import com.pt.PerformanceTracker.common.exception.code.ErrorCode;

public class ResourceNotFoundException extends BaseException {
   public ResourceNotFoundException(ErrorCode errorCode, String resource, String field, Object value) {
      super(errorCode, String.format("%s not found with %s : %s", resource, field, value));
   }
}
