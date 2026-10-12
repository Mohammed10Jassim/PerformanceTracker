package com.pt.PerformanceTracker.organization.departmentDesignation.service;

import com.pt.PerformanceTracker.organization.departmentDesignation.dto.requestDto.CreateDepartmentDesignationRequestDto;
import com.pt.PerformanceTracker.organization.departmentDesignation.dto.responseDto.DepartmentDesignationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentDesignationService {
   DepartmentDesignationResponseDto createDepartmentDesignation(CreateDepartmentDesignationRequestDto request);

   DepartmentDesignationResponseDto getDepartmentDesignationById(Long id);

   Page<DepartmentDesignationResponseDto> getDepartmentDesignations(Long departmentId, Long designationId, boolean activeOnly, Pageable pageable);

   DepartmentDesignationResponseDto activateDepartmentDesignation(Long id);

   DepartmentDesignationResponseDto deactivateDepartmentDesignation(Long id);
}
