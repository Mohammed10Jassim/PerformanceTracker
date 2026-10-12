package com.pt.PerformanceTracker.organization.projects.repository;

import com.pt.PerformanceTracker.organization.projects.entity.ProjectDepartmentEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectDepartmentRepository extends JpaRepository<ProjectDepartmentEntity, Long>, JpaSpecificationExecutor<ProjectDepartmentEntity> {
   Optional<ProjectDepartmentEntity> findByProjectIdAndDepartmentId(Long projectId, Long departmentId);

   boolean existsByProjectIdAndDepartmentId(Long projectId, Long departmentId);
}
