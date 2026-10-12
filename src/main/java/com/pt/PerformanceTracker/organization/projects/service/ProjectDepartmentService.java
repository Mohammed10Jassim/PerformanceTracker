package com.pt.PerformanceTracker.organization.projects.service;

import com.pt.PerformanceTracker.organization.projects.dto.ProjectDepartmentCreateRequest;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectDepartmentResponse;
import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectDepartmentService {
   ProjectDepartmentResponse assignDepartmentToProject(ProjectDepartmentCreateRequest request);

   ProjectDepartmentResponse getById(Long id);

   Page<ProjectDepartmentResponse> getProjectDepartments(Long projectId, Long departmentId, ProjectStatus projectStatus, boolean activeOnly, Pageable pageable);

   void activate(Long id);

   void deactivate(Long id);
}
