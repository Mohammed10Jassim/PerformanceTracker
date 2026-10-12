package com.pt.PerformanceTracker.organization.projects.service;

import com.pt.PerformanceTracker.organization.projects.dto.ProjectCreateRequest;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectResponse;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectUpdateRequest;
import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
   ProjectResponse createProject(ProjectCreateRequest request);

   ProjectResponse getProjectById(Long projectId);

   Page<ProjectResponse> getProjects(ProjectStatus status, String search, boolean activeOnly, Pageable pageable);

   List<ProjectStatus> getAvailableStatuses(Long projectId);

   ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request);

   ProjectResponse updateProjectStatus(Long projectId, ProjectStatus status);

   void activateProject(Long projectId);

   void deactivateProject(Long projectId);
}
