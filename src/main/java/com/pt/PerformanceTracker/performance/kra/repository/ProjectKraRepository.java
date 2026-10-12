package com.pt.PerformanceTracker.performance.kra.repository;

import com.pt.PerformanceTracker.performance.kra.entity.ProjectKraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectKraRepository extends JpaRepository<ProjectKraEntity, Long>, JpaSpecificationExecutor<ProjectKraEntity> {
   boolean existsByProjectDepartmentIdAndKraNameIgnoreCase(Long projectDepartmentId, String kraName);
}
