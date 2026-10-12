package com.pt.PerformanceTracker.performance.kpaEmployee.serviceImpl;

import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import com.pt.PerformanceTracker.organization.employee.repository.EmployeeRepository;
import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.performance.kpa.entity.ProjectKpaEntity;
import com.pt.PerformanceTracker.performance.kpa.repository.ProjectKpaRepository;
import com.pt.PerformanceTracker.performance.kpaEmployee.dto.ProjectKpaEmployeeCreateRequest;
import com.pt.PerformanceTracker.performance.kpaEmployee.dto.ProjectKpaEmployeeResponse;
import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import com.pt.PerformanceTracker.performance.kpaEmployee.mapper.ProjectKpaEmployeeMapper;
import com.pt.PerformanceTracker.performance.kpaEmployee.repository.ProjectKpaEmployeeRepository;
import com.pt.PerformanceTracker.performance.kpaEmployee.service.ProjectKpaEmployeeService;
import com.pt.PerformanceTracker.performance.kpaEmployee.specification.ProjectKpaEmployeeSpecification;
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
public class ProjectKpaEmployeeServiceImpl implements ProjectKpaEmployeeService {
   private final ProjectKpaEmployeeRepository projectKpaEmployeeRepository;
   private final ProjectKpaRepository projectKpaRepository;
   private final EmployeeRepository employeeRepository;
   private final ProjectKpaEmployeeMapper projectKpaEmployeeMapper;
   private final AccessScopeService accessScopeService;
   private final CustomUserService customUserService;

   @Override
   public ProjectKpaEmployeeResponse assignEmployee(ProjectKpaEmployeeCreateRequest request) {
      ProjectKpaEntity projectKpa = (ProjectKpaEntity)this.projectKpaRepository
         .findById(request.projectKpaId())
         .orElseThrow(() -> new IllegalArgumentException("Project KPA not found with id: " + request.projectKpaId()));
      Long departmentId = projectKpa.getProjectKra().getProjectDepartment().getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (!Boolean.TRUE.equals(projectKpa.getActive())) {
         throw new IllegalArgumentException("Cannot assign employee to an inactive Project KPA");
      } else {
         EmployeeEntity employee = (EmployeeEntity)this.employeeRepository
            .findById(request.employeeId())
            .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + request.employeeId()));
         if (!Boolean.TRUE.equals(employee.getActive())) {
            throw new IllegalArgumentException("Cannot assign an inactive employee to a Project KPA");
         } else {
            boolean belongsToDepartment = employee.getDepartmentDesignations()
               .stream()
               .anyMatch(departmentDesignation -> departmentDesignation.getDepartment().getId().equals(departmentId));
            if (!belongsToDepartment) {
               throw new IllegalArgumentException("Employee does not belong to the department of this Project KPA");
            } else {
               ProjectKpaEmployeeEntity existingAssignment = this.projectKpaEmployeeRepository
                  .findByProjectKpaIdAndEmployeeId(request.projectKpaId(), request.employeeId())
                  .orElse(null);
               if (existingAssignment != null) {
                  if (Boolean.TRUE.equals(existingAssignment.getActive())) {
                     throw new IllegalArgumentException("Employee is already assigned to this Project KPA");
                  } else {
                     existingAssignment.setActive(Boolean.valueOf(true));
                     return this.projectKpaEmployeeMapper.toResponse(existingAssignment);
                  }
               } else {
                  ProjectKpaEmployeeEntity assignment = this.projectKpaEmployeeMapper.toEntity(request);
                  assignment.setProjectKpa(projectKpa);
                  assignment.setEmployee(employee);
                  ProjectKpaEmployeeEntity savedAssignment = (ProjectKpaEmployeeEntity)this.projectKpaEmployeeRepository.save(assignment);
                  return this.projectKpaEmployeeMapper.toResponse(savedAssignment);
               }
            }
         }
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public ProjectKpaEmployeeResponse getById(Long id) {
      ProjectKpaEmployeeEntity assignment = (ProjectKpaEmployeeEntity)this.projectKpaEmployeeRepository
         .findById(id)
         .orElseThrow(() -> new IllegalArgumentException("Project KPA Employee assignment not found with id: " + id));
      this.validateReadAccess(assignment);
      return this.projectKpaEmployeeMapper.toResponse(assignment);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public Page<ProjectKpaEmployeeResponse> getAssignments(
      Long projectKpaId, Long employeeId, Long projectId, Long departmentId, boolean activeOnly, Pageable pageable
   ) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      if (this.accessScopeService.isAdmin(currentUser) || this.accessScopeService.isHrAdmin(currentUser)) {
         Specification<ProjectKpaEmployeeEntity> specification = Specification.allOf(
            new Specification[]{
               ProjectKpaEmployeeSpecification.hasProjectKpaId(projectKpaId),
               ProjectKpaEmployeeSpecification.hasEmployeeId(employeeId),
               ProjectKpaEmployeeSpecification.hasProjectId(projectId),
               ProjectKpaEmployeeSpecification.hasDepartmentId(departmentId),
               ProjectKpaEmployeeSpecification.isActive(activeOnly)
            }
         );
         return this.projectKpaEmployeeRepository.findAll(specification, pageable).map(this.projectKpaEmployeeMapper::toResponse);
      } else if (this.accessScopeService.hasManagementRole(currentUser)) {
         Specification<ProjectKpaEmployeeEntity> specification = Specification.allOf(
            new Specification[]{
               ProjectKpaEmployeeSpecification.hasProjectKpaId(projectKpaId),
               ProjectKpaEmployeeSpecification.hasEmployeeId(employeeId),
               ProjectKpaEmployeeSpecification.hasProjectId(projectId),
               ProjectKpaEmployeeSpecification.hasDepartmentId(departmentId),
               ProjectKpaEmployeeSpecification.isActive(activeOnly)
            }
         );
         if (departmentId != null) {
            this.accessScopeService.validateDepartmentAccess(departmentId);
         } else {
            Set<Long> authorizedDepartmentIds = this.getAuthorizedDepartmentIds(currentUser);
            if (authorizedDepartmentIds.isEmpty()) {
               return Page.empty(pageable);
            }

            specification = specification.and(ProjectKpaEmployeeSpecification.hasDepartmentIds(authorizedDepartmentIds));
         }

         return this.projectKpaEmployeeRepository.findAll(specification, pageable).map(this.projectKpaEmployeeMapper::toResponse);
      } else if (currentUser.getEmployee() == null) {
         return Page.empty(pageable);
      } else {
         Long currentEmployeeId = currentUser.getEmployee().getId();
         Specification<ProjectKpaEmployeeEntity> specification = Specification.allOf(
            new Specification[]{
               ProjectKpaEmployeeSpecification.hasProjectKpaId(projectKpaId),
               ProjectKpaEmployeeSpecification.hasProjectId(projectId),
               ProjectKpaEmployeeSpecification.hasDepartmentId(departmentId),
               ProjectKpaEmployeeSpecification.hasEmployeeId(currentEmployeeId),
               ProjectKpaEmployeeSpecification.isActive(activeOnly)
            }
         );
         return this.projectKpaEmployeeRepository.findAll(specification, pageable).map(this.projectKpaEmployeeMapper::toResponse);
      }
   }

