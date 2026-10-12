package com.pt.PerformanceTracker.performance.kpaTask.controller;

import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskCreateRequest;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskResponse;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskUpdateRequest;
import com.pt.PerformanceTracker.performance.kpaTask.entity.TaskStatus;
import com.pt.PerformanceTracker.performance.kpaTask.service.ProjectKpaTaskService;
import jakarta.validation.Valid;
import lombok.Generated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/project-kpa-tasks"})
public class ProjectKpaTaskController {
   private final ProjectKpaTaskService projectKpaTaskService;

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PostMapping({"/create"})
   public ResponseEntity<ProjectKpaTaskResponse> createTask(@Valid @RequestBody ProjectKpaTaskCreateRequest request) {
      ProjectKpaTaskResponse response = this.projectKpaTaskService.createTask(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{taskId}"})
   public ResponseEntity<ProjectKpaTaskResponse> getTaskById(@PathVariable Long taskId) {
      return ResponseEntity.ok(this.projectKpaTaskService.getTaskById(taskId));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<Page<ProjectKpaTaskResponse>> getTasks(
      @RequestParam(required = false) Long assignmentId,
      @RequestParam(required = false) Long employeeId,
      @RequestParam(required = false) Long projectId,
      @RequestParam(required = false) Long departmentId,
      @RequestParam(required = false) TaskStatus status,
      @RequestParam(required = false) String search,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      Pageable pageable
   ) {
      return ResponseEntity.ok(this.projectKpaTaskService.getTasks(assignmentId, employeeId, projectId, departmentId, status, search, activeOnly, pageable));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @PatchMapping({"/update/{taskId}"})
   public ResponseEntity<ProjectKpaTaskResponse> updateTask(@PathVariable Long taskId, @Valid @RequestBody ProjectKpaTaskUpdateRequest request) {
      return ResponseEntity.ok(this.projectKpaTaskService.updateTask(taskId, request));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PatchMapping({"/activate/{taskId}"})
   public ResponseEntity<Void> activateTask(@PathVariable Long taskId) {
      this.projectKpaTaskService.activateTask(taskId);
      return ResponseEntity.noContent().build();
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PatchMapping({"/deactivate/{taskId}"})
   public ResponseEntity<Void> deactivateTask(@PathVariable Long taskId) {
      this.projectKpaTaskService.deactivateTask(taskId);
      return ResponseEntity.noContent().build();
   }

   @Generated
   public ProjectKpaTaskController(final ProjectKpaTaskService projectKpaTaskService) {
      this.projectKpaTaskService = projectKpaTaskService;
   }
}
