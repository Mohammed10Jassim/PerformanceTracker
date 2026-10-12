package com.pt.PerformanceTracker.common.exception;

import com.pt.PerformanceTracker.common.exception.code.ErrorCode;

public class ResourceAlreadyActiveException extends BaseException {
   public ResourceAlreadyActiveException(ErrorCode errorCode, String resource) {
      super(errorCode, resource + " is already active.");
   }
}
