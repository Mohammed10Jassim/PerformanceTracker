package com.pt.PerformanceTracker.performance.kra.controller;

import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraCreateRequest;
import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraResponse;
import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraUpdateRequest;
import com.pt.PerformanceTracker.performance.kra.service.ProjectKraService;
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
@RequestMapping({"/project-kras"})
public class ProjectKraController {
   private final ProjectKraService projectKraService;

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PostMapping({"/create"})
   public ResponseEntity<ProjectKraResponse> createKra(@Valid @RequestBody ProjectKraCreateRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.projectKraService.createKra(request));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{kraId}"})
   public ResponseEntity<ProjectKraResponse> getKraById(@PathVariable Long kraId) {
      return ResponseEntity.ok(this.projectKraService.getKraById(kraId));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<Page<ProjectKraResponse>> getProjectKras(
      @RequestParam(required = false) Long projectDepartmentId,
      @RequestParam(required = false) Long projectId,
      @RequestParam(required = false) Long departmentId,
      @RequestParam(required = false) String search,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      Pageable pageable
   ) {
      return ResponseEntity.ok(this.projectKraService.getProjectKras(projectDepartmentId, projectId, departmentId, search, activeOnly, pageable));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PatchMapping({"/update/{kraId}"})
   public ResponseEntity<ProjectKraResponse> updateKra(@PathVariable Long kraId, @Valid @RequestBody ProjectKraUpdateRequest request) {
      return ResponseEntity.ok(this.projectKraService.updateKra(kraId, request));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PatchMapping({"/activate/{kraId}"})
   public ResponseEntity<Void> activateKra(@PathVariable Long kraId) {
      this.projectKraService.activateKra(kraId);
      return ResponseEntity.noContent().build();
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PatchMapping({"/deactivate/{kraId}"})
   public ResponseEntity<Void> deactivateKra(@PathVariable Long kraId) {
      this.projectKraService.deactivateKra(kraId);
      return ResponseEntity.noContent().build();
   }

   @Generated
   public ProjectKraController(final ProjectKraService projectKraService) {
      this.projectKraService = projectKraService;
   }
}
