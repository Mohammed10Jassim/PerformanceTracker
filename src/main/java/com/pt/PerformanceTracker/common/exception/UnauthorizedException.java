package com.pt.PerformanceTracker.common.exception;

import com.pt.PerformanceTracker.common.exception.code.ErrorCode;

public class UnauthorizedException extends BaseException {
   public UnauthorizedException(ErrorCode errorCode, String message) {
      super(errorCode, message);
   }
}
