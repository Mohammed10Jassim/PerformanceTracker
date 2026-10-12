package com.pt.PerformanceTracker.organization.employee.controller;

import com.pt.PerformanceTracker.common.response.ApiResponseBuilder;
import com.pt.PerformanceTracker.organization.employee.dto.requestDto.CreateEmployeeRequestDto;
import com.pt.PerformanceTracker.organization.employee.dto.requestDto.UpdateEmployeeRequestDto;
import com.pt.PerformanceTracker.organization.employee.dto.responseDto.EmployeeResponseDto;
import com.pt.PerformanceTracker.organization.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.Generated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/employees"})
public class EmployeeController {
   private final EmployeeService employeeService;

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PostMapping({"/create"})
   public ResponseEntity<?> createEmployee(@Valid @RequestBody CreateEmployeeRequestDto request) {
      return ResponseEntity.status(HttpStatus.CREATED)
         .body(ApiResponseBuilder.created("Employee created successfully.", this.employeeService.createEmployee(request)));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{employeeId}"})
   public ResponseEntity<?> getEmployeeById(@PathVariable Long employeeId) {
      return ResponseEntity.ok(ApiResponseBuilder.success("Employee fetched successfully.", this.employeeService.getEmployeeById(employeeId)));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<?> getEmployees(
      @RequestParam(required = false) Long employeeCode,
      @RequestParam(required = false) Long departmentId,
      @RequestParam(required = false) Long designationId,
      @RequestParam(required = false) String search,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      Pageable pageable
   ) {
      Page<EmployeeResponseDto> employees = this.employeeService.getEmployees(employeeCode, departmentId, designationId, search, activeOnly, pageable);
      return ResponseEntity.ok(ApiResponseBuilder.success("Employees fetched successfully.", employees));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PutMapping({"/update/{employeeId}"})
   public ResponseEntity<?> updateEmployee(@PathVariable Long employeeId, @Valid @RequestBody UpdateEmployeeRequestDto request) {
      return ResponseEntity.ok(ApiResponseBuilder.success("Employee updated successfully.", this.employeeService.updateEmployee(employeeId, request)));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PatchMapping({"/activate/{employeeId}"})
   public ResponseEntity<?> activateEmployee(@PathVariable Long employeeId) {
      return ResponseEntity.ok(ApiResponseBuilder.success("Employee activated successfully.", this.employeeService.activateEmployee(employeeId)));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PatchMapping({"/deactivate/{employeeId}"})
   public ResponseEntity<?> deactivateEmployee(@PathVariable Long employeeId) {
      return ResponseEntity.ok(ApiResponseBuilder.success("Employee deactivated successfully.", this.employeeService.deactivateEmployee(employeeId)));
   }

   @Generated
   public EmployeeController(final EmployeeService employeeService) {
      this.employeeService = employeeService;
   }
}