   @Override
   public void activate(Long id) {
      ProjectKpaEmployeeEntity assignment = (ProjectKpaEmployeeEntity)this.projectKpaEmployeeRepository
         .findById(id)
         .orElseThrow(() -> new IllegalArgumentException("Project KPA Employee assignment not found with id: " + id));
      Long departmentId = assignment.getProjectKpa().getProjectKra().getProjectDepartment().getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (Boolean.TRUE.equals(assignment.getActive())) {
         throw new IllegalArgumentException("Project KPA Employee assignment is already active");
      } else if (!Boolean.TRUE.equals(assignment.getProjectKpa().getActive())) {
         throw new IllegalArgumentException("Cannot activate assignment because Project KPA is inactive");
      } else if (!Boolean.TRUE.equals(assignment.getEmployee().getActive())) {
         throw new IllegalArgumentException("Cannot activate assignment because Employee is inactive");
      } else {
         assignment.setActive(Boolean.valueOf(true));
      }
   }

   @Override
   public void deactivate(Long id) {
      ProjectKpaEmployeeEntity assignment = (ProjectKpaEmployeeEntity)this.projectKpaEmployeeRepository
         .findById(id)
         .orElseThrow(() -> new IllegalArgumentException("Project KPA Employee assignment not found with id: " + id));
      Long departmentId = assignment.getProjectKpa().getProjectKra().getProjectDepartment().getDepartment().getId();
      this.accessScopeService.validateDepartmentAccess(departmentId);
      if (!Boolean.TRUE.equals(assignment.getActive())) {
         throw new IllegalArgumentException("Project KPA Employee assignment is already inactive");
      } else {
         assignment.setActive(Boolean.valueOf(false));
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

   private void validateReadAccess(ProjectKpaEmployeeEntity assignment) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      if (!this.accessScopeService.isAdmin(currentUser) && !this.accessScopeService.isHrAdmin(currentUser)) {
         if (this.accessScopeService.hasManagementRole(currentUser)) {
            Long departmentId = assignment.getProjectKpa().getProjectKra().getProjectDepartment().getDepartment().getId();
            this.accessScopeService.validateDepartmentAccess(departmentId);
         } else if (currentUser.getEmployee() == null
            || assignment.getEmployee() == null
            || !assignment.getEmployee().getId().equals(currentUser.getEmployee().getId())) {
            throw new AccessDeniedException("You do not have permission to view this assignment");
         }
      }
   }

   @Generated
   public ProjectKpaEmployeeServiceImpl(
      final ProjectKpaEmployeeRepository projectKpaEmployeeRepository,
      final ProjectKpaRepository projectKpaRepository,
      final EmployeeRepository employeeRepository,
      final ProjectKpaEmployeeMapper projectKpaEmployeeMapper,
      final AccessScopeService accessScopeService,
      final CustomUserService customUserService
   ) {
      this.projectKpaEmployeeRepository = projectKpaEmployeeRepository;
      this.projectKpaRepository = projectKpaRepository;
      this.employeeRepository = employeeRepository;
      this.projectKpaEmployeeMapper = projectKpaEmployeeMapper;
      this.accessScopeService = accessScopeService;
      this.customUserService = customUserService;
   }
}
