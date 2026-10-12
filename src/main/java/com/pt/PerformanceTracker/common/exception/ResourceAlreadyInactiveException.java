package com.pt.PerformanceTracker.common.exception;

import com.pt.PerformanceTracker.common.exception.code.ErrorCode;

public class ResourceAlreadyInactiveException extends BaseException {
   public ResourceAlreadyInactiveException(ErrorCode errorCode, String resource) {
      super(errorCode, resource + " is already inactive.");
   }
}
