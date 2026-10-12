package com.pt.PerformanceTracker.organization.department.controller;

import com.pt.PerformanceTracker.common.response.ApiResponseBuilder;
import com.pt.PerformanceTracker.organization.department.dto.requestDto.CreateDepartmentRequestDto;
import com.pt.PerformanceTracker.organization.department.dto.requestDto.UpdateDepartmentRequestDto;
import com.pt.PerformanceTracker.organization.department.dto.responseDto.DepartmentResponseDto;
import com.pt.PerformanceTracker.organization.department.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.Generated;
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
@RequestMapping({"/departments"})
public class DepartmentController {
   private final DepartmentService departmentService;

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PostMapping({"/create"})
   public ResponseEntity<?> createDepartment(@Valid @RequestBody CreateDepartmentRequestDto request) {
      DepartmentResponseDto response = this.departmentService.createDepartment(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseBuilder.created("Department created successfully.", response));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PutMapping({"/update/{departmentId}"})
   public ResponseEntity<?> updateDepartment(@PathVariable Long departmentId, @Valid @RequestBody UpdateDepartmentRequestDto request) {
      return ResponseEntity.ok(ApiResponseBuilder.success("Department updated successfully.", this.departmentService.updateDepartment(departmentId, request)));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{departmentId}"})
   public ResponseEntity<?> getDepartmentById(@PathVariable Long departmentId) {
      return ResponseEntity.ok(ApiResponseBuilder.success("Department fetched successfully.", this.departmentService.getDepartmentById(departmentId)));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<?> getAllDepartments(@RequestParam(defaultValue = "false") boolean activeOnly) {
      return ResponseEntity.ok(ApiResponseBuilder.success("Departments fetched successfully.", this.departmentService.getAllDepartments(activeOnly)));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PatchMapping({"/activate/{departmentId}"})
   public ResponseEntity<?> activateDepartment(@PathVariable Long departmentId) {
      return ResponseEntity.ok(ApiResponseBuilder.success("Department activated successfully.", this.departmentService.activateDepartment(departmentId)));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PatchMapping({"/deactivate/{departmentId}"})
   public ResponseEntity<?> deactivateDepartment(@PathVariable Long departmentId) {
      return ResponseEntity.ok(ApiResponseBuilder.success("Department deactivated successfully.", this.departmentService.deactivateDepartment(departmentId)));
   }

   @Generated
   public DepartmentController(final DepartmentService departmentService) {
      this.departmentService = departmentService;
   }
}
