package com.pt.PerformanceTracker.performance.kpaTask.repository;

import com.pt.PerformanceTracker.performance.kpaTask.entity.ProjectKpaTaskEntity;
import com.pt.PerformanceTracker.performance.kpaTask.entity.TaskStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectKpaTaskRepository extends JpaRepository<ProjectKpaTaskEntity, Long>, JpaSpecificationExecutor<ProjectKpaTaskEntity> {
   Optional<ProjectKpaTaskEntity> findByKpaEmployeeAssignmentIdAndTaskNameIgnoreCase(Long assignmentId, String taskName);

   long countByKpaEmployeeAssignmentIdAndActiveTrue(Long assignmentId);

   long countByKpaEmployeeAssignmentIdAndStatusAndActiveTrue(Long assignmentId, TaskStatus status);
}
