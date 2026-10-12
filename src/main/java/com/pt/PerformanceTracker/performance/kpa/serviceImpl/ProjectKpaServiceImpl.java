package com.pt.PerformanceTracker.performance.kpa.serviceImpl;

import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaCreateRequest;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaResponse;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaUpdateRequest;
import com.pt.PerformanceTracker.performance.kpa.entity.ProjectKpaEntity;
import com.pt.PerformanceTracker.performance.kpa.mapper.ProjectKpaMapper;
import com.pt.PerformanceTracker.performance.kpa.repository.ProjectKpaRepository;
import com.pt.PerformanceTracker.performance.kpa.service.ProjectKpaService;
import com.pt.PerformanceTracker.performance.kpa.specification.ProjectKpaSpecification;
import com.pt.PerformanceTracker.performance.kpaEmployee.repository.ProjectKpaEmployeeRepository;
import com.pt.PerformanceTracker.performance.kra.entity.ProjectKraEntity;
import com.pt.PerformanceTracker.performance.kra.repository.ProjectKraRepository;
import com.pt.PerformanceTracker.security.access.AccessScopeService;
import com.pt.PerformanceTracker.security.service.CustomUserService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectKpaServiceImpl implements ProjectKpaService {
   private final ProjectKpaRepository projectKpaRepository;
   private final ProjectKraRepository projectKraRepository;
   private final ProjectKpaMapper projectKpaMapper;
   private final AccessScopeService accessScopeService;
   private final CustomUserService customUserService;
   private final ProjectKpaEmployeeRepository projectKpaEmployeeRepository;

   @Override
   public ProjectKpaResponse createProjectKpa(ProjectKpaCreateRequest request) {
      ProjectKraEntity projectKra = (ProjectKraEntity)this.projectKraRepository
         .findById(request.projectKraId())
         .orElseThrow(() -> new IllegalArgumentException("Project KRA not found with id: " + request.projectKraId()));
      Long departmentId = projectKra.getProjectDepartment().getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (!Boolean.TRUE.equals(projectKra.getActive())) {
         throw new IllegalArgumentException("Cannot create KPA for an inactive Project KRA");
      } else {
         String kpaName = request.kpaName().trim();
         if (this.projectKpaRepository.existsByProjectKraIdAndKpaNameIgnoreCase(request.projectKraId(), kpaName)) {
            throw new IllegalArgumentException("Project KPA already exists with name: " + kpaName);
         } else {
            ProjectKpaEntity projectKpa = this.projectKpaMapper.toEntity(request);
            projectKpa.setProjectKra(projectKra);
            projectKpa.setKpaName(kpaName);
            ProjectKpaEntity savedProjectKpa = (ProjectKpaEntity)this.projectKpaRepository.save(projectKpa);
            return this.projectKpaMapper.toResponse(savedProjectKpa);
         }
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public ProjectKpaResponse getProjectKpaById(Long projectKpaId) {
      ProjectKpaEntity projectKpa = this.getProjectKpaEntity(projectKpaId);
      this.validateReadAccess(projectKpa);
      return this.projectKpaMapper.toResponse(projectKpa);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public Page<ProjectKpaResponse> getProjectKpas(Long projectKraId, Long projectId, Long departmentId, String search, boolean activeOnly, Pageable pageable) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      Specification<ProjectKpaEntity> specification = Specification.where(ProjectKpaSpecification.hasProjectKraId(projectKraId))
         .and(ProjectKpaSpecification.hasProjectId(projectId))
         .and(ProjectKpaSpecification.hasDepartmentId(departmentId))
         .and(ProjectKpaSpecification.search(search))
         .and(ProjectKpaSpecification.isActive(activeOnly));
      if (this.accessScopeService.isAdmin(currentUser) || this.accessScopeService.isHrAdmin(currentUser)) {
         return this.projectKpaRepository.findAll(specification, pageable).map(this.projectKpaMapper::toResponse);
      } else if (this.accessScopeService.hasManagementRole(currentUser)) {
         if (departmentId != null) {
            this.accessScopeService.validateDepartmentAccess(departmentId);
         } else {
            Set<Long> authorizedDepartmentIds = this.getAuthorizedDepartmentIds(currentUser);
            if (authorizedDepartmentIds.isEmpty()) {
               return Page.empty(pageable);
            }

            specification = specification.and(ProjectKpaSpecification.hasDepartmentIds(authorizedDepartmentIds));
         }

         return this.projectKpaRepository.findAll(specification, pageable).map(this.projectKpaMapper::toResponse);
      } else if (currentUser.getEmployee() == null) {
         return Page.empty(pageable);
      } else {
         Long currentEmployeeId = currentUser.getEmployee().getId();
         specification = specification.and(ProjectKpaSpecification.hasEmployeeAssignment(currentEmployeeId));
         return this.projectKpaRepository.findAll(specification, pageable).map(this.projectKpaMapper::toResponse);
      }
   }

   @Override
   public ProjectKpaResponse updateProjectKpa(Long projectKpaId, ProjectKpaUpdateRequest request) {
      ProjectKpaEntity projectKpa = this.getProjectKpaEntity(projectKpaId);
      Long departmentId = projectKpa.getProjectKra().getProjectDepartment().getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (request.kpaName() != null && !request.kpaName().isBlank()) {
         String kpaName = request.kpaName().trim();
         boolean nameChanged = !projectKpa.getKpaName().equalsIgnoreCase(kpaName);
         if (nameChanged && this.projectKpaRepository.existsByProjectKraIdAndKpaNameIgnoreCase(projectKpa.getProjectKra().getId(), kpaName)) {
            throw new IllegalArgumentException("Project KPA already exists with name: " + kpaName);
         }
      }

      this.projectKpaMapper.updateEntity(request, projectKpa);
      if (request.kpaName() != null && !request.kpaName().isBlank()) {
         projectKpa.setKpaName(request.kpaName().trim());
      }

      return this.projectKpaMapper.toResponse(projectKpa);
   }

   @Override
   public void activateProjectKpa(Long projectKpaId) {
      ProjectKpaEntity projectKpa = this.getProjectKpaEntity(projectKpaId);
      Long departmentId = projectKpa.getProjectKra().getProjectDepartment().getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (Boolean.TRUE.equals(projectKpa.getActive())) {
         throw new IllegalArgumentException("Project KPA is already active");
      } else if (!Boolean.TRUE.equals(projectKpa.getProjectKra().getActive())) {
         throw new IllegalArgumentException("Cannot activate KPA because the Project KRA is inactive");
      } else {
         projectKpa.setActive(Boolean.valueOf(true));
      }
   }

   @Override
   public void deactivateProjectKpa(Long projectKpaId) {
      ProjectKpaEntity projectKpa = this.getProjectKpaEntity(projectKpaId);
      Long departmentId = projectKpa.getProjectKra().getProjectDepartment().getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (!Boolean.TRUE.equals(projectKpa.getActive())) {
         throw new IllegalArgumentException("Project KPA is already inactive");
      } else {
         projectKpa.setActive(Boolean.valueOf(false));
      }
   }

   private void validateReadAccess(ProjectKpaEntity projectKpa) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      if (!this.accessScopeService.isAdmin(currentUser) && !this.accessScopeService.isHrAdmin(currentUser)) {
         if (this.accessScopeService.hasManagementRole(currentUser)) {
            Long departmentId = projectKpa.getProjectKra().getProjectDepartment().getDepartment().getId();
            this.accessScopeService.validateDepartmentAccess(departmentId);
         } else if (currentUser.getEmployee() == null) {
            throw new AccessDeniedException("You do not have permission to access this KPA");
         } else {
            Long currentEmployeeId = currentUser.getEmployee().getId();
            boolean assigned = this.projectKpaEmployeeRepository
               .findByProjectKpaIdAndEmployeeIdAndActiveTrue(projectKpa.getId(), currentEmployeeId)
               .isPresent();
            if (!assigned) {
               throw new AccessDeniedException("You do not have permission to access this KPA");
            }
         }
      }
   }

   private Set<Long> getAuthorizedDepartmentIds(UserEntity currentUser) {
      return currentUser.getEmployee() != null && currentUser.getEmployee().getDepartmentDesignations() != null
         ? currentUser.getEmployee()
            .getDepartmentDesignations()
            .stream()
            .filter(dd -> dd.getDepartment() != null)
            .map(dd -> dd.getDepartment().getId())
            .collect(Collectors.toSet())
         : Set.of();
   }

   private ProjectKpaEntity getProjectKpaEntity(Long projectKpaId) {
      return (ProjectKpaEntity)this.projectKpaRepository
         .findById(projectKpaId)
         .orElseThrow(() -> new IllegalArgumentException("Project KPA not found with id: " + projectKpaId));
   }

   @Generated
   public ProjectKpaServiceImpl(
      final ProjectKpaRepository projectKpaRepository,
      final ProjectKraRepository projectKraRepository,
      final ProjectKpaMapper projectKpaMapper,
      final AccessScopeService accessScopeService,
      final CustomUserService customUserService,
      final ProjectKpaEmployeeRepository projectKpaEmployeeRepository
   ) {
      this.projectKpaRepository = projectKpaRepository;
      this.projectKraRepository = projectKraRepository;
      this.projectKpaMapper = projectKpaMapper;
      this.accessScopeService = accessScopeService;
      this.customUserService = customUserService;
      this.projectKpaEmployeeRepository = projectKpaEmployeeRepository;
   }
}
