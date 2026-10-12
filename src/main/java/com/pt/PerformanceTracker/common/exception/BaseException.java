package com.pt.PerformanceTracker.common.exception;

import com.pt.PerformanceTracker.common.exception.code.ErrorCode;
import lombok.Generated;

public abstract class BaseException extends RuntimeException {
   private final ErrorCode errorCode;

   protected BaseException(ErrorCode errorCode, String message) {
      super(message);
      this.errorCode = errorCode;
   }

   @Generated
   public ErrorCode getErrorCode() {
      return this.errorCode;
   }
}
