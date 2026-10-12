package com.pt.PerformanceTracker.common.exception;

import com.pt.PerformanceTracker.common.exception.code.ErrorCode;

public class InvalidRequestException extends BaseException {
   public InvalidRequestException(ErrorCode errorCode, String message) {
      super(errorCode, message);
   }
}
