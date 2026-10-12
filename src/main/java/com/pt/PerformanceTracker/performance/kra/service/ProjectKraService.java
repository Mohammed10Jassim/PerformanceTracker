package com.pt.PerformanceTracker.performance.kra.service;

import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraCreateRequest;
import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraResponse;
import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectKraService {
   ProjectKraResponse createKra(ProjectKraCreateRequest request);

   ProjectKraResponse getKraById(Long kraId);

   Page<ProjectKraResponse> getProjectKras(Long projectDepartmentId, Long projectId, Long departmentId, String search, boolean activeOnly, Pageable pageable);

   ProjectKraResponse updateKra(Long kraId, ProjectKraUpdateRequest request);

   void activateKra(Long kraId);

   void deactivateKra(Long kraId);
}
