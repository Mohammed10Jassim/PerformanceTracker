package com.pt.PerformanceTracker.organization.employee.service;

import com.pt.PerformanceTracker.organization.employee.dto.requestDto.CreateEmployeeRequestDto;
import com.pt.PerformanceTracker.organization.employee.dto.requestDto.UpdateEmployeeRequestDto;
import com.pt.PerformanceTracker.organization.employee.dto.responseDto.EmployeeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
   EmployeeResponseDto createEmployee(CreateEmployeeRequestDto request);

   EmployeeResponseDto getEmployeeById(Long employeeId);

   Page<EmployeeResponseDto> getEmployees(Long employeeCode, Long departmentId, Long designationId, String search, boolean activeOnly, Pageable pageable);

   EmployeeResponseDto updateEmployee(Long employeeId, UpdateEmployeeRequestDto request);

   EmployeeResponseDto activateEmployee(Long employeeId);

   EmployeeResponseDto deactivateEmployee(Long employeeId);
}
