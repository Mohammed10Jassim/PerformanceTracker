package com.pt.PerformanceTracker.common.exception;

import com.pt.PerformanceTracker.common.exception.code.ErrorCode;

public class DuplicateResourceException extends BaseException {
   public DuplicateResourceException(ErrorCode errorCode, String resource, String field, Object value) {
      super(errorCode, String.format("%s already exists with %s : %s", resource, field, value));
   }
}
