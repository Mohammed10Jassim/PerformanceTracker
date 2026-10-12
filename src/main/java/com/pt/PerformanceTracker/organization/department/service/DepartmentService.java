package com.pt.PerformanceTracker.organization.department.service;

import com.pt.PerformanceTracker.organization.department.dto.requestDto.CreateDepartmentRequestDto;
import com.pt.PerformanceTracker.organization.department.dto.requestDto.UpdateDepartmentRequestDto;
import com.pt.PerformanceTracker.organization.department.dto.responseDto.DepartmentResponseDto;
import java.util.List;

public interface DepartmentService {
   DepartmentResponseDto createDepartment(CreateDepartmentRequestDto request);

   DepartmentResponseDto updateDepartment(Long departmentId, UpdateDepartmentRequestDto request);

   DepartmentResponseDto getDepartmentById(Long departmentId);

   List<DepartmentResponseDto> getAllDepartments(boolean activeOnly);

   DepartmentResponseDto activateDepartment(Long departmentId);

   DepartmentResponseDto deactivateDepartment(Long departmentId);
}
