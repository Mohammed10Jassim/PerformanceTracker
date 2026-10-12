package com.pt.PerformanceTracker.organization.projects.serviceImpl;

import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import com.pt.PerformanceTracker.organization.department.repository.DepartmentRepository;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectDepartmentCreateRequest;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectDepartmentResponse;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectDepartmentEntity;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectEntity;
import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import com.pt.PerformanceTracker.organization.projects.mapper.ProjectDepartmentMapper;
import com.pt.PerformanceTracker.organization.projects.repository.ProjectDepartmentRepository;
import com.pt.PerformanceTracker.organization.projects.repository.ProjectRepository;
import com.pt.PerformanceTracker.organization.projects.service.ProjectDepartmentService;
import com.pt.PerformanceTracker.organization.projects.specification.ProjectDepartmentSpecification;
import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.security.access.AccessScopeService;
import com.pt.PerformanceTracker.security.service.CustomUserService;
import java.util.HashSet;
import java.util.Set;
import lombok.Generated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectDepartmentServiceImpl implements ProjectDepartmentService {
   private final ProjectDepartmentRepository projectDepartmentRepository;
   private final ProjectRepository projectRepository;
   private final DepartmentRepository departmentRepository;
   private final ProjectDepartmentMapper projectDepartmentMapper;
   private final AccessScopeService accessScopeService;
   private final CustomUserService customUserService;

   @Override
   public ProjectDepartmentResponse assignDepartmentToProject(ProjectDepartmentCreateRequest request) {
      ProjectEntity project = (ProjectEntity)this.projectRepository
         .findById(request.projectId())
         .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + request.projectId()));
      if (!Boolean.TRUE.equals(project.getActive())) {
         throw new IllegalArgumentException("Cannot assign a department to an inactive project");
      } else {
         DepartmentEntity department = this.departmentRepository
            .findById(request.departmentId())
            .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + request.departmentId()));
         if (!Boolean.TRUE.equals(department.getActive())) {
            throw new IllegalArgumentException("Cannot assign an inactive department to a project");
         } else {
            this.accessScopeService.validateDepartmentAccess(request.departmentId());
            boolean exists = this.projectDepartmentRepository.existsByProjectIdAndDepartmentId(request.projectId(), request.departmentId());
            if (exists) {
               throw new IllegalArgumentException("Department is already assigned to this project");
            } else {
               ProjectDepartmentEntity mapping = ProjectDepartmentEntity.builder().project(project).department(department).build();
               ProjectDepartmentEntity saved = (ProjectDepartmentEntity)this.projectDepartmentRepository.save(mapping);
               return this.projectDepartmentMapper.toResponse(saved);
            }
         }
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public ProjectDepartmentResponse getById(Long id) {
      ProjectDepartmentEntity mapping = (ProjectDepartmentEntity)this.projectDepartmentRepository
         .findById(id)
         .orElseThrow(() -> new IllegalArgumentException("Project department mapping not found with id: " + id));
      this.validateReadAccess(mapping);
      return this.projectDepartmentMapper.toResponse(mapping);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public Page<ProjectDepartmentResponse> getProjectDepartments(
      Long projectId, Long departmentId, ProjectStatus projectStatus, boolean activeOnly, Pageable pageable
   ) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      Specification<ProjectDepartmentEntity> specification = Specification.where(ProjectDepartmentSpecification.hasProjectId(projectId))
         .and(ProjectDepartmentSpecification.hasDepartmentId(departmentId))
         .and(ProjectDepartmentSpecification.hasProjectStatus(projectStatus))
         .and(ProjectDepartmentSpecification.isActive(activeOnly));
      if (this.accessScopeService.isAdmin(currentUser)) {
         return this.projectDepartmentRepository.findAll(specification, pageable).map(this.projectDepartmentMapper::toResponse);
      } else if (this.accessScopeService.isHrAdmin(currentUser)) {
         return this.projectDepartmentRepository.findAll(specification, pageable).map(this.projectDepartmentMapper::toResponse);
      } else if (this.accessScopeService.hasManagementRole(currentUser)) {
         if (departmentId != null) {
            this.accessScopeService.validateDepartmentAccess(departmentId);
         } else {
            Set<Long> authorizedDepartmentIds = this.getAuthorizedDepartmentIds(currentUser);
            if (authorizedDepartmentIds.isEmpty()) {
               return Page.empty(pageable);
            }

            specification = specification.and(ProjectDepartmentSpecification.hasDepartmentIds(authorizedDepartmentIds));
         }

         return this.projectDepartmentRepository.findAll(specification, pageable).map(this.projectDepartmentMapper::toResponse);
      } else if (currentUser.getEmployee() != null) {
         Long employeeId = currentUser.getEmployee().getId();
         specification = specification.and(ProjectDepartmentSpecification.hasEmployeeAssignment(employeeId));
         return this.projectDepartmentRepository.findAll(specification, pageable).map(this.projectDepartmentMapper::toResponse);
      } else {
         return Page.empty(pageable);
      }
   }

   @Override
   public void activate(Long id) {
      ProjectDepartmentEntity mapping = (ProjectDepartmentEntity)this.projectDepartmentRepository
         .findById(id)
         .orElseThrow(() -> new IllegalArgumentException("Project department mapping not found with id: " + id));
      this.accessScopeService.validateDepartmentAccess(mapping.getDepartment().getId());
      if (Boolean.TRUE.equals(mapping.getActive())) {
         throw new IllegalArgumentException("Project department mapping is already active");
      } else if (!Boolean.TRUE.equals(mapping.getProject().getActive())) {
         throw new IllegalArgumentException("Cannot activate mapping because the project is inactive");
      } else if (!Boolean.TRUE.equals(mapping.getDepartment().getActive())) {
         throw new IllegalArgumentException("Cannot activate mapping because the department is inactive");
      } else {
         mapping.setActive(Boolean.valueOf(true));
      }
   }

   @Override
   public void deactivate(Long id) {
      ProjectDepartmentEntity mapping = (ProjectDepartmentEntity)this.projectDepartmentRepository
         .findById(id)
         .orElseThrow(() -> new IllegalArgumentException("Project department mapping not found with id: " + id));
      this.accessScopeService.validateDepartmentAccess(mapping.getDepartment().getId());
      if (Boolean.FALSE.equals(mapping.getActive())) {
         throw new IllegalArgumentException("Project department mapping is already inactive");
      } else {
         mapping.setActive(Boolean.valueOf(false));
      }
   }

   private void validateReadAccess(ProjectDepartmentEntity mapping) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      if (!this.accessScopeService.isAdmin(currentUser)) {
         if (!this.accessScopeService.isHrAdmin(currentUser)) {
            if (this.accessScopeService.hasManagementRole(currentUser)) {
               this.accessScopeService.validateDepartmentAccess(mapping.getDepartment().getId());
            } else {
               if (currentUser.getEmployee() != null) {
                  Long employeeId = currentUser.getEmployee().getId();
                  Specification<ProjectDepartmentEntity> employeeAccess = Specification.where(ProjectDepartmentSpecification.hasEmployeeAssignment(employeeId))
                     .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), mapping.getId()));
                  boolean hasAccess = this.projectDepartmentRepository.exists(employeeAccess);
                  if (hasAccess) {
                     return;
                  }
               }

               throw new AccessDeniedException("You do not have permission to access this project department");
            }
         }
      }
   }

   private Set<Long> getAuthorizedDepartmentIds(UserEntity currentUser) {
      if (currentUser.getEmployee() != null && currentUser.getEmployee().getDepartmentDesignations() != null) {
         Set<Long> departmentIds = new HashSet<>();
         currentUser.getEmployee().getDepartmentDesignations().forEach(departmentDesignation -> {
            if (departmentDesignation.getDepartment() != null) {
               departmentIds.add(departmentDesignation.getDepartment().getId());
            }
         });
         return departmentIds;
      } else {
         return Set.of();
      }
   }

   @Generated
   public ProjectDepartmentServiceImpl(
      final ProjectDepartmentRepository projectDepartmentRepository,
      final ProjectRepository projectRepository,
      final DepartmentRepository departmentRepository,
      final ProjectDepartmentMapper projectDepartmentMapper,
      final AccessScopeService accessScopeService,
      final CustomUserService customUserService
   ) {
      this.projectDepartmentRepository = projectDepartmentRepository;
      this.projectRepository = projectRepository;
      this.departmentRepository = departmentRepository;
      this.projectDepartmentMapper = projectDepartmentMapper;
      this.accessScopeService = accessScopeService;
      this.customUserService = customUserService;
   }
}
