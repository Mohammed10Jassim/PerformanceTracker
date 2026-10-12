package com.pt.PerformanceTracker.performance.kra.serviceImpl;

import com.pt.PerformanceTracker.organization.projects.entity.ProjectDepartmentEntity;
import com.pt.PerformanceTracker.organization.projects.repository.ProjectDepartmentRepository;
import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraCreateRequest;
import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraResponse;
import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraUpdateRequest;
import com.pt.PerformanceTracker.performance.kra.entity.ProjectKraEntity;
import com.pt.PerformanceTracker.performance.kra.mapper.ProjectKraMapper;
import com.pt.PerformanceTracker.performance.kra.repository.ProjectKraRepository;
import com.pt.PerformanceTracker.performance.kra.service.ProjectKraService;
import com.pt.PerformanceTracker.performance.kra.specification.ProjectKraSpecification;
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
public class ProjectKraServiceImpl implements ProjectKraService {
   private final ProjectKraRepository projectKraRepository;
   private final ProjectDepartmentRepository projectDepartmentRepository;
   private final ProjectKraMapper projectKraMapper;
   private final AccessScopeService accessScopeService;
   private final CustomUserService customUserService;

   @Override
   public ProjectKraResponse createKra(ProjectKraCreateRequest request) {
      ProjectDepartmentEntity projectDepartment = (ProjectDepartmentEntity)this.projectDepartmentRepository
         .findById(request.projectDepartmentId())
         .orElseThrow(() -> new IllegalArgumentException("Project department not found with id: " + request.projectDepartmentId()));
      Long departmentId = projectDepartment.getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (!Boolean.TRUE.equals(projectDepartment.getActive())) {
         throw new IllegalStateException("Project department is inactive");
      } else if (!Boolean.TRUE.equals(projectDepartment.getProject().getActive())) {
         throw new IllegalStateException("Project is inactive");
      } else if (this.projectKraRepository.existsByProjectDepartmentIdAndKraNameIgnoreCase(request.projectDepartmentId(), request.kraName())) {
         throw new IllegalArgumentException("KRA already exists for this project department");
      } else {
         ProjectKraEntity projectKra = new ProjectKraEntity();
         projectKra.setProjectDepartment(projectDepartment);
         projectKra.setKraName(request.kraName());
         projectKra.setDescription(request.description());
         projectKra.setActive(Boolean.valueOf(true));
         ProjectKraEntity savedKra = (ProjectKraEntity)this.projectKraRepository.save(projectKra);
         return this.projectKraMapper.toResponse(savedKra);
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public ProjectKraResponse getKraById(Long kraId) {
      ProjectKraEntity kra = (ProjectKraEntity)this.projectKraRepository
         .findById(kraId)
         .orElseThrow(() -> new IllegalArgumentException("Project KRA not found with id: " + kraId));
      this.validateReadAccess(kra);
      return this.projectKraMapper.toResponse(kra);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public Page<ProjectKraResponse> getProjectKras(
      Long projectDepartmentId, Long projectId, Long departmentId, String search, boolean activeOnly, Pageable pageable
   ) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      Specification<ProjectKraEntity> specification = Specification.where(ProjectKraSpecification.hasProjectDepartmentId(projectDepartmentId))
         .and(ProjectKraSpecification.hasProjectId(projectId))
         .and(ProjectKraSpecification.hasDepartmentId(departmentId))
         .and(ProjectKraSpecification.isActive(activeOnly))
         .and(ProjectKraSpecification.search(search));
      if (this.accessScopeService.isAdmin(currentUser)) {
         return this.projectKraRepository.findAll(specification, pageable).map(this.projectKraMapper::toResponse);
      } else if (this.accessScopeService.isHrAdmin(currentUser)) {
         return this.projectKraRepository.findAll(specification, pageable).map(this.projectKraMapper::toResponse);
      } else if (this.accessScopeService.hasManagementRole(currentUser)) {
         if (departmentId != null) {
            this.accessScopeService.validateDepartmentAccess(departmentId);
         } else {
            Set<Long> departmentIds = this.getAuthorizedDepartmentIds(currentUser);
            if (departmentIds.isEmpty()) {
               return Page.empty(pageable);
            }

            specification = specification.and(ProjectKraSpecification.hasDepartmentIds(departmentIds));
         }

         return this.projectKraRepository.findAll(specification, pageable).map(this.projectKraMapper::toResponse);
      } else if (currentUser.getEmployee() != null) {
         Long employeeId = currentUser.getEmployee().getId();
         specification = specification.and(ProjectKraSpecification.hasEmployeeAssignment(employeeId));
         return this.projectKraRepository.findAll(specification, pageable).map(this.projectKraMapper::toResponse);
      } else {
         return Page.empty(pageable);
      }
   }

   @Override
   public ProjectKraResponse updateKra(Long kraId, ProjectKraUpdateRequest request) {
      ProjectKraEntity kra = (ProjectKraEntity)this.projectKraRepository
         .findById(kraId)
         .orElseThrow(() -> new IllegalArgumentException("Project KRA not found with id: " + kraId));
      Long departmentId = kra.getProjectDepartment().getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (!Boolean.TRUE.equals(kra.getProjectDepartment().getActive())) {
         throw new IllegalStateException("Project department is inactive");
      } else if (!Boolean.TRUE.equals(kra.getProjectDepartment().getProject().getActive())) {
         throw new IllegalStateException("Project is inactive");
      } else {
         if (request.kraName() != null && !request.kraName().isBlank() && !request.kraName().equalsIgnoreCase(kra.getKraName())) {
            boolean duplicate = this.projectKraRepository
               .existsByProjectDepartmentIdAndKraNameIgnoreCase(kra.getProjectDepartment().getId(), request.kraName());
            if (duplicate) {
               throw new IllegalArgumentException("KRA already exists for this project department");
            }

            kra.setKraName(request.kraName());
         }

         if (request.description() != null) {
            kra.setDescription(request.description());
         }

         ProjectKraEntity updatedKra = (ProjectKraEntity)this.projectKraRepository.save(kra);
         return this.projectKraMapper.toResponse(updatedKra);
      }
   }

   @Override
   public void activateKra(Long kraId) {
      ProjectKraEntity kra = (ProjectKraEntity)this.projectKraRepository
         .findById(kraId)
         .orElseThrow(() -> new IllegalArgumentException("Project KRA not found with id: " + kraId));
      Long departmentId = kra.getProjectDepartment().getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (Boolean.TRUE.equals(kra.getActive())) {
         throw new IllegalStateException("Project KRA is already active");
      } else if (!Boolean.TRUE.equals(kra.getProjectDepartment().getActive())) {
         throw new IllegalStateException("Cannot activate KRA because project department is inactive");
      } else if (!Boolean.TRUE.equals(kra.getProjectDepartment().getProject().getActive())) {
         throw new IllegalStateException("Cannot activate KRA because project is inactive");
      } else {
         kra.setActive(Boolean.valueOf(true));
         this.projectKraRepository.save(kra);
      }
   }

   @Override
   public void deactivateKra(Long kraId) {
      ProjectKraEntity kra = (ProjectKraEntity)this.projectKraRepository
         .findById(kraId)
         .orElseThrow(() -> new IllegalArgumentException("Project KRA not found with id: " + kraId));
      Long departmentId = kra.getProjectDepartment().getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (!Boolean.TRUE.equals(kra.getActive())) {
         throw new IllegalStateException("Project KRA is already inactive");
      } else {
         kra.setActive(Boolean.valueOf(false));
         this.projectKraRepository.save(kra);
      }
   }

   private void validateReadAccess(ProjectKraEntity kra) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      if (!this.accessScopeService.isAdmin(currentUser)) {
         if (!this.accessScopeService.isHrAdmin(currentUser)) {
            if (this.accessScopeService.hasManagementRole(currentUser)) {
               Long departmentId = kra.getProjectDepartment().getDepartment().getId();
               this.accessScopeService.validateDepartmentAccess(departmentId);
            } else {
               if (currentUser.getEmployee() != null) {
                  Long employeeId = currentUser.getEmployee().getId();
                  Specification<ProjectKraEntity> employeeAccess = Specification.where(ProjectKraSpecification.hasEmployeeAssignment(employeeId))
                     .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), kra.getId()));
                  boolean hasAccess = this.projectKraRepository.exists(employeeAccess);
                  if (hasAccess) {
                     return;
                  }
               }

               throw new AccessDeniedException("You do not have permission to access this project KRA");
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
   public ProjectKraServiceImpl(
      final ProjectKraRepository projectKraRepository,
      final ProjectDepartmentRepository projectDepartmentRepository,
      final ProjectKraMapper projectKraMapper,
      final AccessScopeService accessScopeService,
      final CustomUserService customUserService
   ) {
      this.projectKraRepository = projectKraRepository;
      this.projectDepartmentRepository = projectDepartmentRepository;
      this.projectKraMapper = projectKraMapper;
      this.accessScopeService = accessScopeService;
      this.customUserService = customUserService;
   }
}
