package com.pt.PerformanceTracker.common.exception;

import com.pt.PerformanceTracker.common.exception.code.ErrorCode;

public class ForbiddenException extends BaseException {
   public ForbiddenException(ErrorCode errorCode, String message) {
      super(errorCode, message);
   }
}
