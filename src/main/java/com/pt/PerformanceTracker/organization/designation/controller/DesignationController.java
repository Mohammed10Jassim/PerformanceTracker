package com.pt.PerformanceTracker.organization.designation.controller;

import com.pt.PerformanceTracker.common.response.ApiResponse;
import com.pt.PerformanceTracker.common.response.ApiResponseBuilder;
import com.pt.PerformanceTracker.organization.designation.dto.requestDto.CreateDesignationRequestDto;
import com.pt.PerformanceTracker.organization.designation.dto.requestDto.UpdateDesignationRequestDto;
import com.pt.PerformanceTracker.organization.designation.dto.responseDto.DesignationResponseDto;
import com.pt.PerformanceTracker.organization.designation.service.DesignationService;
import jakarta.validation.Valid;
import lombok.Generated;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/designations"})
public class DesignationController {
   private final DesignationService designationService;

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PostMapping({"/create"})
   @ResponseStatus(HttpStatus.CREATED)
   public ApiResponse<?> createDesignation(@Valid @RequestBody CreateDesignationRequestDto request) {
      return ApiResponseBuilder.created("Designation created successfully.", this.designationService.createDesignation(request));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{designationId}"})
   public ApiResponse<?> getDesignationById(@PathVariable Long designationId) {
      return ApiResponseBuilder.success("Designation fetched successfully.", this.designationService.getDesignationById(designationId));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping
   public ApiResponse<?> getAllDesignations(@RequestParam(defaultValue = "false") boolean activeOnly, Pageable pageable) {
      return ApiResponseBuilder.success("Designations fetched successfully.", this.designationService.getAllDesignations(activeOnly, pageable));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PutMapping({"/update/{designationId}"})
   public ApiResponse<?> updateDesignation(@PathVariable Long designationId, @Valid @RequestBody UpdateDesignationRequestDto request) {
      return ApiResponseBuilder.success("Designation updated successfully.", this.designationService.updateDesignation(designationId, request));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PatchMapping({"/activate/{designationId}"})
   public ApiResponse<DesignationResponseDto> activateDesignation(@PathVariable Long designationId) {
      return ApiResponseBuilder.success("Designation activated successfully.", this.designationService.activateDesignation(designationId));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
   @PatchMapping({"/deactivate/{designationId}"})
   public ApiResponse<DesignationResponseDto> deactivateDesignation(@PathVariable Long designationId) {
      return ApiResponseBuilder.success("Designation deactivated successfully.", this.designationService.deactivateDesignation(designationId));
   }

   @Generated
   public DesignationController(final DesignationService designationService) {
      this.designationService = designationService;
   }
}
