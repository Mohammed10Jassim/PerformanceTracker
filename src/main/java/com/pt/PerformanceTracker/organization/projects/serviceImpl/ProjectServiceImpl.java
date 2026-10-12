package com.pt.PerformanceTracker.organization.projects.serviceImpl;

import com.pt.PerformanceTracker.organization.projects.dto.ProjectCreateRequest;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectResponse;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectUpdateRequest;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectEntity;
import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import com.pt.PerformanceTracker.organization.projects.mapper.ProjectMapper;
import com.pt.PerformanceTracker.organization.projects.repository.ProjectRepository;
import com.pt.PerformanceTracker.organization.projects.service.ProjectService;
import com.pt.PerformanceTracker.organization.projects.specification.ProjectSpecification;
import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.security.access.AccessScopeService;
import com.pt.PerformanceTracker.security.service.CustomUserService;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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
public class ProjectServiceImpl implements ProjectService {
   private final ProjectRepository projectRepository;
   private final ProjectMapper projectMapper;
   private final AccessScopeService accessScopeService;
   private final CustomUserService customUserService;

   @Override
   public ProjectResponse createProject(ProjectCreateRequest request) {
      String projectName = request.projectName().trim();
      if (this.projectRepository.existsByProjectNameIgnoreCase(projectName)) {
         throw new IllegalArgumentException("Project already exists with name: " + projectName);
      } else {
         this.validateDates(request.startDate(), request.endDate());
         ProjectEntity project = this.projectMapper.toEntity(request);
         project.setProjectName(projectName);
         project.setStatus(ProjectStatus.DRAFT);
         ProjectEntity savedProject = (ProjectEntity)this.projectRepository.save(project);
         return this.projectMapper.toResponse(savedProject);
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public ProjectResponse getProjectById(Long projectId) {
      ProjectEntity project = this.findProjectById(projectId);
      if (!this.canViewProject(project)) {
         throw new AccessDeniedException("You do not have permission to view this project");
      } else {
         return this.projectMapper.toResponse(project);
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public Page<ProjectResponse> getProjects(ProjectStatus status, String search, boolean activeOnly, Pageable pageable) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      Specification<ProjectEntity> specification = Specification.allOf(
         new Specification[]{ProjectSpecification.hasStatus(status), ProjectSpecification.search(search), ProjectSpecification.isActive(activeOnly)}
      );
      if (this.accessScopeService.isAdmin(currentUser) || this.accessScopeService.isHrAdmin(currentUser)) {
         return this.projectRepository.findAll(specification, pageable).map(this.projectMapper::toResponse);
      } else if (this.accessScopeService.hasManagementRole(currentUser)) {
         if (currentUser.getEmployee() != null && currentUser.getEmployee().getDepartmentDesignations() != null) {
            Set<Long> authorizedDepartmentIds = this.getAuthorizedDepartmentIds(currentUser);
            if (authorizedDepartmentIds.isEmpty()) {
               return Page.empty(pageable);
            } else {
               specification = specification.and(ProjectSpecification.hasDepartmentIds(authorizedDepartmentIds));
               return this.projectRepository.findAll(specification, pageable).map(this.projectMapper::toResponse);
            }
         } else {
            return Page.empty(pageable);
         }
      } else if (currentUser.getEmployee() != null) {
         Long employeeId = currentUser.getEmployee().getId();
         specification = specification.and(ProjectSpecification.hasEmployeeAssignment(employeeId));
         return this.projectRepository.findAll(specification, pageable).map(this.projectMapper::toResponse);
      } else {
         return Page.empty(pageable);
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public List<ProjectStatus> getAvailableStatuses(Long projectId) {
      ProjectEntity project = this.findProjectById(projectId);
      if (!this.canViewProject(project)) {
         throw new AccessDeniedException("You do not have permission to view this project");
      } else {
         ProjectStatus currentStatus = project.getStatus();

         return switch (currentStatus) {
            case DRAFT -> List.of(ProjectStatus.PLANNED, ProjectStatus.CANCELLED);
            case PLANNED -> List.of(ProjectStatus.IN_PROGRESS, ProjectStatus.CANCELLED);
            case IN_PROGRESS -> List.of(ProjectStatus.ON_HOLD, ProjectStatus.COMPLETED, ProjectStatus.CANCELLED);
            case ON_HOLD -> List.of(ProjectStatus.IN_PROGRESS, ProjectStatus.CANCELLED);
            case COMPLETED -> List.of(ProjectStatus.CLOSED);
            case CANCELLED, CLOSED -> List.of();
         };
      }
   }

   @Override
   public ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request) {
      ProjectEntity project = this.findProjectById(projectId);
      if (request.projectName() != null && !request.projectName().isBlank()) {
         String projectName = request.projectName().trim();
         if (!project.getProjectName().equalsIgnoreCase(projectName) && this.projectRepository.existsByProjectNameIgnoreCase(projectName)) {
            throw new IllegalArgumentException("Project already exists with name: " + projectName);
         }
      }

      this.projectMapper.updateEntity(request, project);
      if (project.getProjectName() != null) {
         project.setProjectName(project.getProjectName().trim());
      }

      this.validateDates(project.getStartDate(), project.getEndDate());
      return this.projectMapper.toResponse(project);
   }

   @Override
   public ProjectResponse updateProjectStatus(Long projectId, ProjectStatus status) {
      if (status == null) {
         throw new IllegalArgumentException("Project status cannot be null");
      } else {
         ProjectEntity project = this.findProjectById(projectId);
         ProjectStatus currentStatus = project.getStatus();
         if (currentStatus == status) {
            throw new IllegalArgumentException("Project is already in status: " + status);
         } else {
            this.validateStatusTransition(currentStatus, status);
            project.setStatus(status);
            return this.projectMapper.toResponse(project);
         }
      }
   }

   @Override
   public void activateProject(Long projectId) {
      ProjectEntity project = this.findProjectById(projectId);
      if (Boolean.TRUE.equals(project.getActive())) {
         throw new IllegalArgumentException("Project is already active");
      } else {
         project.setActive(Boolean.valueOf(true));
      }
   }

   @Override
   public void deactivateProject(Long projectId) {
      ProjectEntity project = this.findProjectById(projectId);
      if (Boolean.FALSE.equals(project.getActive())) {
         throw new IllegalArgumentException("Project is already inactive");
      } else {
         project.setActive(Boolean.valueOf(false));
      }
   }

   private boolean canViewProject(ProjectEntity project) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      if (this.accessScopeService.isAdmin(currentUser) || this.accessScopeService.isHrAdmin(currentUser)) {
         return true;
      } else if (this.accessScopeService.hasManagementRole(currentUser)) {
         return project.getProjectDepartments() != null && !project.getProjectDepartments().isEmpty()
            ? project.getProjectDepartments()
               .stream()
               .anyMatch(projectDepartment -> this.accessScopeService.canAccessDepartment(currentUser, projectDepartment.getDepartment().getId()))
            : false;
      } else if (currentUser.getEmployee() != null) {
         Long employeeId = currentUser.getEmployee().getId();
         Specification<ProjectEntity> employeeAccess = Specification.where(ProjectSpecification.hasEmployeeAssignment(employeeId))
            .and((root, query, cb) -> cb.equal(root.get("id"), project.getId()));
         return this.projectRepository.exists(employeeAccess);
      } else {
         return false;
      }
   }

   private ProjectEntity findProjectById(Long projectId) {
      return (ProjectEntity)this.projectRepository
         .findById(projectId)
         .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));
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

   private void validateDates(LocalDate startDate, LocalDate endDate) {
      if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
         throw new IllegalArgumentException("Project end date cannot be before start date");
      }
   }

   private void validateStatusTransition(ProjectStatus currentStatus, ProjectStatus newStatus) {
      boolean validTransition = switch (currentStatus) {
         case DRAFT -> newStatus == ProjectStatus.PLANNED || newStatus == ProjectStatus.CANCELLED;
         case PLANNED -> newStatus == ProjectStatus.IN_PROGRESS || newStatus == ProjectStatus.CANCELLED;
         case IN_PROGRESS -> newStatus == ProjectStatus.ON_HOLD || newStatus == ProjectStatus.COMPLETED || newStatus == ProjectStatus.CANCELLED;
         case ON_HOLD -> newStatus == ProjectStatus.IN_PROGRESS || newStatus == ProjectStatus.CANCELLED;
         case COMPLETED -> newStatus == ProjectStatus.CLOSED;
         case CANCELLED, CLOSED -> false;
      };
      if (!validTransition) {
         throw new IllegalArgumentException("Invalid project status transition from " + currentStatus + " to " + newStatus);
      }
   }

   @Generated
   public ProjectServiceImpl(
      final ProjectRepository projectRepository,
      final ProjectMapper projectMapper,
      final AccessScopeService accessScopeService,
      final CustomUserService customUserService
   ) {
      this.projectRepository = projectRepository;
      this.projectMapper = projectMapper;
      this.accessScopeService = accessScopeService;
      this.customUserService = customUserService;
   }
}
