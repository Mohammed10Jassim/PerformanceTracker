package com.pt.PerformanceTracker.common.exception;

import com.pt.PerformanceTracker.common.exception.code.ErrorCode;

public class ResourceAlreadyExistsException extends BaseException {
   public ResourceAlreadyExistsException(ErrorCode errorCode, String message) {
      super(errorCode, message);
   }
}
