package com.pt.PerformanceTracker.performance.kpaEmployee.service;

import com.pt.PerformanceTracker.performance.kpaEmployee.dto.ProjectKpaEmployeeCreateRequest;
import com.pt.PerformanceTracker.performance.kpaEmployee.dto.ProjectKpaEmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectKpaEmployeeService {
   ProjectKpaEmployeeResponse assignEmployee(ProjectKpaEmployeeCreateRequest request);

   ProjectKpaEmployeeResponse getById(Long id);

   Page<ProjectKpaEmployeeResponse> getAssignments(Long projectKpaId, Long employeeId, Long projectId, Long departmentId, boolean activeOnly, Pageable pageable);

   void activate(Long id);

   void deactivate(Long id);
}
