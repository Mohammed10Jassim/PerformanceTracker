package com.pt.PerformanceTracker.performance.kpaEmployee.controller;

import com.pt.PerformanceTracker.performance.kpaEmployee.dto.ProjectKpaEmployeeCreateRequest;
import com.pt.PerformanceTracker.performance.kpaEmployee.dto.ProjectKpaEmployeeResponse;
import com.pt.PerformanceTracker.performance.kpaEmployee.service.ProjectKpaEmployeeService;
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
@RequestMapping({"/project-kpa-employees"})
public class ProjectKpaEmployeeController {
   private final ProjectKpaEmployeeService projectKpaEmployeeService;

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PostMapping({"/assign"})
   public ResponseEntity<ProjectKpaEmployeeResponse> assignEmployee(@Valid @RequestBody ProjectKpaEmployeeCreateRequest request) {
      ProjectKpaEmployeeResponse response = this.projectKpaEmployeeService.assignEmployee(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping({"/{id}"})
   public ResponseEntity<ProjectKpaEmployeeResponse> getById(@PathVariable Long id) {
      return ResponseEntity.ok(this.projectKpaEmployeeService.getById(id));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<Page<ProjectKpaEmployeeResponse>> getAssignments(
      @RequestParam(required = false) Long projectKpaId,
      @RequestParam(required = false) Long employeeId,
      @RequestParam(required = false) Long projectId,
      @RequestParam(required = false) Long departmentId,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      Pageable pageable
   ) {
      return ResponseEntity.ok(this.projectKpaEmployeeService.getAssignments(projectKpaId, employeeId, projectId, departmentId, activeOnly, pageable));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PatchMapping({"/activate/{id}"})
   public ResponseEntity<Void> activate(@PathVariable Long id) {
      this.projectKpaEmployeeService.activate(id);
      return ResponseEntity.noContent().build();
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN', 'MANAGER')")
   @PatchMapping({"/deactivate/{id}"})
   public ResponseEntity<Void> deactivate(@PathVariable Long id) {
      this.projectKpaEmployeeService.deactivate(id);
      return ResponseEntity.noContent().build();
   }

   @Generated
   public ProjectKpaEmployeeController(final ProjectKpaEmployeeService projectKpaEmployeeService) {
      this.projectKpaEmployeeService = projectKpaEmployeeService;
   }
}
