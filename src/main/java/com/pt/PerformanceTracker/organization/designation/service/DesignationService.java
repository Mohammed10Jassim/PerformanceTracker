package com.pt.PerformanceTracker.organization.designation.service;

import com.pt.PerformanceTracker.organization.designation.dto.requestDto.CreateDesignationRequestDto;
import com.pt.PerformanceTracker.organization.designation.dto.requestDto.UpdateDesignationRequestDto;
import com.pt.PerformanceTracker.organization.designation.dto.responseDto.DesignationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DesignationService {
   DesignationResponseDto createDesignation(CreateDesignationRequestDto request);

   DesignationResponseDto updateDesignation(Long designationId, UpdateDesignationRequestDto request);

   DesignationResponseDto getDesignationById(Long designationId);

   Page<DesignationResponseDto> getAllDesignations(boolean activeOnly, Pageable pageable);

   DesignationResponseDto activateDesignation(Long designationId);

   DesignationResponseDto deactivateDesignation(Long designationId);
}
