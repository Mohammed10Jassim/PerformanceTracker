package com.pt.PerformanceTracker.performance.kpaTask.service;

import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskCreateRequest;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskResponse;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskUpdateRequest;
import com.pt.PerformanceTracker.performance.kpaTask.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectKpaTaskService {
   ProjectKpaTaskResponse createTask(ProjectKpaTaskCreateRequest request);

   ProjectKpaTaskResponse getTaskById(Long taskId);

   Page<ProjectKpaTaskResponse> getTasks(
      Long assignmentId, Long employeeId, Long projectId, Long departmentId, TaskStatus status, String search, boolean activeOnly, Pageable pageable
   );

   ProjectKpaTaskResponse updateTask(Long taskId, ProjectKpaTaskUpdateRequest request);

   void activateTask(Long taskId);

   void deactivateTask(Long taskId);
}
