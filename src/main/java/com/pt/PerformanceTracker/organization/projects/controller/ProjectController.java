package com.pt.PerformanceTracker.organization.projects.controller;

import com.pt.PerformanceTracker.organization.projects.dto.ProjectCreateRequest;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectResponse;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectUpdateRequest;
import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import com.pt.PerformanceTracker.organization.projects.service.ProjectService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping({"/projects"})
public class ProjectController {
   private final ProjectService projectService;

   @PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
   @PostMapping({"/create"})
   public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectCreateRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.projectService.createProject(request));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{projectId}"})
   public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long projectId) {
      return ResponseEntity.ok(this.projectService.getProjectById(projectId));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<Page<ProjectResponse>> getProjects(
      @RequestParam(required = false) ProjectStatus status,
      @RequestParam(required = false) String search,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      Pageable pageable
   ) {
      return ResponseEntity.ok(this.projectService.getProjects(status, search, activeOnly, pageable));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{projectId}/available-statuses"})
   public ResponseEntity<List<ProjectStatus>> getAvailableStatuses(@PathVariable Long projectId) {
      return ResponseEntity.ok(this.projectService.getAvailableStatuses(projectId));
   }

   @PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
   @PatchMapping({"/update/{projectId}"})
   public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectUpdateRequest request) {
      return ResponseEntity.ok(this.projectService.updateProject(projectId, request));
   }

   @PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
   @PatchMapping({"/activate/{projectId}"})
   public ResponseEntity<Void> activateProject(@PathVariable Long projectId) {
      this.projectService.activateProject(projectId);
      return ResponseEntity.noContent().build();
   }

   @PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
   @PatchMapping({"/update/status/{projectId}"})
   public ResponseEntity<ProjectResponse> updateProjectStatus(@PathVariable Long projectId, @RequestParam ProjectStatus status) {
      return ResponseEntity.ok(this.projectService.updateProjectStatus(projectId, status));
   }

   @PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
   @PatchMapping({"/deactivate/{projectId}"})
   public ResponseEntity<Void> deactivateProject(@PathVariable Long projectId) {
      this.projectService.deactivateProject(projectId);
      return ResponseEntity.noContent().build();
   }

   @Generated
   public ProjectController(final ProjectService projectService) {
      this.projectService = projectService;
   }
}
