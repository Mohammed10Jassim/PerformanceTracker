package com.pt.PerformanceTracker.organization.departmentDesignation.controller;

import com.pt.PerformanceTracker.common.response.ApiResponseBuilder;
import com.pt.PerformanceTracker.organization.departmentDesignation.dto.requestDto.CreateDepartmentDesignationRequestDto;
import com.pt.PerformanceTracker.organization.departmentDesignation.service.DepartmentDesignationService;
import jakarta.validation.Valid;
import lombok.Generated;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/department-designations"})
public class DepartmentDesignationController {
   private final DepartmentDesignationService departmentDesignationService;

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PostMapping({"/create"})
   public ResponseEntity<?> createDepartmentDesignation(@Valid @RequestBody CreateDepartmentDesignationRequestDto requestDto) {
      return ResponseEntity.status(HttpStatus.CREATED)
         .body(
            ApiResponseBuilder.created(
               "Department designation mapping created successfully.", this.departmentDesignationService.createDepartmentDesignation(requestDto)
            )
         );
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{id}"})
   public ResponseEntity<?> getDepartmentDesignationById(@PathVariable Long id) {
      return ResponseEntity.ok(
         ApiResponseBuilder.success("Department designation mapping fetched successfully.", this.departmentDesignationService.getDepartmentDesignationById(id))
      );
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<?> getDepartmentDesignations(
      @RequestParam(required = false) Long departmentId,
      @RequestParam(required = false) Long designationId,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      Pageable pageable
   ) {
      return ResponseEntity.ok(
         ApiResponseBuilder.success(
            "Department designation mapping fetched successfully.",
            this.departmentDesignationService.getDepartmentDesignations(departmentId, designationId, activeOnly, pageable)
         )
      );
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PatchMapping({"/activate/{id}"})
   public ResponseEntity<?> activateDepartmentDesignation(@PathVariable Long id) {
      return ResponseEntity.ok(
         ApiResponseBuilder.success(
            "Department designation mapping activated successfully.", this.departmentDesignationService.activateDepartmentDesignation(id)
         )
      );
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PatchMapping({"/deactivate/{id}"})
   public ResponseEntity<?> deactivateDepartmentDesignation(@PathVariable Long id) {
      return ResponseEntity.ok(
         ApiResponseBuilder.success(
            "Department designation mapping deactivated successfully.", this.departmentDesignationService.deactivateDepartmentDesignation(id)
         )
      );
   }

   @Generated
   public DepartmentDesignationController(final DepartmentDesignationService departmentDesignationService) {
      this.departmentDesignationService = departmentDesignationService;
   }
}
