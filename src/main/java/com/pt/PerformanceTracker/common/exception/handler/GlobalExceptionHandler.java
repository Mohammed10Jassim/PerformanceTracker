package com.pt.PerformanceTracker.common.exception.handler;

import com.pt.PerformanceTracker.common.exception.BaseException;
import com.pt.PerformanceTracker.common.exception.code.ErrorCode;
import com.pt.PerformanceTracker.common.exception.response.ErrorResponse;
import com.pt.PerformanceTracker.common.exception.response.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

   @ExceptionHandler({BaseException.class})
   public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
      HttpStatus status = this.resolveHttpStatus(ex);
      log.warn(
         "Business Exception Occurred\nURL        : {}\nMethod     : {}\nError Code : {}\nMessage    : {}\n",
         new Object[]{request.getRequestURI(), request.getMethod(), ex.getErrorCode().getCode(), ex.getMessage()}
      );
      return this.buildErrorResponse(ex, status, request);
   }

   @ExceptionHandler({MethodArgumentNotValidException.class})
   public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
      Map<String, String> validationErrors = new LinkedHashMap<>();

      for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
         validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
      }

      ValidationErrorResponse response = ValidationErrorResponse.builder()
         .timestamp(LocalDateTime.now())
         .status(Integer.valueOf(HttpStatus.BAD_REQUEST.value()))
         .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
         .errorCode(ErrorCode.VALIDATION_FAILED.getCode())
         .message(ErrorCode.VALIDATION_FAILED.getDefaultMessage())
         .path(request.getRequestURI())
         .traceId(this.generateTraceId())
         .validationErrors(validationErrors)
         .build();
      return ResponseEntity.badRequest().body(response);
   }

   @ExceptionHandler({HttpMessageNotReadableException.class})
   public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
      return this.buildGenericResponse(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, "Malformed JSON request.", request);
   }

   @ExceptionHandler({MissingServletRequestParameterException.class})
   public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
      return this.buildGenericResponse(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, ex.getParameterName() + " parameter is missing.", request);
   }

   @ExceptionHandler({MethodArgumentTypeMismatchException.class})
   public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
      return this.buildGenericResponse(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, "Invalid value for parameter : " + ex.getName(), request);
   }

   @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
   public ResponseEntity<ErrorResponse> handleBusinessRuleViolation(RuntimeException ex, HttpServletRequest request) {
      String message = ex.getMessage() == null ? ErrorCode.BAD_REQUEST.getDefaultMessage() : ex.getMessage();
      String normalized = message.toLowerCase(Locale.ROOT);
      HttpStatus status;
      if (normalized.contains("not found")) {
         status = HttpStatus.NOT_FOUND;
      } else if (!normalized.contains("already exists") && !normalized.contains("already assigned")) {
         status = HttpStatus.BAD_REQUEST;
      } else {
         status = HttpStatus.CONFLICT;
      }

      log.warn(
         "Business Rule Violation\nURL    : {}\nMethod : {}\nStatus : {}\nDetail : {}\n",
         new Object[]{request.getRequestURI(), request.getMethod(), status.value(), message}
      );
      return this.buildGenericResponse(status, ErrorCode.BAD_REQUEST, message, request);
   }

   @ExceptionHandler({DataIntegrityViolationException.class})
   public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
      log.warn("Data integrity violation on {} {}: {}", new Object[]{request.getMethod(), request.getRequestURI(), ex.getMostSpecificCause().getMessage()});
      return this.buildGenericResponse(
         HttpStatus.CONFLICT, ErrorCode.BAD_REQUEST, "The request conflicts with existing data or is missing a required value.", request
      );
   }

   @ExceptionHandler({NoResourceFoundException.class})
   public ResponseEntity<ErrorResponse> handleNoResource(NoResourceFoundException ex, HttpServletRequest request) {
      return this.buildGenericResponse(HttpStatus.NOT_FOUND, ErrorCode.BAD_REQUEST, "Resource not found.", request);
   }

   @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
   public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
      return this.buildGenericResponse(
         HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.BAD_REQUEST, "HTTP method " + ex.getMethod() + " is not supported for this endpoint.", request
      );
   }

   @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
   public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
      return this.buildGenericResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.BAD_REQUEST, "Unsupported content type.", request);
   }

   @ExceptionHandler({AccessDeniedException.class})
   public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
      log.warn("Authorization Denied\nURL    : {}\nMethod : {}\nDetail : {}\n", new Object[]{request.getRequestURI(), request.getMethod(), ex.getMessage()});
      return this.buildGenericResponse(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, ex.getMessage(), request);
   }

   @ExceptionHandler({UsernameNotFoundException.class})
   public ResponseEntity<ErrorResponse> handleUserNotProvisioned(UsernameNotFoundException ex, HttpServletRequest request) {
      log.warn("Authenticated account is not linked to a PMS user on {} {}: {}", new Object[]{request.getMethod(), request.getRequestURI(), ex.getMessage()});
      return this.buildGenericResponse(
         HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, "Your account is not linked to an employee record in the Performance Tracker. Please contact HR.", request
      );
   }

   @ExceptionHandler({AuthenticationException.class})
   public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
      log.warn("Authentication Failed\nURL    : {}\nMethod : {}\nDetail : {}\n", new Object[]{request.getRequestURI(), request.getMethod(), ex.getMessage()});
      return this.buildGenericResponse(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED, ex.getMessage(), request);
   }

   @ExceptionHandler({Exception.class})
   public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
      String traceId = this.generateTraceId();
      log.error("Unexpected Exception\nTrace ID : {}\nURL      : {}\nMethod   : {}\n", new Object[]{traceId, request.getRequestURI(), request.getMethod(), ex});
      ErrorResponse response = ErrorResponse.builder()
         .timestamp(LocalDateTime.now())
         .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
         .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
         .errorCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
         .message("An unexpected error occurred. Please contact support with trace ID " + traceId + ".")
         .path(request.getRequestURI())
         .traceId(traceId)
         .build();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
   }

   private ResponseEntity<ErrorResponse> buildErrorResponse(BaseException ex, HttpStatus status, HttpServletRequest request) {
      ErrorResponse response = ErrorResponse.builder()
         .timestamp(LocalDateTime.now())
         .status(status.value())
         .error(status.getReasonPhrase())
         .errorCode(ex.getErrorCode().getCode())
         .message(ex.getMessage())
         .path(request.getRequestURI())
         .traceId(this.generateTraceId())
         .build();
      return ResponseEntity.status(status).body(response);
   }

   private ResponseEntity<ErrorResponse> buildGenericResponse(HttpStatus status, ErrorCode errorCode, String message, HttpServletRequest request) {
      ErrorResponse response = ErrorResponse.builder()
         .timestamp(LocalDateTime.now())
         .status(status.value())
         .error(status.getReasonPhrase())
         .errorCode(errorCode.getCode())
         .message(message)
         .path(request.getRequestURI())
         .traceId(this.generateTraceId())
         .build();
      return ResponseEntity.status(status).body(response);
   }

   private HttpStatus resolveHttpStatus(BaseException ex) {
      return switch (ex.getErrorCode()) {
         case DEPARTMENT_NOT_FOUND, DESIGNATION_NOT_FOUND, DEPARTMENT_DESIGNATION_NOT_FOUND, EMPLOYEE_NOT_FOUND, USER_NOT_FOUND, ROLE_NOT_FOUND, KEYCLOAK_USER_NOT_FOUND, KRA_NOT_FOUND, KPA_NOT_FOUND, KPI_NOT_FOUND, PERFORMANCE_TEMPLATE_NOT_FOUND -> HttpStatus.NOT_FOUND;
         case DEPARTMENT_ALREADY_EXISTS, DESIGNATION_ALREADY_EXISTS, DEPARTMENT_DESIGNATION_ALREADY_EXISTS, EMPLOYEE_ALREADY_EXISTS, EMPLOYEE_CODE_ALREADY_EXISTS, EMPLOYEE_EMAIL_ALREADY_EXISTS, USER_ALREADY_EXISTS, ROLE_ALREADY_EXISTS, KRA_ALREADY_EXISTS, KPA_ALREADY_EXISTS, KPI_ALREADY_EXISTS, PERFORMANCE_TEMPLATE_ALREADY_EXISTS -> HttpStatus.CONFLICT;
         case DEPARTMENT_ALREADY_ACTIVE, DEPARTMENT_ALREADY_INACTIVE, DESIGNATION_ALREADY_ACTIVE, DESIGNATION_ALREADY_INACTIVE, DEPARTMENT_DESIGNATION_ALREADY_ACTIVE, DEPARTMENT_DESIGNATION_ALREADY_INACTIVE, EMPLOYEE_ALREADY_ACTIVE, EMPLOYEE_ALREADY_INACTIVE, VALIDATION_FAILED, BAD_REQUEST -> HttpStatus.BAD_REQUEST;
         case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
         case FORBIDDEN -> HttpStatus.FORBIDDEN;
         default -> HttpStatus.INTERNAL_SERVER_ERROR;
      };
   }

   private String generateTraceId() {
      return UUID.randomUUID().toString();
   }
}
