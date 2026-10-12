package com.pt.PerformanceTracker.performance.kpaTask.serviceImpl;

import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import com.pt.PerformanceTracker.performance.kpaEmployee.repository.ProjectKpaEmployeeRepository;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskCreateRequest;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskResponse;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskUpdateRequest;
import com.pt.PerformanceTracker.performance.kpaTask.entity.ProjectKpaTaskEntity;
import com.pt.PerformanceTracker.performance.kpaTask.entity.TaskStatus;
import com.pt.PerformanceTracker.performance.kpaTask.repository.ProjectKpaTaskRepository;
import com.pt.PerformanceTracker.performance.kpaTask.service.ProjectKpaTaskService;
import com.pt.PerformanceTracker.performance.kpaTask.specification.ProjectKpaTaskSpecification;
import com.pt.PerformanceTracker.security.access.AccessScopeService;
import com.pt.PerformanceTracker.security.service.CustomUserService;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class ProjectKpaTaskServiceImpl implements ProjectKpaTaskService {
   private final ProjectKpaTaskRepository projectKpaTaskRepository;
   private final ProjectKpaEmployeeRepository projectKpaEmployeeRepository;
   private final CustomUserService customUserService;
   private final AccessScopeService accessScopeService;

   @Override
   public ProjectKpaTaskResponse createTask(ProjectKpaTaskCreateRequest request) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      ProjectKpaEmployeeEntity assignment = (ProjectKpaEmployeeEntity)this.projectKpaEmployeeRepository
         .findById(request.kpaEmployeeAssignmentId())
         .orElseThrow(() -> new IllegalArgumentException("KPA assignment not found with id: " + request.kpaEmployeeAssignmentId()));
      this.validateAssignmentAccess(assignment, currentUser);
      if (!Boolean.TRUE.equals(assignment.getActive())) {
         throw new IllegalArgumentException("Cannot create task because the KPA assignment is inactive");
      } else if (!Boolean.TRUE.equals(assignment.getProjectKpa().getActive())) {
         throw new IllegalArgumentException("Cannot create task because the Project KPA is inactive");
      } else {
         String taskName = request.taskName().trim();
         ProjectKpaTaskEntity existingTask = this.projectKpaTaskRepository
            .findByKpaEmployeeAssignmentIdAndTaskNameIgnoreCase(assignment.getId(), taskName)
            .orElse(null);
         if (existingTask != null) {
            if (Boolean.TRUE.equals(existingTask.getActive())) {
               throw new IllegalArgumentException("Task already exists with name: " + taskName);
            } else {
               existingTask.setActive(Boolean.valueOf(true));
               existingTask.setDescription(request.description());
               existingTask.setStatus(TaskStatus.PENDING);
               this.recalculateCompletionPercentage(assignment);
               return this.toResponse(existingTask);
            }
         } else {
            ProjectKpaTaskEntity task = ProjectKpaTaskEntity.builder()
               .kpaEmployeeAssignment(assignment)
               .taskName(taskName)
               .description(request.description())
               .status(TaskStatus.PENDING)
               .build();
            ProjectKpaTaskEntity savedTask = (ProjectKpaTaskEntity)this.projectKpaTaskRepository.save(task);
            this.recalculateCompletionPercentage(assignment);
            return this.toResponse(savedTask);
         }
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public ProjectKpaTaskResponse getTaskById(Long taskId) {
      ProjectKpaTaskEntity task = (ProjectKpaTaskEntity)this.projectKpaTaskRepository
         .findById(taskId)
         .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));
      UserEntity currentUser = this.customUserService.getCurrentUser();
      if (!this.canViewTask(task, currentUser)) {
         throw new AccessDeniedException("You do not have permission to view this task");
      } else {
         return this.toResponse(task);
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public Page<ProjectKpaTaskResponse> getTasks(
      Long assignmentId, Long employeeId, Long projectId, Long departmentId, TaskStatus status, String search, boolean activeOnly, Pageable pageable
   ) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      if (this.accessScopeService.isAdmin(currentUser) || this.accessScopeService.isHrAdmin(currentUser)) {
         Specification<ProjectKpaTaskEntity> specification = Specification.allOf(
            new Specification[]{
               ProjectKpaTaskSpecification.hasAssignmentId(assignmentId),
               ProjectKpaTaskSpecification.hasEmployeeId(employeeId),
               ProjectKpaTaskSpecification.hasProjectId(projectId),
               ProjectKpaTaskSpecification.hasDepartmentId(departmentId),
               ProjectKpaTaskSpecification.hasStatus(status),
               ProjectKpaTaskSpecification.search(search),
               ProjectKpaTaskSpecification.isActive(activeOnly)
            }
         );
         return this.projectKpaTaskRepository.findAll(specification, pageable).map(this::toResponse);
      } else if (this.accessScopeService.hasManagementRole(currentUser)) {
         Specification<ProjectKpaTaskEntity> specification = Specification.allOf(
            new Specification[]{
               ProjectKpaTaskSpecification.hasAssignmentId(assignmentId),
               ProjectKpaTaskSpecification.hasEmployeeId(employeeId),
               ProjectKpaTaskSpecification.hasProjectId(projectId),
               ProjectKpaTaskSpecification.hasDepartmentId(departmentId),
               ProjectKpaTaskSpecification.hasStatus(status),
               ProjectKpaTaskSpecification.search(search),
               ProjectKpaTaskSpecification.isActive(activeOnly)
            }
         );
         if (departmentId != null) {
            this.accessScopeService.validateDepartmentAccess(departmentId);
         } else {
            Set<Long> departmentIds = this.getAuthorizedDepartmentIds(currentUser);
            if (departmentIds.isEmpty()) {
               return Page.empty(pageable);
            }

            specification = specification.and(ProjectKpaTaskSpecification.hasDepartmentIds(departmentIds));
         }

         return this.projectKpaTaskRepository.findAll(specification, pageable).map(this::toResponse);
      } else if (currentUser.getEmployee() != null) {
         Long currentEmployeeId = currentUser.getEmployee().getId();
         Specification<ProjectKpaTaskEntity> specification = Specification.allOf(
            new Specification[]{
               ProjectKpaTaskSpecification.hasAssignmentId(assignmentId),
               ProjectKpaTaskSpecification.hasProjectId(projectId),
               ProjectKpaTaskSpecification.hasDepartmentId(departmentId),
               ProjectKpaTaskSpecification.hasStatus(status),
               ProjectKpaTaskSpecification.search(search),
               ProjectKpaTaskSpecification.isActive(activeOnly),
               ProjectKpaTaskSpecification.hasEmployeeId(currentEmployeeId),
               ProjectKpaTaskSpecification.isAssignmentActive()
            }
         );
         return this.projectKpaTaskRepository.findAll(specification, pageable).map(this::toResponse);
      } else {
         return Page.empty(pageable);
      }
   }

   @Override
   public ProjectKpaTaskResponse updateTask(Long taskId, ProjectKpaTaskUpdateRequest request) {
      ProjectKpaTaskEntity task = (ProjectKpaTaskEntity)this.projectKpaTaskRepository
         .findById(taskId)
         .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));
      UserEntity currentUser = this.customUserService.getCurrentUser();
      ProjectKpaEmployeeEntity assignment = task.getKpaEmployeeAssignment();
      this.validateAssignmentAccess(assignment, currentUser);
      if (!Boolean.TRUE.equals(assignment.getActive())) {
         throw new IllegalArgumentException("Cannot update task because the KPA assignment is inactive");
      } else if (!Boolean.TRUE.equals(task.getActive())) {
         throw new IllegalArgumentException("Cannot update an inactive task. Activate the task first");
      } else if (!Boolean.TRUE.equals(assignment.getProjectKpa().getActive())) {
         throw new IllegalArgumentException("Cannot update task because the Project KPA is inactive");
      } else {
         boolean elevated = this.accessScopeService.isAdmin(currentUser)
            || this.accessScopeService.isHrAdmin(currentUser)
            || this.accessScopeService.hasManagementRole(currentUser);
         if (!elevated) {
            boolean renaming = request.taskName() != null && !request.taskName().isBlank() && !request.taskName().trim().equals(task.getTaskName());
            boolean changingDescription = request.description() != null && !request.description().equals(task.getDescription());
            if (renaming || changingDescription) {
               throw new AccessDeniedException("Employees can only update the status of their own tasks");
            }
         }

         if (request.taskName() != null && !request.taskName().isBlank()) {
            String taskName = request.taskName().trim();
            if (!task.getTaskName().equalsIgnoreCase(taskName)) {
               ProjectKpaTaskEntity existingTask = this.projectKpaTaskRepository
                  .findByKpaEmployeeAssignmentIdAndTaskNameIgnoreCase(assignment.getId(), taskName)
                  .orElse(null);
               if (existingTask != null && !existingTask.getId().equals(task.getId())) {
                  throw new IllegalArgumentException("Task already exists with name: " + taskName);
               }
            }

            task.setTaskName(taskName);
         }

         if (request.description() != null) {
            task.setDescription(request.description());
         }

         if (request.status() != null) {
            task.setStatus(request.status());
         }

         this.recalculateCompletionPercentage(assignment);
         return this.toResponse(task);
      }
   }

   @Override
   public void activateTask(Long taskId) {
      ProjectKpaTaskEntity task = (ProjectKpaTaskEntity)this.projectKpaTaskRepository
         .findById(taskId)
         .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));
      UserEntity currentUser = this.customUserService.getCurrentUser();
      ProjectKpaEmployeeEntity assignment = task.getKpaEmployeeAssignment();
      this.validateAssignmentAccess(assignment, currentUser);
      if (Boolean.TRUE.equals(task.getActive())) {
         throw new IllegalArgumentException("Task is already active");
      } else if (!Boolean.TRUE.equals(assignment.getActive())) {
         throw new IllegalArgumentException("Cannot activate task because the KPA assignment is inactive");
      } else if (!Boolean.TRUE.equals(assignment.getProjectKpa().getActive())) {
         throw new IllegalArgumentException("Cannot activate task because the Project KPA is inactive");
      } else {
         task.setActive(Boolean.valueOf(true));
         this.recalculateCompletionPercentage(assignment);
      }
   }

   @Override
   public void deactivateTask(Long taskId) {
      ProjectKpaTaskEntity task = (ProjectKpaTaskEntity)this.projectKpaTaskRepository
         .findById(taskId)
         .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));
      UserEntity currentUser = this.customUserService.getCurrentUser();
      ProjectKpaEmployeeEntity assignment = task.getKpaEmployeeAssignment();
      this.validateAssignmentAccess(assignment, currentUser);
      if (!Boolean.TRUE.equals(task.getActive())) {
         throw new IllegalArgumentException("Task is already inactive");
      } else {
         task.setActive(Boolean.valueOf(false));
         this.recalculateCompletionPercentage(assignment);
      }
   }

   private void validateAssignmentAccess(ProjectKpaEmployeeEntity assignment, UserEntity currentUser) {
      if (!this.accessScopeService.isAdmin(currentUser) && !this.accessScopeService.isHrAdmin(currentUser)) {
         Long departmentId = assignment.getProjectKpa().getProjectKra().getProjectDepartment().getDepartment().getId();
         if (this.accessScopeService.hasManagementRole(currentUser)) {
            this.accessScopeService.validateDepartmentAccess(departmentId);
         } else if (currentUser.getEmployee() == null
            || assignment.getEmployee() == null
            || !assignment.getEmployee().getId().equals(currentUser.getEmployee().getId())) {
            throw new AccessDeniedException("You do not have permission to access this KPA assignment");
         }
      }
   }

   private boolean canViewTask(ProjectKpaTaskEntity task, UserEntity currentUser) {
      ProjectKpaEmployeeEntity assignment = task.getKpaEmployeeAssignment();
      if (this.accessScopeService.isAdmin(currentUser) || this.accessScopeService.isHrAdmin(currentUser)) {
         return true;
      } else if (this.accessScopeService.hasManagementRole(currentUser)) {
         Long departmentId = assignment.getProjectKpa().getProjectKra().getProjectDepartment().getDepartment().getId();
         return this.accessScopeService.canAccessDepartment(currentUser, departmentId);
      } else {
         return currentUser.getEmployee() != null
            && assignment.getEmployee() != null
            && assignment.getEmployee().getId().equals(currentUser.getEmployee().getId());
      }
   }

   private void recalculateCompletionPercentage(ProjectKpaEmployeeEntity assignment) {
      Long assignmentId = assignment.getId();
      long totalActiveTasks = this.projectKpaTaskRepository.countByKpaEmployeeAssignmentIdAndActiveTrue(assignmentId);
      if (totalActiveTasks == 0L) {
         assignment.setCompletionPercentage(BigDecimal.ZERO);
      } else {
         long completedActiveTasks = this.projectKpaTaskRepository.countByKpaEmployeeAssignmentIdAndStatusAndActiveTrue(assignmentId, TaskStatus.COMPLETED);
         BigDecimal completionPercentage = BigDecimal.valueOf(completedActiveTasks)
            .multiply(BigDecimal.valueOf(100L))
            .divide(BigDecimal.valueOf(totalActiveTasks), 2, RoundingMode.HALF_UP);
         assignment.setCompletionPercentage(completionPercentage);
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

   private ProjectKpaTaskResponse toResponse(ProjectKpaTaskEntity task) {
      return new ProjectKpaTaskResponse(
         task.getId(), task.getKpaEmployeeAssignment().getId(), task.getTaskName(), task.getDescription(), task.getStatus(), task.getActive()
      );
   }

   @Generated
   public ProjectKpaTaskServiceImpl(
      final ProjectKpaTaskRepository projectKpaTaskRepository,
      final ProjectKpaEmployeeRepository projectKpaEmployeeRepository,
      final CustomUserService customUserService,
      final AccessScopeService accessScopeService
   ) {
      this.projectKpaTaskRepository = projectKpaTaskRepository;
      this.projectKpaEmployeeRepository = projectKpaEmployeeRepository;
      this.customUserService = customUserService;
      this.accessScopeService = accessScopeService;
   }
}
