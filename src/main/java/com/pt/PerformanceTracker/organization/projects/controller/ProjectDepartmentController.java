package com.pt.PerformanceTracker.organization.projects.controller;

import com.pt.PerformanceTracker.organization.projects.dto.ProjectDepartmentCreateRequest;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectDepartmentResponse;
import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import com.pt.PerformanceTracker.organization.projects.service.ProjectDepartmentService;
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
@RequestMapping({"/project-departments"})
public class ProjectDepartmentController {
   private final ProjectDepartmentService projectDepartmentService;

   @PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
   @PostMapping({"/assign"})
   public ResponseEntity<ProjectDepartmentResponse> assignDepartmentToProject(@Valid @RequestBody ProjectDepartmentCreateRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.projectDepartmentService.assignDepartmentToProject(request));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{id}"})
   public ResponseEntity<ProjectDepartmentResponse> getById(@PathVariable Long id) {
      return ResponseEntity.ok(this.projectDepartmentService.getById(id));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<Page<ProjectDepartmentResponse>> getProjectDepartments(
      @RequestParam(required = false) Long projectId,
      @RequestParam(required = false) Long departmentId,
      @RequestParam(required = false) ProjectStatus projectStatus,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      Pageable pageable
   ) {
      return ResponseEntity.ok(this.projectDepartmentService.getProjectDepartments(projectId, departmentId, projectStatus, activeOnly, pageable));
   }

   @PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
   @PatchMapping({"/activate/{id}"})
   public ResponseEntity<Void> activate(@PathVariable Long id) {
      this.projectDepartmentService.activate(id);
      return ResponseEntity.noContent().build();
   }

   @PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
   @PatchMapping({"/deactivate/{id}"})
   public ResponseEntity<Void> deactivate(@PathVariable Long id) {
      this.projectDepartmentService.deactivate(id);
      return ResponseEntity.noContent().build();
   }

   @Generated
   public ProjectDepartmentController(final ProjectDepartmentService projectDepartmentService) {
      this.projectDepartmentService = projectDepartmentService;
   }
}
