package com.pt.PerformanceTracker.performance.kpa.controller;

import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaCreateRequest;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaResponse;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaUpdateRequest;
import com.pt.PerformanceTracker.performance.kpa.service.ProjectKpaService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/project-kpas"})
public class ProjectKpaController {
   private final ProjectKpaService projectKpaService;

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PostMapping({"/create"})
   @ResponseStatus(HttpStatus.CREATED)
   public ProjectKpaResponse createProjectKpa(@Valid @RequestBody ProjectKpaCreateRequest request) {
      return this.projectKpaService.createProjectKpa(request);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{projectKpaId}"})
   public ProjectKpaResponse getProjectKpaById(@PathVariable Long projectKpaId) {
      return this.projectKpaService.getProjectKpaById(projectKpaId);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<Page<ProjectKpaResponse>> getProjectKpas(
      @RequestParam(required = false) Long projectKraId,
      @RequestParam(required = false) Long projectId,
      @RequestParam(required = false) Long departmentId,
      @RequestParam(required = false) String search,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      Pageable pageable
   ) {
      return ResponseEntity.ok(this.projectKpaService.getProjectKpas(projectKraId, projectId, departmentId, search, activeOnly, pageable));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PatchMapping({"/update/{projectKpaId}"})
   public ProjectKpaResponse updateProjectKpa(@PathVariable Long projectKpaId, @Valid @RequestBody ProjectKpaUpdateRequest request) {
      return this.projectKpaService.updateProjectKpa(projectKpaId, request);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PatchMapping({"/activate/{projectKpaId}"})
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void activateProjectKpa(@PathVariable Long projectKpaId) {
      this.projectKpaService.activateProjectKpa(projectKpaId);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PatchMapping({"/deactivate/{projectKpaId}"})
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deactivateProjectKpa(@PathVariable Long projectKpaId) {
      this.projectKpaService.deactivateProjectKpa(projectKpaId);
   }

   @Generated
   public ProjectKpaController(final ProjectKpaService projectKpaService) {
      this.projectKpaService = projectKpaService;
   }
}
