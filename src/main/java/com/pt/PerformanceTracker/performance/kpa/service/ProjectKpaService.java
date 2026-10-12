package com.pt.PerformanceTracker.performance.kpa.service;

import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaCreateRequest;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaResponse;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectKpaService {
   ProjectKpaResponse createProjectKpa(ProjectKpaCreateRequest request);

   ProjectKpaResponse getProjectKpaById(Long projectKpaId);

   Page<ProjectKpaResponse> getProjectKpas(Long projectKraId, Long projectId, Long departmentId, String search, boolean activeOnly, Pageable pageable);

   ProjectKpaResponse updateProjectKpa(Long projectKpaId, ProjectKpaUpdateRequest request);

   void activateProjectKpa(Long projectKpaId);

   void deactivateProjectKpa(Long projectKpaId);
}
